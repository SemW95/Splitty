package client.components;

import commons.Expense;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ExpenseCardCtrl {

    @FXML
    Label expenseDescription;
    @FXML
    Label expenseReceiver;
    @FXML
    Label expenseDate;
    @FXML
    Label expenseAmount;
    @FXML
    Button expenseTag;
    @FXML
    Label expenseParticipants;

    public void setExpense(Expense expense) {
        this.expenseDescription.setText(expense.getDescription());
        if(expense.getReceiver() != null){
            this.expenseReceiver.setText(expense.getReceiver().getFirstName());
        }
        if (expense.getPaymentDateTime() != null){
            this.expenseDate.setText(expense.getPaymentDateTime().toString());
        }
        if(expense.getPaid() != null){
            this.expenseAmount.setText(expense.getPaid().toString());
        }
        if(expense.getTag() != null){
            this.expenseTag.setText(expense.getTag().toString());
        }
        if(expense.getParticipants() != null){
            this.expenseParticipants.setText(expense.getParticipants().stream().map(p -> p.getFirstName()).toList().toString());
        }
    }
}
