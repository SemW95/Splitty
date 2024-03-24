package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;

/**
 * Admin credential pop-up screen.
 */
public class AdminCredentialsCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ResourceBundle resources;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text incorrectPassword;

    @Inject
    public AdminCredentialsCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    @FXML
    private void handleEnterButton(ActionEvent actionEvent) {
        String password = passwordField.getText();
        // Clear the password field and incorrect password warning
        passwordField.clear();
        incorrectPassword.setText("");

        // TODO: make a get request to /admin/validate/{password}

        if (server.validateAdminPassword(password)) {
            mainCtrl.setSavedAdminPassword(password);
            mainCtrl.closeCurrentPopup();
            mainCtrl.showAdminOverview();
        } else {
            // Show a warning to the user that the password was incorrect
            incorrectPassword.setText("Password incorrect");
        }
    }

    public boolean savedPasswordIsCorrect() {
        if (mainCtrl.getSavedAdminPassword() == null) {
            return false;
        }
        return server.validateAdminPassword(mainCtrl.getSavedAdminPassword());
    }
}