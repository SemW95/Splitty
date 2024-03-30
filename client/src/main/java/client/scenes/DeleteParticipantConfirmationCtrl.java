package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * DeleteParticipantConfirmation Popup.
 */
public class DeleteParticipantConfirmationCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ResourceBundle resources;
    private Runnable callback;

    @Inject
    public DeleteParticipantConfirmationCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    @FXML
    private Button deleteParticipant;

    @FXML
    private Button goBackToManageScene;

    @FXML
    private void deleteParticipant() {
        callback.run();
        mainCtrl.closePopup();
    }

    @FXML
    private void goBackToManageScene() {
        mainCtrl.closePopup();
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }
}
