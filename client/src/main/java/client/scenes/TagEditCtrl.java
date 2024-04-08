package client.scenes;

import client.utils.ScreenUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Colour;
import commons.Tag;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;


/**
 * Controller of TagEdit Popup.
 */
public class TagEditCtrl implements Initializable {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML
    private TextField colourTextField;

    @FXML
    private TextField nameTextField;

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

        root.addEventFilter(KeyEvent.KEY_PRESSED,
            ScreenUtils.exitHandler(resources, mainCtrl::closePopup));
    }

    @FXML
    private void save() {
        colour = new Colour(colourTextField.getText());
        tag = new Tag(nameTextField.getText(), colour);
        // TODO: Save the Tag into repository.
    }

    @FXML
    private void close() {
        // TODO: Think about where to add this popup.
    }
}

