package client.scenes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ExpenseCtrl implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label expenseNameLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private Button closeButton;

    // You can add more FXML-injected components here

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize your controller here
        // You can access FXML components and perform any necessary setup
        // For example:
        expenseNameLabel.setText("Expense Name");
        amountLabel.setText("$ 230,15");

        // You can also attach event handlers to components here
        closeButton.setOnAction(event -> handleCloseButtonClicked());
    }

    // Event handler for the close button
    private void handleCloseButtonClicked() {
        // Implement what should happen when the close button is clicked
        System.out.println("Close button clicked!");
    }
}
