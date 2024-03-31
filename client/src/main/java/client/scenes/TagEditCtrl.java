package client.scenes;

import commons.Colour;
import commons.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
        colour = new Colour(colourTextField.getText());
        tag = new Tag(nameTextField.getText(), colour);
        // TODO: Save the Tag into repository.
    }

    @FXML
    private void close(){
        // TODO: Think about where to add this popup.
    }
}
