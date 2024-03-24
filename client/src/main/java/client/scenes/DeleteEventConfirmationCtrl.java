package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * Delete event confirmation popup.
 */
public class DeleteEventConfirmationCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ResourceBundle resources;
    private Runnable deleteCallback;

    @Inject
    public DeleteEventConfirmationCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    @FXML
    private void handleYes() {
        if (deleteCallback != null) {
            deleteCallback.run();
        }
        mainCtrl.closePrimaryPopup();
    }

    @FXML
    private void handleNo() {
        mainCtrl.closePrimaryPopup();
    }

    public void setCallback(Runnable deleteCallback) {
        this.deleteCallback = deleteCallback;
    }
}
