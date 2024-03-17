package client.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

public class ExpenseCtrl {

    @FXML
    private Label expenseNameLabel;

    @FXML
    private Label paidAmountLabel;

    @FXML
    private Label participantsCountLabel;

    @FXML
    private Button addButton;

    @FXML
    private Button manageButton;

    @FXML
    private Button closeButton;

    // This method will be invoked when the add participant button is clicked
    @FXML
    private void onAddParticipantButtonClick(ActionEvent event) {
        // Implement functionality to handle add participant button click
        System.out.println("Add Participant button clicked!");
    }

    // This method will be invoked when the manage button is clicked
    @FXML
    private void onManageButtonClick(ActionEvent event) {
        // Implement functionality to handle manage button click
        System.out.println("Manage button clicked!");
    }

    // This method will be invoked when the close button is clicked
    @FXML
    private void onCloseButtonClick(ActionEvent event) {
        // Implement functionality to handle close button click
        System.out.println("Close button clicked!");
    }

    // Method to update the UI with expense details
    public void updateExpenseDetails(String name, String amount, int participantsCount) {
        expenseNameLabel.setText(name);
        paidAmountLabel.setText(amount);
        participantsCountLabel.setText(Integer.toString(participantsCount));
    }
}
