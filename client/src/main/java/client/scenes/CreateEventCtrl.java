package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import commons.Event;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller class for the Expense Creation Screen FXML UI.
 */
public class CreateEventCtrl {

    private ServerUtils server;
    private MainCtrl mainCtrl;
    @FXML
    private TextField eventName;
    @FXML
    private TextField eventDescription;
    private String name;
    private String description;
    @FXML
    private Label invalidFieldsMessage;

    @Inject
    public CreateEventCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @FXML
    private void handleCreate(ActionEvent actionEvent) {
        if (!eventName.getText().isBlank() && !eventDescription.getText().isBlank()) {
            name = eventName.getText();
            description = eventDescription.getText();
            invalidFieldsMessage.setVisible(false);
            Event event = new Event(name, description); // Invite code automatically generated
            event = server.createEvent(event);
            Main.configManager.addCode(event.getCode());
            mainCtrl.closePopup();
            mainCtrl.showEventOverview(event, false);
        } else {
            invalidFieldsMessage.setVisible(true);
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        mainCtrl.closePopup();
    }

}
