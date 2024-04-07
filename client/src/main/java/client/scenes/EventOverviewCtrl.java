package client.scenes;


import client.Main;
import client.utils.ExpenseCardCtrl;
import client.utils.ScreenUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;


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
    private TextField eventNameTextField;
    @FXML
    private Label eventDates;
    @FXML
    private Label eventLastModified;
    @FXML
    private Label amountOfParticipants;
    @FXML
    private Label descriptionLabel;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private Label inviteCode;
    @FXML
    private Pane root;

    // TODO: make tags a component and add them + make field

    @FXML
    private ComboBox<String> dropDown;

    @FXML
    private FlowPane expensesFlowPane;


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
            this.eventNameTextField.setText(event.getTitle());
        }
        if (event.getDescription() != null) {
            this.descriptionLabel.setText(event.getDescription());
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
        if (event.getCode() != null) {
            this.inviteCode.setText(event.getCode());
        }

        List<Expense> sortedExpenses =
            event.getExpenses().stream()
                .sorted(Comparator.comparing(Expense::getPaymentDateTime).reversed())
                .toList();

        expensesFlowPane.getChildren().setAll();
        for (Expense expense : sortedExpenses) {
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

    // TODO
    public void handleManageTags(ActionEvent actionEvent) {
    }

    // TODO

    /**
     * Logic for the "+" button next to "Expenses".
     */
    public void handleAddExpenses(ActionEvent actionEvent) {
        Expense expense = new Expense("New expense", new ArrayList<>(), null, BigDecimal.ZERO, null,
            Instant.now());
        expense = server.createExpense(expense);
        event.getExpenses().add(expense);
        server.updateEvent(event);
        mainCtrl.showExpenseOverview(expense, event);
        mainCtrl.showManageExpensePopup(expense, event);
    }

    /**
     * Copy the invite code to the clipboard.
     */
    @FXML
    public void getInviteCode() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(inviteCode.getText());
        clipboard.setContent(content);
    }

    @FXML
    private void editEventName() {
        System.out.println("Edit Event Name.");
        eventNameLabel.setVisible(false); // Hide the Label
        eventNameTextField.setVisible(true); // Show the TextField
        eventNameTextField.setText(eventNameLabel.getText()); // Set the initialized text
        eventNameTextField.requestFocus(); // Set focus to TextField

        // Set a key event handler for eventNameTextField
        eventNameTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    handleEditAndSaveEventName();
                    // Save and switch back to Label display when Enter is pressed
                }
            }
        });
    }

    private void handleEditAndSaveEventName() {
        // Get the content of textField and update EventName
        String newName = eventNameTextField.getText();
        eventNameLabel.setText(newName);
        event.setTitle(newName);
        server.updateEvent(event);
        populate();
        eventNameLabel.setVisible(true); // Show the Label
        eventNameTextField.setVisible(false); // Hide the TextField
    }

    @FXML
    private void editDescription() {
        System.out.println("Edit Description.");
        descriptionLabel.setVisible(false); // Hide the Label
        descriptionTextField.setVisible(true); // Show the TextField
        descriptionTextField.setText(descriptionLabel.getText()); // Set the initialized text
        descriptionTextField.requestFocus(); // Set focus to TextField

        // Set a key event handler for descriptionTextField
        descriptionTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    handleEditAndSaveDescription();
                    // Save and switch back to Label display when Enter is pressed
                }
            }
        });
    }


    private void handleEditAndSaveDescription() {
        // Get the content of textField and update description
        String newDescription = descriptionTextField.getText();
        descriptionLabel.setText(newDescription);
        event.setDescription(newDescription);
        server.updateEvent(event);
        populate();
        descriptionLabel.setVisible(true); // Show the Label
        descriptionTextField.setVisible(false); // Hide the TextField
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
        });
    }

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

    public Event getEvent() {
        return event;
    }

    public boolean getGoBackToAdmin() {
        return goBackToAdmin;
    }
}