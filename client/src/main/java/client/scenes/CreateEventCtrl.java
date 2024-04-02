package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import commons.Person;
import jakarta.inject.Inject;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    @FXML
    private Button cross;
    @FXML
    private Button create;
    @FXML
    private Button cancel;

    private Consumer<Event> callback;

    @Inject
    public CreateEventCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @FXML
    private void handleCreate(ActionEvent actionEvent) {
        if (eventName.getText().isBlank() || eventDescription.getText().isBlank()) {
            name = eventName.getText();
            description = eventDescription.getText();
            invalidFieldsMessage.setVisible(false);
        } else {
            invalidFieldsMessage.setVisible(true);
        }
        
        Event event = new Event(name, description);
        callback.accept(event);
        mainCtrl.closePopup();
        mainCtrl.showEventOverview(event, true);

    }

    public void handleCancel(ActionEvent actionEvent) {
        mainCtrl.closePopup();
    }

    public void handleCross(ActionEvent actionEvent) {
        mainCtrl.closePopup();
    }

    public void setCallback(Consumer<Event> callback) {
        this.callback = callback;
    }

}
