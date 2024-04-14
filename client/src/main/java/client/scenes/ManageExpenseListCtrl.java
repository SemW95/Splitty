package client.scenes;

import client.utils.ScreenUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;

/**
 * Controller of ManageExpenseList screen.
 */
public class ManageExpenseListCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private ComboBox<Expense> expenseMenu;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private FlowPane expenseFlowPane;
    @FXML
    private Button backButton;
    @FXML
    private Button deleteExpenseButton;
    @FXML
    private Button editExpenseButton;
    private ResourceBundle resources;
    private Event event;


    @Inject
    public ManageExpenseListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        rootAnchorPane.addEventFilter(KeyEvent.KEY_PRESSED,
            ScreenUtils.exitHandler(resources, this::handleExit));
    }

    private void handleExit() {
        mainCtrl.showEventOverview(this.event, false);

    }

    @FXML
    private void editExpense() {
        Expense selectedExpense = expenseMenu.getValue();
        if (selectedExpense == null) {
            return;
        }
        mainCtrl.showManageExpensePopup(selectedExpense, event);
    }

    @FXML
    private void deleteExpense() {
        Expense selectedExpense = expenseMenu.getValue();
        if (selectedExpense == null) {
            return;
        }
        mainCtrl.showDeleteExpenseConfirmationPopup(
            () -> server.deleteExpenseFromEvent(selectedExpense, event));
    }


    @FXML
    private void backButton() {
        mainCtrl.showEventOverview(event, false);
    }

    /**
     * Populate the screen.
     */
    public void populate() {
        if (event == null) {
            return;
        }
        List<Expense> expenseList = event.getExpenses();

        // Disable the buttons if there are no expenses
        deleteExpenseButton.setDisable(expenseList.isEmpty());
        editExpenseButton.setDisable(expenseList.isEmpty());

        expenseMenu.getSelectionModel().clearSelection();

        // Populate expense list
        expenseFlowPane.getChildren().setAll();
        for (Expense expense : expenseList) {
            AnchorPane expenseCard = createExpenseCard(expense);
            expenseFlowPane.getChildren().add(expenseCard);
        }

        // Initialize the ComboBox
        expenseMenu.getItems().setAll(expenseList);
        expenseMenu.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(Expense e1, boolean empty) {
                super.updateItem(e1, empty);
                if (e1 != null) {
                    setText(e1.getDescription());
                } else {
                    setText(null);
                }
            }
        });

        expenseMenu.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Expense expense, boolean empty) {
                super.updateItem(expense, empty);
                if (expense != null) {
                    setText(expense.getDescription());
                } else {
                    setText(null);
                }
            }
        });
    }

    private AnchorPane createExpenseCard(Expense expense) {
        AnchorPane card = new AnchorPane();
        card.setPrefSize(475, 50);
        card.setStyle(
            "-fx-border-color: lightgrey; -fx-border-width: 2px; -fx-border-radius: 5px;");

        String expenseRepresentation =
            expense.getDescription();
        Label expenseLabel = new Label(expenseRepresentation);
        Font globalFont = new Font("System Bold", 24);
        expenseLabel.setFont(globalFont);
        expenseLabel.setLayoutX(12.5);
        expenseLabel.setLayoutY(7.5);
        expenseLabel.setMaxWidth(401);


        card.getChildren().add(expenseLabel);
        return card;
    }

    /**
     * Update current event.
     */
    public void refetch() {
        if (this.event == null) {
            return;
        }
        this.event = server.getEventById(event.getId());
        populate();
    }

    public void update(Event event) {
        this.event = event;
        populate();
    }

    public Event getEvent() {
        return event;
    }

}