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
 * DeleteExpenseConfirmation Popup.
 */
public class DeleteExpenseConfirmationCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ResourceBundle resources;
    private Runnable callback;
    @FXML
    private AnchorPane root;

    @Inject
    public DeleteExpenseConfirmationCtrl(ServerUtils server, MainCtrl mainCtrl) {
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
    private Button deleteExpense;

    @FXML
    private Button goBackToManageScene;

    @FXML
    private void deleteExpense() {
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
