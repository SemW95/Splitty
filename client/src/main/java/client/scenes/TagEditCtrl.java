package client.scenes;

import commons.Colour;
import commons.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller of TagEdit Popup.
 */
public class TagEditCtrl {
    @FXML
    private Button close;

    @FXML
    private TextField colourTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button save;

    private Colour colour;
    private Tag tag;

    @FXML
    private void save() {
        //TODO
    }


}

