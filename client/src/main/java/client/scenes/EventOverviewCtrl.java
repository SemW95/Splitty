package client.scenes;


import client.Main;
import client.utils.ExpenseCardCtrl;
import client.utils.ScreenUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


// TODO: add a way such that the tags of the event can be changed in quantity, colour and text

/**
 * EventOverview screen.
 */
public class EventOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ResourceBundle resources;
    private Event event;
    private boolean goBackToAdmin;
    // TODO: change this such that label is seen when changing the name and otherwise text
    @FXML
    private Label eventNameLabel;
    @FXML
    public Text eventNameText;
    @FXML
    private Text eventDescription;
    @FXML
    private Label eventDates;
    @FXML
    private Label eventLastModified;
    @FXML
    private Label amountOfParticipants;
    // TODO: make tags a component and add them + make field

    @FXML
    private ComboBox<String> dropDown;

    @FXML
    private FlowPane expensesFlowPane;

    @FXML
    private Pane root;

    @Inject
    public EventOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        /*
        Makes options for the dropdown menu
        TODO should call actual servers and handle these options
        */
        ObservableList<String> options = FXCollections.observableArrayList(
            "Server 1",
            "Server 2",
            "Server 3"
        );
        dropDown.setValue("Server 1");
        dropDown.setItems(options);

        root.addEventFilter(KeyEvent.KEY_PRESSED,
            ScreenUtils.exitHandler(resources, this::handleExit));
    }

    /**
     * This method fills the flowpane with expenses (expenseCard).
     */
    // TODO: Make this pretty in the UI
    public void populate() {
        if (event == null) {
            return;
        }
        if (event.getTitle() != null) {
            this.eventNameLabel.setText(event.getTitle());
        }
        if (event.getTitle() != null) {
            this.eventNameText.setText(event.getTitle());
        }
        if (event.getDescription() != null) {
            this.eventDescription.setText(event.getDescription());
        }
        if (event.getStartDate() != null & event.getEndDate() != null) {
            String dates = event.getStartDate().toString() + " - " + event.getEndDate().toString();
            this.eventDates.setText(dates);
        }
        if (event.getLastModifiedDateTime() != null) {
            this.eventLastModified.setText(event.getLastModifiedDateTime().toString());
        }
        if (event.getPeople() != null) {
            this.amountOfParticipants.setText(event.getPeople().toString());
        }

        expensesFlowPane.getChildren().setAll();
        for (Expense expense : event.getExpenses()) {
            var expenseCard = Main.FXML.loadComponent(ExpenseCardCtrl.class,
                "client", "components", "ExpenseCard.fxml");
            expenseCard.getKey().setExpense(expense);
            expenseCard.getKey().setOnClick((e) -> mainCtrl.showExpenseOverview(e, event));
            expensesFlowPane.getChildren().add(expenseCard.getValue());
        }
    }

    /**
     * Updates the loaded event.
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

    /**
     * Testing function for language switch.
     */
    public void testing() {
        if (mainCtrl.getCurrentLanguage().equals("en")) {
            mainCtrl.changeLanguage("lt");
        } else {
            mainCtrl.changeLanguage("en");
        }
    }

    /**
     * Logic for the home title.
     */
    public void handleHome() {
        handleExit();
    }

    /**
     * Logic for the "language" button on home.
     */

    public void handleLanguage() {
        System.out.println("Pressed language");
        testing();
    }

    /**
     * Logic for the "currency" button on home.
     */
    public void handleCurrency() {
        System.out.println("Pressed currency.");
    }

    public void clickChangeEventName(MouseEvent mouseEvent) {
        // TODO: use the following to control the visibility of the label and the textLabel.
        // eventNameLabel.setVisible(false); // Hide the Label
        // eventNameText.setVisible(true); // Show the TextField
        // eventNameText.requestFocus(); // Set focus to TextField
        // TODO: use the content in the textField by emailTextField.getText() to save in the
        //  repository and display on the label
    }

    // TODO
    public void handleManageTags(ActionEvent actionEvent) {
    }

    /**
     * Logic for the "+" button next to "Expenses".
     */
    public void handleAddExpenses(ActionEvent actionEvent) {
        System.out.println("Pressed add expense.");
    }

    // TODO: go to manage expenses
    public void handleManageExpenses(ActionEvent actionEvent) {
    }

    @FXML
    private void handleAddParticipants(ActionEvent actionEvent) {
        mainCtrl.showAddParticipantPopup((person) -> {
            person = server.createPerson(person);
            event.getPeople().add(person);
            server.updateEvent(event);
            mainCtrl.updateAll();
        });
    }

    // TODO: go to manage participants
    public void handleManageParticipants(ActionEvent actionEvent) {
        mainCtrl.showManageParticipantsScreen(event);
    }

    // TODO: go to add payments
    public void handleAddPayments(ActionEvent actionEvent) {
    }

    // TODO: go to manage payments
    public void handleManagePayments(ActionEvent actionEvent) {
    }

    // TODO: go to Open Debts
    public void handleOpenDebts(ActionEvent actionEvent) {
    }

    // TODO: go to Paid Off Debts
    public void handlePaidOffDebts(ActionEvent actionEvent) {
    }

    @FXML
    private void handleExit() {
        if (goBackToAdmin) {
            mainCtrl.showAdminOverview();
            goBackToAdmin = false;
        } else {
            mainCtrl.showHome();
        }
    }

    public void setGoBackToAdmin(boolean goBackToAdmin) {
        this.goBackToAdmin = goBackToAdmin;
    }

    // TODO
    public void handleCopyInviteCode(ActionEvent actionEvent) {

    }

    public Event getEvent() {
        return event;
    }

    public boolean getGoBackToAdmin() {
        return goBackToAdmin;
    }
}