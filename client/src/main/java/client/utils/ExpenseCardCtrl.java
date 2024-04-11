package client.utils;

import commons.Expense;
import commons.Person;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * ExpenseCard component.
 */
public class ExpenseCardCtrl {

    @FXML
    private Pane root;
    @FXML
    private Button mainButton;
    @FXML
    private Pane mainPane;
    @FXML
    private Label expenseDescription;
    @FXML
    private Label expenseReceiver;
    @FXML
    private Label expenseDate;
    @FXML
    private Label expenseAmount;
    @FXML
    private Label expenseParticipants;
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
            this.expenseDate.setText(expense.getPaymentDateTime().toString().substring(0, 10));
        }
        if (expense.getPaid() != null) {
            String paid = (char) 8364 + " " + expense.getPaid().toPlainString();
            this.expenseAmount.setText(paid);
        }
        if (expense.getTag() != null) {
            Label tagLabel = PaneCreator.createTagItem(expense.getTag());
            tagLabel.setLayoutX(12);
            tagLabel.setLayoutY(114);
            mainPane.getChildren().add(tagLabel);
        }
        if (expense.getParticipants() != null) {
            String firstNames =
                String.join(", ",
                    expense.getParticipants().stream().map(Person::getFirstName).toList());
            this.expenseParticipants.setText(firstNames);
        }
    }

    public void setOnClick(Consumer<Expense> consumer) {
        mainButton.setOnAction((e) -> consumer.accept(expense));
    }
}
