/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Participant screen.
 */
public class ParticipantCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ResourceBundle resources;

    @Inject
    public ParticipantCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    @FXML
    private Button addBankAccount;

    @FXML
    private ListView<?> bankAccountListView;

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
    private Label lastNameLabel;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private void editFirstName() {
        System.out.println("Edit First Name.");
        firstNameLabel.setVisible(!firstNameLabel.isVisible());
        firstNameTextField.setVisible(!firstNameTextField.isVisible());
        if (firstNameTextField.isVisible()) {
            firstNameTextField.setText(firstNameLabel.getText());
            firstNameTextField.requestFocus();
        }
    }

    @FXML
    private void editLastName() {
        System.out.println("Edit Last Name.");
        lastNameLabel.setVisible(!lastNameLabel.isVisible());
        lastNameTextField.setVisible(!lastNameTextField.isVisible());
        if (lastNameTextField.isVisible()) {
            lastNameTextField.setText(lastNameLabel.getText());
            lastNameTextField.requestFocus();
        }
    }

    @FXML
    private void editEmail() {
        System.out.println("Edit Email.");
        emailLabel.setVisible(!emailLabel.isVisible());
        emailTextField.setVisible(!emailTextField.isVisible());
        if (emailTextField.isVisible()) {
            emailTextField.setText(emailLabel.getText());
            emailTextField.requestFocus();
        }
    }

    @FXML
    private void editIban() {
        System.out.println("Edit IBAN.");
        ibanLabel.setVisible(!ibanLabel.isVisible());
        ibanTextField.setVisible(!ibanTextField.isVisible());
        if (ibanTextField.isVisible()) {
            ibanTextField.setText(ibanLabel.getText());
            ibanTextField.requestFocus();
        }
    }

    @FXML
    private void editBic() {
        System.out.println("Edit BIC.");
        bicLabel.setVisible(!bicLabel.isVisible());
        bicTextField.setVisible(!bicTextField.isVisible());
        if (bicTextField.isVisible()) {
            bicTextField.setText(bicLabel.getText());
            bicTextField.requestFocus();
        }
    }

}