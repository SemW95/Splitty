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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

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
    private ComboBox<Tag> tagMenu;
    private ResourceBundle resources;
    private Expense expense;

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
     * populates the UI with appropiate data from the expense object.
     */
    public void populate() {
        Event event = server.getEvents().getFirst();
        this.expense = event.getExpenses().getFirst();
        List<Tag> allTags = event.getTags();

        // Initialize UI with expense data
        expenseNameLabel.setText(expense.getDescription());
        expenseAmountLabel.setText(expense.getPaid().toString());
        tagMenu.getItems().setAll(allTags);
        List<Person> allPeople = event.getPeople();
        recipientMenu.getItems().setAll(allPeople);
        expenseDate.setPromptText(Date.from(expense.getPaymentDateTime()).toString());
        tagMenu.setCellFactory(p -> {
            return new ListCell<Tag>() {
                protected void updateItem(Tag t1, boolean empty) {
                    super.updateItem(t1, empty);
                    if (t1 != null) {
                        setText(t1.getName());
                    } else {
                        setText(null);
                    }

                }
            };
        });
        recipientMenu.setCellFactory(p -> {
            return new ListCell<Person>() {
                protected void updateItem(Person p1, boolean empty) {
                    super.updateItem(p1, empty);
                    if (p1 != null) {
                        setText(p1.getFirstName() + "-" + p1.getId());
                    } else {
                        setText(null);
                    }

                }
            };
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
        }
        );
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
        }
        );


        // Populate participants
        for (Person participant : expense.getParticipants()) {
            AnchorPane participantCard = createParticipantCard(participant);
            participantsFlowPane.getChildren().add(participantCard);
        }
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

        String participantRepresentation = "Remove " + participant.getFirstName().concat("-"
            + participant.getId());
        Label participantLabel = new Label(participantRepresentation);
        Font globalFont = new Font("System Bold", 24);
        participantLabel.setFont(globalFont);
        participantLabel.setLayoutX(12.5);
        participantLabel.setLayoutY(7.5);
        participantLabel.setOnMouseEntered(
            event -> participantLabel.setTextFill(Paint.valueOf("red")));

        participantLabel.setOnMouseExited(
            event -> participantLabel.setTextFill(Paint.valueOf("black")));
        participantLabel.setOnMouseClicked(event -> {
                this.expense.getParticipants().remove(participant);
                server.updateExpense(this.expense);
                participantLabel.setVisible(false);
                card.getChildren().remove(participantLabel);
            }
        );

        card.getChildren().add(participantLabel);
        return card;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    @FXML
    private void handleTagChange(ActionEvent actionEvent) {
        Tag selectedTag = tagMenu.getSelectionModel().getSelectedItem();
        if (selectedTag != null) {
            this.expense.setTag(selectedTag);
            server.updateExpense(this.expense);
        }
    }


    @FXML
    private void handleRecipientChange(ActionEvent actionEvent) {
        Person selectedPerson = recipientMenu.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            this.expense.setReceiver(selectedPerson);
            server.updateExpense(this.expense);
        }
    }

    @FXML
    private void handleDateChange(ActionEvent actionEvent) {
        LocalDate selectedDate = expenseDate.getValue();
        Instant selectedDateAsInstant =
            selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        if (selectedDateAsInstant != null) {
            this.expense.setPaymentDateTime(selectedDateAsInstant);
            server.updateExpense(this.expense);
        }
    }

    @FXML
    private void handleNameChange(ActionEvent actionEvent) {
        String selectedName = expenseNameLabel.getText();
        if (selectedName != null) {
            this.expense.setDescription(selectedName);
            System.out.println(selectedName);
            server.updateExpense(this.expense);
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
                String modifiedString =
                    typedAmount.substring(0, lastIndex).replace(".", "")
                        + typedAmount.substring(lastIndex, typedAmount.length());
                typedAmount = modifiedString;
            }
        }
        //7015.15 7015.15 7015.15


        BigDecimal selectedAmount = new BigDecimal(typedAmount);
        this.expense.setPaid(selectedAmount);
        server.updateExpense(this.expense);
    }

}