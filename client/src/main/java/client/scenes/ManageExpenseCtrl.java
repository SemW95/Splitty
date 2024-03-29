package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import commons.Person;
import commons.Tag;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;

/**
 * Controls the Expense UI.
 */
public class ManageExpenseCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private TextField expenseAmountLabel;

    @FXML
    private DatePicker expenseDate;

    @FXML
    private TextField expenseNameLabel;

    @FXML
    private ComboBox<Person> recipientMenu;

    @FXML
    private FlowPane participantsFlowPane;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private ImageView indicatorAmountModified;

    @FXML
    private ImageView indicatorDateModified;

    @FXML
    private ImageView indicatorNameModified;

    @FXML
    private ImageView indicatorRecipientModified;

    @FXML
    private ImageView indicatorTagModified;

    @FXML
    private ComboBox<Tag> tagMenu;
    private ResourceBundle resources;
    private Expense expense;
    private Event event;

    @Inject
    public ManageExpenseCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    /**
     * populates the UI with appropriate data from the expense object.
     */
    public void populate() {
        List<Tag> allTags = event.getTags();

        // Initialize UI with expense data
        expenseNameLabel.setText(expense.getDescription());
        expenseAmountLabel.setText(expense.getPaid().toString());
        tagMenu.getItems().setAll(allTags);
        List<Person> allPeople = event.getPeople();
        recipientMenu.getItems().setAll(allPeople);
        expenseDate.setPromptText(Date.from(expense.getPaymentDateTime()).toString());
        tagMenu.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(Tag t1, boolean empty) {
                super.updateItem(t1, empty);
                if (t1 != null) {
                    setText(t1.getName());
                } else {
                    setText(null);
                }
            }
        });
        recipientMenu.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(Person p1, boolean empty) {
                super.updateItem(p1, empty);
                if (p1 != null) {
                    setText(p1.getFirstName() + "-" + p1.getId());
                } else {
                    setText(null);
                }
            }
        });

        tagMenu.getSelectionModel().select(expense.getTag());
        recipientMenu.getSelectionModel().select(expense.getReceiver());


        tagMenu.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Tag tag, boolean empty) {
                super.updateItem(tag, empty);
                if (tag != null) {
                    setText(tag.getName());
                } else {
                    setText(null);
                }
            }
        });
        recipientMenu.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Person person, boolean empty) {
                super.updateItem(person, empty);
                if (person != null) {
                    setText(person.getFirstName() + "-" + person.getLastName());
                } else {
                    setText(null);
                }
            }
        });


        // Populate participants
        participantsFlowPane.getChildren().setAll();
        for (Person participant : expense.getParticipants()) {
            participantsFlowPane.getChildren().add(createParticipantCard(participant));
        }

        // Prevent window closure when there are unsaved changes with invalid syntax
        rootAnchorPane.getScene().getWindow()
            .addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::handleCloseRequest);
    }

    /**
     * Creates a new Participant card for the dynamically scaled FlowPane.
     *
     * @param participant The participant
     * @return An anchor pane
     */
    private AnchorPane createParticipantCard(Person participant) {
        AnchorPane card = new AnchorPane();
        card.setPrefSize(475, 50);
        card.setStyle(
            "-fx-border-color: lightgrey; -fx-border-width: 2px; -fx-border-radius: 5px;");

        String participantRepresentation =
            "Remove " + participant.getFirstName() + " " + participant.getLastName();
        Label participantLabel = new Label(participantRepresentation);
        Font globalFont = new Font("System Bold", 24);
        participantLabel.setFont(globalFont);
        participantLabel.setLayoutX(12.5);
        participantLabel.setLayoutY(7.5);
        if (participantLabel.isVisible()) {
            participantLabel.setOnMouseEntered(
                event -> participantLabel.setTextFill(Paint.valueOf("red")));

            participantLabel.setOnMouseExited(
                event -> participantLabel.setTextFill(Paint.valueOf("black")));
        }

        participantLabel.setOnMousePressed(event -> {
                this.expense.getParticipants().remove(participant);
                participantsFlowPane.getChildren().remove(card);
                participantsFlowPane.requestLayout();
                server.updateExpense(this.expense);
                mainCtrl.updateAll();
            }
        );

        card.getChildren().add(participantLabel);
        return card;
    }

    @FXML
    private void handleTagChange(ActionEvent actionEvent) {
        Tag selectedTag = tagMenu.getSelectionModel().getSelectedItem();
        if (selectedTag != null) {
            if (selectedTag.equals(expense.getTag())) {
                return;
            }
            indicatorTagModified.setImage(new Image("client/icons/edit_done.png"));
            this.expense.setTag(selectedTag);
            server.updateExpense(this.expense);
            mainCtrl.updateAll();
        }
    }


    @FXML
    private void handleRecipientChange(ActionEvent actionEvent) {
        Person selectedPerson = recipientMenu.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            if (selectedPerson.equals(expense.getReceiver())) {
                return;
            }
            indicatorRecipientModified.setImage(new Image("client/icons/edit_done.png"));
            this.expense.setReceiver(selectedPerson);
            server.updateExpense(this.expense);
            mainCtrl.updateAll();
        }
    }

    @FXML
    private void handleDateChange(ActionEvent actionEvent) {
        LocalDate selectedDate = expenseDate.getValue();
        Instant selectedDateAsInstant =
            selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        if (selectedDateAsInstant != null) {
            if (selectedDate.equals(
                LocalDate.ofInstant(expense.getPaymentDateTime(), ZoneId.systemDefault()))) {
                return;
            }
            indicatorDateModified.setImage(new Image("client/icons/edit_done.png"));
            this.expense.setPaymentDateTime(selectedDateAsInstant);
            server.updateExpense(this.expense);
            mainCtrl.updateAll();
        }
    }

    @FXML
    private void handleNameChange(ActionEvent actionEvent) {
        String selectedName = expenseNameLabel.getText();
        if (selectedName != null) {
            if (selectedName.equals(expense.getDescription())) {
                return;
            }
            indicatorNameModified.setImage(new Image("client/icons/edit_done.png"));
            this.expense.setDescription(selectedName);

            server.updateExpense(this.expense);
            mainCtrl.updateAll();
        }
    }

    @FXML
    private void handleAmountChange(ActionEvent actionEvent) {
        //70.15.15 7015.15 70,15.15
        String typedAmount = expenseAmountLabel.getText().replace(",", ".");
        //70.15.15 7015.15 70.15.15
        if (typedAmount.contains(".")) {
            int lastIndex = typedAmount.lastIndexOf('.');
            if (lastIndex != -1) { // Check if a dot exists
                typedAmount = typedAmount.substring(0, lastIndex).replace(".", "")
                    + typedAmount.substring(lastIndex);
            }
        }
        //7015.15 7015.15 7015.15
        char[] inChars = typedAmount.toCharArray();
        for (char c : inChars) {
            if (!Character.isDigit(c) && (c != '.')) {
                //invalid
                indicatorAmountModified.setImage(new Image("client/icons/edit_invalid.png"));
                return;
            }
        }


        BigDecimal selectedAmount = new BigDecimal(typedAmount);
        if (selectedAmount.equals(expense.getPaid())) {
            return;
        }
        indicatorAmountModified.setImage(new Image("client/icons/edit_done.png"));
        this.expense.setPaid(selectedAmount);
        expenseAmountLabel.setText(selectedAmount.toPlainString());
        server.updateExpense(this.expense);
        mainCtrl.updateAll();
    }

    private void handleCloseRequest(WindowEvent event) {
        if (!amountHasCorrectSyntax()) {
            // Prevent the window from closing
            event.consume();

            // Show a modal dialog to inform the user
            Dialog<String> dialog = new Dialog<>();
            dialog.initModality(Modality.APPLICATION_MODAL); // Make the dialog modal
            dialog.initOwner(rootAnchorPane.getScene().getWindow()); // Set the owner

            // Customize the dialog appearance
            dialog.setTitle("Invalid Input Detected");
            dialog.setContentText(
                "You have unsaved changes with invalid syntax."
                    +
                    "\nPlease review that you have entered a valid amount of money.");

            // Adding a custom close button inside the dialog, since default buttons are not used
            ButtonType closeButton = new ButtonType("Understood", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(closeButton);

            // Handling dialog result to perform actions if needed, but it's informational
            dialog.showAndWait();
        }
    }

    private boolean amountHasCorrectSyntax() {
        String s = expenseAmountLabel.getText();
        System.out.println(s);
        char[] inChars = s.toCharArray();
        for (char c : inChars) {
            if (!Character.isDigit(c) && (c != '.')) {
                //invalid
                System.err.println("    " + c + " is not a digit or a dot");

                indicatorAmountModified.setImage(new Image("client/icons/edit_invalid.png"));
                return false;
            }
        }
        return true;
    }

    public void refetch() {
        if (this.expense == null || this.event == null) {
            return;
        }
        this.expense = server.getExpenseById(expense.getId());
        this.event = server.getEventById(event.getId());
        populate();
    }

    public void update(Expense expense, Event event) {
        this.expense = expense;
        this.event = event;
        populate();
    }
}