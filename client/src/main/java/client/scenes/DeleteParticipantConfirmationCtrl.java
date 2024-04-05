package client.scenes;

import client.utils.ScreenUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * DeleteParticipantConfirmation Popup.
 */
public class DeleteParticipantConfirmationCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ResourceBundle resources;
    private Runnable callback;
    @FXML
    private AnchorPane root;

    @Inject
    public DeleteParticipantConfirmationCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        root.addEventFilter(KeyEvent.KEY_PRESSED,
            ScreenUtils.exitHandler(resources, mainCtrl::closePopup));
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
