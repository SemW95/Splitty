package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;


/**
 * Delete event confirmation popup.
 */
public class DeleteEventConfirmationCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ResourceBundle resources;
    private Runnable deleteCallback;

    @FXML
    private Pane root;

    @Inject
    public DeleteEventConfirmationCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
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

    @FXML
    private void handleYes() {
        if (deleteCallback != null) {
            deleteCallback.run();
        }
        mainCtrl.closePopup();
        mainCtrl.updateAll();
    }

    @FXML
    private void handleNo() {
        mainCtrl.closePopup();
    }

    public void setCallback(Runnable deleteCallback) {
        this.deleteCallback = deleteCallback;
    }
}
