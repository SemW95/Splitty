package client.scenes;

import client.utils.ScreenUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * Language Select pop-up screen.
 */
public class LanguageSelectCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private ResourceBundle resources;

    @FXML
    private Pane root;

    @Inject
    public LanguageSelectCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        root.addEventFilter(KeyEvent.KEY_PRESSED,
            ScreenUtils.exitHandler(resources, mainCtrl::closePopup));
    }

    public void setDutch() {
        mainCtrl.changeLanguage("nl");
    }

    public void setEnglish() {
        mainCtrl.changeLanguage("en");
    }

    public void setLith() {
        mainCtrl.changeLanguage("lt");
    }

    public void handleReturn() {
        mainCtrl.closePopup();
    }

}