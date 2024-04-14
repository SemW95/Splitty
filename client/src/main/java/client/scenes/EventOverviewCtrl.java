package client.scenes;

import client.Main;
import client.utils.ExpenseCardCtrl;
import client.utils.PaneCreator;
import client.utils.ScreenUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import commons.Payment;
import commons.Person;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
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
    @FXML
    private Label totalAmountSpent;
    @FXML
    private HBox tagsBox;
    @FXML
    private FlowPane expensesFlowPane;
    @FXML
    private ChoiceBox<String> filterInclusionChoiceBox;
    @FXML
    private ComboBox<Person> filterNameComboBox;


    @Inject
    public EventOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        root.addEventFilter(KeyEvent.KEY_PRESSED,
            ScreenUtils.exitHandler(resources, this::handleExit));

        String all = resources.getString("event-overview.all");
        String from = resources.getString("event-overview.from");
        String including = resources.getString("event-overview.including");
        filterInclusionChoiceBox.getItems().setAll(all, from, including);
        // Select 'All' by default
        filterInclusionChoiceBox.getSelectionModel().select(0);
    }

    /**
     * This method fills the flowpane with expenses (expenseCard).
     */
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
            this.eventLastModified.setText(event.getLastModifiedDateTime()
                .toString().substring(0, 10));
        }
        if (event.getPeople() != null) {
            this.amountOfParticipants.setText(String.valueOf(event.getPeople().size()));
        }
        if (event.getCode() != null) {
            this.inviteCode.setText(event.getCode());
        }

        tagsBox.getChildren().setAll(event.getTags().stream()
            .map(PaneCreator::createTagItem).toList());

        String spent = (char) 8364 + " " + event.totalAmountSpent().toPlainString();
        totalAmountSpent.setText(spent);

        // Initialize the ComboBox
        filterNameComboBox.getSelectionModel().clearSelection();
        filterNameComboBox.getItems().setAll(event.getPeople());
        filterNameComboBox.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(Person p1, boolean empty) {
                super.updateItem(p1, empty);
                if (p1 != null) {
                    setText(p1.getFirstName() + " " + p1.getLastName());
                } else {
                    setText(null);
                }
            }
        });

        // The cell that is selected
        filterNameComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Person person, boolean empty) {
                super.updateItem(person, empty);
                if (person != null) {
                    setText(person.getFirstName() + " " + person.getLastName());
                } else {
                    setText(null);
                }
            }
        });

        displayExpenses();
    }

    @FXML
    private void displayExpenses() {
        if (event == null) {
            return;
        }

        Person nameChoice = filterNameComboBox.getSelectionModel().getSelectedItem();
        List<Expense> expenses = event.getExpenses();

        int inclusionChoice = filterInclusionChoiceBox.getSelectionModel().getSelectedIndex();

        // Disable `filterNameComboBox` if inclusion choice is 'All'
        filterNameComboBox.setDisable(inclusionChoice == 0);

        // Filter expenses only if a person was selected
        if (nameChoice != null) {
            switch (inclusionChoice) {
                // from
                case 1 -> expenses =
                    expenses.stream().filter(e -> Objects.equals(e.getReceiver(), nameChoice))
                        .toList();
                // including
                case 2 -> expenses =
                    expenses.stream().filter(e -> e.getParticipants().contains(nameChoice))
                        .toList();
                default -> {
                }
            }
        }

        // Sort expenses by their payment date
        expenses =
            expenses.stream().sorted(Comparator.comparing(Expense::getPaymentDateTime).reversed())
                .toList();

        expensesFlowPane.getChildren().setAll();
        for (Expense expense : expenses) {
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
     * Logic for the "language" button on home.
     */

    @FXML
    private void handleLanguage() {
        mainCtrl.showLanguageSelectPopup();
    }

    /**
     * Logic for the "currency" button on home.
     */
    @FXML
    private void handleCurrency() {
        System.out.println("Pressed currency.");
    }

    // TODO
    @FXML
    private void handleManageTags(ActionEvent actionEvent) {
    }

    /**
     * Logic for the "+" button next to "Expenses".
     */
    @FXML
    private void handleAddExpenses(ActionEvent actionEvent) {
        Expense expense =
            new Expense(resources.getString("manage-expenses.new-expense"), new ArrayList<>(), null,
                BigDecimal.ZERO, null,
                Instant.now());
        expense = server.createExpenseForEvent(expense, event);
        event = server.getEventById(event.getId());

        mainCtrl.showExpenseOverview(expense, event);
        mainCtrl.showManageExpensePopup(expense, event);
    }

    /**
     * Copy the invite code to the clipboard.
     */
    @FXML
    private void getInviteCode() {
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

    @FXML
    private void handleManageExpenses(ActionEvent actionEvent) {
        mainCtrl.showManageExpenseListScreen(event);
    }

    @FXML
    private void handleAddParticipants(ActionEvent actionEvent) {
        mainCtrl.showAddParticipantPopup((person) -> server.createPersonForEvent(person, event));
    }

    @FXML
    private void handleManageParticipants(ActionEvent actionEvent) {
        mainCtrl.showManageParticipantsScreen(event);
    }

    @FXML
    private void handleAddPayments(ActionEvent actionEvent) {
        Payment payment = new Payment(null, null, BigDecimal.ZERO);
        mainCtrl.showEditPaymentPopup(payment, event);
    }

    @FXML
    private void handleManagePayments(ActionEvent actionEvent) {
        mainCtrl.showManagePaymentScreen(event);
    }

    @FXML
    private void handleOpenDebts(ActionEvent actionEvent) {
        mainCtrl.showOpenDebtsScreen(event);
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

    @FXML
    private void handleStatistics(ActionEvent actionEvent) {
        mainCtrl.showStatisticsPopup(event);
    }
}