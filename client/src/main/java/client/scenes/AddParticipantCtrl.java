package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Person;
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
    private Button cross;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField ibanTextField;

    @FXML
    private TextField bicTextField;

    @FXML
    private Label invalidEmailMessage;

    @FXML
    private Label invalidIbanMessage;

    @FXML
    private Label invalidBicMessage;

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

        if (Person.ibanCheckSum(ibanTextField.getText())) {
            iban = ibanTextField.getText();
        } else {
            invalidIbanMessage.setVisible(!invalidIbanMessage.isVisible());
        }

        if (Person.bicCheck(bicTextField.getText())) {
            bic = bicTextField.getText();
        } else {
            invalidBicMessage.setVisible(!invalidBicMessage.isVisible());
        }

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

    //    @FXML
    //    private void handleSaveButtonAction() {
    //        String inputData = inputTextField.getText();
    //
    //        try (Connection conn = DriverManager.getConnection(
    //        "jdbc:mysql://localhost:3306/your_database", "username", "password")) {
    //            String sql = "INSERT INTO your_table_name (column_name) VALUES (?)";
    //            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    //                pstmt.setString(1, inputData);
    //                pstmt.executeUpdate();
    //            }
    //        } catch (SQLException e) {
    //            e.printStackTrace();
    //        }
    //    }

}

