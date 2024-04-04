package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
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

    @FXML
    private Pane root;

    @Inject
    public AdminCredentialsCtrl(ServerUtils server, MainCtrl mainCtrl) {
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
                    mainCtrl.closePopup();
                }
                event.consume(); // Prevents the event from propagating further
            }
        });
    }

    @FXML
    private void handleEnterButton(ActionEvent actionEvent) {
        String password = passwordField.getText();
        // Clear the password field and incorrect password warning
        passwordField.clear();
        incorrectPassword.setText("");

        if (server.validateAdminPassword(password)) {
            mainCtrl.setSavedAdminPassword(password);
            mainCtrl.closePopup();
            mainCtrl.showAdminOverview();
        } else {
            // Show a warning to the user that the password was incorrect
            incorrectPassword.setText(resources.getString("admin-credentials.incorrect-password"));
        }
    }

    /**
     * Checks if the saved admin password is correct.
     *
     * @return whether the saved password is correct
     */
    public boolean savedPasswordIsCorrect() {
        if (mainCtrl.getSavedAdminPassword() == null) {
            return false;
        }
        return server.validateAdminPassword(mainCtrl.getSavedAdminPassword());
    }
}