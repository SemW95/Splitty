package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Person;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * EditParticipant popup.
 */
public class EditParticipantCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ResourceBundle resources;

    @Inject
    public EditParticipantCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    @FXML
    private Label bicLabel;

    @FXML
    private TextField bicTextField;

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

    private Person person;

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

    @FXML
    private void save() {
        System.out.println("Save.");
        if (firstNameTextField.isVisible()) {
            person.setFirstName(firstNameTextField.getText());
            server.updatePerson(person);
            populate();
            mainCtrl.updateAll();
            firstNameTextField.setVisible(false);
            firstNameLabel.setVisible(true);
        }
        if (lastNameTextField.isVisible()) {
            person.setLastName(lastNameTextField.getText());
            server.updatePerson(person);
            populate();
            mainCtrl.updateAll();
            lastNameTextField.setVisible(false);
            lastNameLabel.setVisible(true);
        }
        if (emailTextField.isVisible()) {
            if (emailTextField.getText().isBlank() || Person.emailCheck(emailTextField.getText())) {
                person.setEmail(emailTextField.getText());
                server.updatePerson(person);
                populate();
                mainCtrl.updateAll();
                emailTextField.setVisible(false);
                emailLabel.setVisible(true);
                invalidEmailMessage.setVisible(false);
            } else {
                invalidEmailMessage.setVisible(true);
            }
        }
        if (ibanTextField.isVisible()) {
            if (ibanTextField.getText().isBlank() || Person.ibanCheckSum(ibanTextField.getText())) {
                person.setIban(ibanTextField.getText());
                server.updatePerson(person);
                populate();
                mainCtrl.updateAll();
                ibanTextField.setVisible(false);
                ibanLabel.setVisible(true);
                invalidIbanMessage.setVisible(false);
            } else {
                invalidIbanMessage.setVisible(true);
            }
        }
        if (bicTextField.isVisible()) {
            if (bicTextField.getText().isBlank() || Person.bicCheck(bicTextField.getText())) {
                person.setBic(bicTextField.getText());
                server.updatePerson(person);
                populate();
                mainCtrl.updateAll();
                bicTextField.setVisible(false);
                bicLabel.setVisible(true);
                invalidBicMessage.setVisible(false);
            } else {
                invalidBicMessage.setVisible(true);
            }
        }
    }

    public void populate() {
        firstNameLabel.setText(person.getFirstName());
        lastNameLabel.setText(person.getLastName());
        emailLabel.setText(person.getEmail());
        ibanLabel.setText(person.getIban());
        bicLabel.setText(person.getBic());
    }

    public void refetch() {
        if (this.person == null) {
            return;
        }
        this.person = server.getPersonById(person.getId());
        populate();
    }

    public void update(Person person) {
        this.person = person;
        populate();
    }
}
