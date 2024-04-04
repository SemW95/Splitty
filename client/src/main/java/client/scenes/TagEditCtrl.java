package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Colour;
import commons.Tag;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;


/**
 * Controller of TagEdit Popup.
 */
public class TagEditCtrl implements Initializable {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML
    private Button close;


    @FXML
    private TextField colourTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button save;
    @FXML
    private Pane root;

    private Colour colour;
    private Tag tag;
    private ResourceBundle resources;

    @Inject
    public TagEditCtrl(ServerUtils server, MainCtrl mainCtrl) {
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
    private void save() {
        colour = new Colour(colourTextField.getText());
        tag = new Tag(nameTextField.getText(), colour);
        // TODO: Save the Tag into repository.
    }

    @FXML
    private void close(){
        // TODO: Think about where to add this popup.
    }
}

