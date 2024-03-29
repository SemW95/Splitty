package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Person;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 * AddParticipant screen.
 */
public class AddParticipantCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ResourceBundle resources;
    private Consumer<Person> callback;

    @Inject
    public AddParticipantCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    @FXML
    private TextField bicTextField;


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

        if (emailTextField.getText().isBlank() || Person.emailCheck(emailTextField.getText())) {
            email = emailTextField.getText();
            invalidEmailMessage.setVisible(false);
        } else {
            invalidEmailMessage.setVisible(true);
        }

        if (ibanTextField.getText().isBlank() || Person.ibanCheckSum(ibanTextField.getText())) {
            iban = ibanTextField.getText();
            invalidIbanMessage.setVisible(false);
        } else {
            invalidIbanMessage.setVisible(true);
        }

        if (bicTextField.getText().isBlank() || Person.bicCheck(bicTextField.getText())) {
            bic = bicTextField.getText();
            invalidBicMessage.setVisible(false);
        } else {
            invalidBicMessage.setVisible(true);
        }

        if (!invalidEmailMessage.isVisible()
            && !invalidIbanMessage.isVisible()
            && !invalidBicMessage.isVisible()) {
            Person person = new Person(firstName, lastName, email, iban, bic);
            callback.accept(person);
            mainCtrl.closePrimaryPopup();
        }
    }

    public void setCallback(Consumer<Person> callback) {
        this.callback = callback;
    }
}

