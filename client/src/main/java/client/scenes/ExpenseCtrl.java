package client.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ExpenseCtrl {

    @FXML
    private Label expenseNameLabel;

    @FXML
    private Label paidAmountLabel;

    @FXML
    private Label participantsCountLabel;

    @FXML
    private Button addParticipantButton;

    @FXML
    private Button manageButton;

    // Method to handle adding a participant
    @FXML
    private void onAddParticipantButtonClick() {
        // Implement functionality to add participant
        System.out.println("Add participant button clicked!");
    }

    // Method to handle managing participants
    @FXML
    private void onManageButtonClick() {
        // Implement functionality to manage participants
        System.out.println("Manage button clicked!");
    }
}
