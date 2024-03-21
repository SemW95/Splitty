package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 * AddParticipant screen.
 */
public class AddParticipantCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ResourceBundle resources;

    @Inject
    public AddParticipantCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    //@Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    @FXML
    private TextField bicTextField;

    @FXML
    private Button cross;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField ibanTextField;

    @FXML
    private Label invalidBicMessage;

    @FXML
    private Label invalidEmailMessage;

    @FXML
    private Label invalidIbanMessage;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private Button save;

    private String firstName;
    private String lastName;
    private String email;
    private String iban;
    private String bic;

    @FXML
    private void save() {
        firstName = firstNameTextField.getText();
        lastName = lastNameTextField.getText();

        email = emailTextField.getText();
        // TODO: Check if the email address is valid.

        iban = ibanTextField.getText();
        // TODO: Check if the IBAN is valid.

        bic = bicTextField.getText();
        // TODO: Check if the BIC is valid.

        //        if (Person.ibanCheckSum(ibanTextField.getText())) {
        //            iban = ibanTextField.getText();
        //            invalidEmailMessage.setVisible(false);
        //        } else {
        //            invalidIbanMessage.setVisible(true);
        //        }

        //        if (Person.bicCheck(bicTextField.getText())) {
        //            bic = bicTextField.getText();
        //            invalidBicMessage.setVisible(false);
        //        } else {
        //            invalidBicMessage.setVisible(true);
        //        }

        if (!invalidEmailMessage.isVisible()
                && !invalidIbanMessage.isVisible()
                && !invalidBicMessage.isVisible()) {
            // TODO: Store the new Participant in this Event.
            // TODO: Go back to the Event Overview scene.
        }

    }

    @FXML
    private void cross(){
        // TODO: Go back to the Event Overview scene.
    }

}

