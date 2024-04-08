package client.scenes;

import client.utils.ScreenUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        root.addEventFilter(KeyEvent.KEY_PRESSED,
            ScreenUtils.exitHandler(resources, mainCtrl::closePopup));
    }

    @FXML
    private void handleYes() {
        if (deleteCallback != null) {
            deleteCallback.run();
        }
        mainCtrl.closePopup();
    }

    @FXML
    private void handleNo() {
        mainCtrl.closePopup();
    }

    public void setCallback(Runnable deleteCallback) {
        this.deleteCallback = deleteCallback;
    }
}
