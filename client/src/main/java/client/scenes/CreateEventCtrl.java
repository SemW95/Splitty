package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import commons.Event;
import jakarta.inject.Inject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Controller class for the Expense Creation Screen FXML UI.
 */
public class CreateEventCtrl implements Initializable {

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
    private AnchorPane root;
    private ResourceBundle resources;

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
            mainCtrl.updateAll();
            mainCtrl.closePopup();
            mainCtrl.showEventOverview(event, false);
        } else {
            invalidFieldsMessage.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                // Creating a confirmation dialog
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirmation");
                confirmAlert.setHeaderText(null); // Optional: No header
                confirmAlert.setContentText("You have pressed Escape, "
                    +
                    "\nare you sure you want to go back?");

                // This will show the dialog and wait for the user response
                Optional<ButtonType> result = confirmAlert.showAndWait();

                // Checking the user's decision
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // If user clicks OK, then perform the action to go back/close
                    mainCtrl.closePopup();
                }
                event.consume(); // Prevents the event from propagating further
            }
        });
    }

    public void handleCancel(ActionEvent actionEvent) {
        mainCtrl.closePopup();
    }

}
