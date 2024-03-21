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
 * EditParticipant popup.
 */
public class EditParticipantCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ResourceBundle resources;

    @Inject
    public EditParticipantCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    //@Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    @FXML
    private Label bicLabel;

    @FXML
    private TextField bicTextField;

    @FXML
    private Button cross;

    @FXML
    private Button editBic;

    @FXML
    private Button editEmail;

    @FXML
    private Button editFirstName;

    @FXML
    private Button editIban;

    @FXML
    private Button editLastName;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label firstNameLabel;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private Label ibanLabel;

    @FXML
    private TextField ibanTextField;

    @FXML
    private Label invalidBicMessage;

    @FXML
    private Label invalidEmailMessage;

    @FXML
    private Label invalidIbanMessage;

    @FXML
    private Label lastNameLabel;

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
    private void editFirstName() {
        System.out.println("Edit First Name.");
        firstNameLabel.setVisible(false); // Hide the Label
        firstNameTextField.setVisible(true); // Show the TextField
        firstNameTextField.requestFocus(); // Set focus to TextField
    }

    @FXML
    private void editLastName() {
        System.out.println("Edit Last Name.");
        lastNameLabel.setVisible(false); // Hide the Label
        lastNameTextField.setVisible(true); // Show the TextField
        lastNameTextField.requestFocus(); // Set focus to TextField

    }

    @FXML
    private void editEmail() {
        System.out.println("Edit Email.");
        emailLabel.setVisible(false); // Hide the Label
        emailTextField.setVisible(true); // Show the TextField
        emailTextField.requestFocus(); // Set focus to TextField
    }

    @FXML
    private void editIban() {
        System.out.println("Edit IBAN.");
        ibanLabel.setVisible(false); // Hide the Label
        ibanTextField.setVisible(true); // Show the TextField
        ibanTextField.requestFocus(); // Set focus to TextField
    }

    @FXML
    private void editBic() {
        System.out.println("Edit BIC.");
        bicLabel.setVisible(false); // Hide the Label
        bicTextField.setVisible(true); // Show the TextField
        bicTextField.requestFocus(); // Set focus to TextField
    }

    @SuppressWarnings("checkstyle:EmptyBlock")
    @FXML
    private void save() {
        System.out.println("Save.");
        if (firstNameTextField.isVisible()) {
            firstName = firstNameTextField.getText();
        }
        if (lastNameTextField.isVisible()) {
            lastName = lastNameTextField.getText();
        }
        if (emailTextField.isVisible()) {
            // TODO: Check if the email address is valid
            email = emailTextField.getText();
        }
        if (ibanTextField.isVisible()) {
            // TODO: Check if the IBAN is valid.
            iban = ibanTextField.getText();
        }
        if (bicTextField.isVisible()) {
            // TODO: Check if the BIC is valid.
            bic = bicTextField.getText();
        }
        //        if (ibanTextField.isVisible()) {
        //            if (Person.ibanCheckSum(ibanTextField.getText())) {
        //                iban = ibanTextField.getText();
        //                invalidIbanMessage.setVisible(false);
        //            } else {
        //                invalidIbanMessage.setVisible(true);
        //            }
        //        }
        //        if (bicTextField.isVisible()) {
        //            if (Person.bicCheck(bicTextField.getText())) {
        //                bic = bicTextField.getText();
        //                invalidBicMessage.setVisible(false);
        //            } else {
        //                invalidBicMessage.setVisible(true);
        //            }
        //        }
        if (!invalidEmailMessage.isVisible()
                && !invalidIbanMessage.isVisible()
                && !invalidBicMessage.isVisible()) {
            // TODO: Store the updated Participant in this Event.
            // TODO: Go back to the ManageParticipants scene.
        }
    }

    @FXML
    private void cross() {
        // TODO: Go back to the ManageParticipants scene.
    }

}
