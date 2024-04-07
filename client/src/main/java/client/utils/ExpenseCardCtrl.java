package client.utils;

import commons.Expense;
import commons.Person;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * ExpenseCard component.
 */
public class ExpenseCardCtrl {

    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    Label expenseDescription;
    @FXML
    Label expenseReceiver;
    @FXML
    Label expenseDate;
    @FXML
    Label expenseAmount;
    @FXML
    Label expenseParticipants;
    private Expense expense;

    /**
     * Sets all attributes of an expense (and thus setting the expense).
     *
     * @param expense The expense that has to be set and shown on the expenseCard
     */
    public void setExpense(Expense expense) {
        this.expense = expense;

        this.expenseDescription.setText(expense.getDescription());
        if (expense.getReceiver() != null) {
            this.expenseReceiver.setText(expense.getReceiver().getFirstName());
        }
        if (expense.getPaymentDateTime() != null) {
            this.expenseDate.setText(expense.getPaymentDateTime().toString());
        }
        if (expense.getPaid() != null) {
            this.expenseAmount.setText(expense.getPaid().toString());
        }
        if (expense.getTag() != null) {
            Label tagLabel = PaneCreator.createTagItem(expense.getTag());
            tagLabel.setLayoutX(11);
            tagLabel.setLayoutY(114);
            rootAnchorPane.getChildren().add(tagLabel);
        }
        if (expense.getParticipants() != null) {
            this.expenseParticipants.setText(
                expense.getParticipants().stream().map(Person::getFirstName).toList().toString());
        }
    }

    public void setOnClick(Consumer<Expense> consumer) {
        expenseDescription.setOnMouseClicked((e) -> consumer.accept(expense));
    }
}
