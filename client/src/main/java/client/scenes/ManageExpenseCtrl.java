package client.scenes;

import client.utils.ScreenUtils;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
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
    private Label confirmAmount;

    @FXML
    private Label confirmDate;

    @FXML
    private Label confirmName;

    @FXML
    private Label confirmRecipient;

    @FXML
    private Label confirmTag;


    @FXML
    private Label confirmLastDeleted;

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

        rootAnchorPane.addEventFilter(KeyEvent.KEY_PRESSED,
            ScreenUtils.exitHandler(resources, () -> {
                if (amountHasCorrectSyntax()) {
                    mainCtrl.closePopup();
                } else {
                    showUnsavedChangesDialog();
                }
            }));
    }


    /**
     * populates the UI with appropriate data from the expense object.
     */
    public void populate() {
        if (expense == null || event == null) {
            return;
        }

        List<Tag> allTags = event.getTags();

        // Initialize UI with expense data
        expenseNameLabel.setText(expense.getDescription());
        expenseAmountLabel.setText(expense.getPaid().toString());

        tagMenu.getSelectionModel().clearSelection();
        recipientMenu.getSelectionModel().clearSelection();

        tagMenu.getItems().setAll(allTags);
        recipientMenu.getItems().setAll(event.getPeople());

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

        if (expense.getTag() != null) {
            tagMenu.getSelectionModel().select(expense.getTag());
        }
        if (expense.getReceiver() != null) {
            recipientMenu.getSelectionModel().select(expense.getReceiver());
        }


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
        if (expense.getReceiver() != null) {
            participantsFlowPane.getChildren().add(createRecipientCard(expense.getReceiver()));
            System.out.println("Created a recipient card instead of a normal participant card");
        }

        for (Person participant : expense.getParticipants()) {
            System.out.println("HERE");
            System.out.println(participant);
            participantsFlowPane.getChildren().add(createParticipantCard(participant));
            System.out.println("Created a regular participant card");
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
    private AnchorPane createRecipientCard(Person participant) {
        AnchorPane card = new AnchorPane();
        card.setPrefSize(350, 50);
        card.setStyle(
            "-fx-border-color: lightgrey; -fx-border-width: 2px; -fx-border-radius: 5px;");

        String participantRepresentation =
            participant.getFirstName() + " " + participant.getLastName();
        System.out.println(participant.getId());
        System.out.println(expense.getReceiver().getId());
        participantRepresentation +=
            " (" + resources.getString("manage-expense.recipient") + ")";
        Label participantLabel = new Label(participantRepresentation);
        participantLabel.setMaxWidth(276);
        Font globalFont = new Font("System Bold", 24);
        participantLabel.setFont(globalFont);
        participantLabel.setLayoutX(12.5);
        participantLabel.setLayoutY(7.5);


        participantLabel.setTextFill(Color.valueOf("#636363"));
        ImageView lockedImage = new ImageView(new Image("client/icons/locked.png"));
        lockedImage.setLayoutX(301);
        lockedImage.setLayoutY(13);
        lockedImage.setFitHeight(24);
        lockedImage.setFitWidth(24);
        card.getChildren().add(lockedImage);


        card.getChildren().add(participantLabel);
        return card;
    }

    /**
     * Creates a new Participant card for the dynamically scaled FlowPane.
     *
     * @param participant The participant
     * @return An anchor pane
     */
    private AnchorPane createParticipantCard(Person participant) {
        AnchorPane card = new AnchorPane();
        card.setFocusTraversable(true);
        card.setMouseTransparent(false);

        card.setPrefSize(475, 50);
        card.setStyle(
            "-fx-border-color: lightgrey; -fx-border-width: 2px; -fx-border-radius: 5px;");

        String participantRepresentation =
            "Remove " + participant.getFirstName() + " " + participant.getLastName();
        System.out.println(participant.getId());
        System.out.println(expense.getReceiver().getId());
        Label participantLabel = new Label(participantRepresentation);
        participantLabel.setFocusTraversable(true);
        Font globalFont = new Font("System Bold", 24);
        participantLabel.setFont(globalFont);
        participantLabel.setLayoutX(12.5);
        participantLabel.setLayoutY(7.5);
        participantLabel.setMaxWidth(276);
        if (participantLabel.isVisible()) {
            participantLabel.setOnMouseEntered(
                event -> participantLabel.setTextFill(Paint.valueOf("red")));

            participantLabel.setOnMouseExited(
                event -> participantLabel.setTextFill(Paint.valueOf("black")));
        }


        card.setOnMousePressed(event -> {
            this.expense.getParticipants().remove(participant);
            participantsFlowPane.getChildren().remove(card);
            participantsFlowPane.requestLayout();
            server.updateExpense(this.expense);
            confirmLastDeleted.setText(
                confirmLastDeleted.getText() + "\nRemoved " + participant.getFirstName()
                    +
                    " from this expense successfully.");
        });

        card.getChildren().add(participantLabel);
        return card;
    }

    @FXML
    private void handleNameChange(ActionEvent actionEvent) {
        String selectedName = expenseNameLabel.getText();
        if (selectedName != null) {
            if (selectedName.equals(expense.getDescription())) {
                return;
            }
            indicatorNameModified.setImage(new Image("/client/icons/edit_done.png"));

            setConfirmationLabels();
            this.expense.setDescription(selectedName);

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
                typedAmount = typedAmount.substring(0, lastIndex).replace(".", "")
                    +
                    typedAmount.substring(lastIndex);
            }
        }
        //7015.15 7015.15 7015.15
        char[] inChars = typedAmount.toCharArray();
        for (char c : inChars) {
            if (!Character.isDigit(c) && (c != '.')) {
                //invalid
                indicatorAmountModified.setImage(new Image("client/icons/edit_invalid.png"));

                setConfirmationLabels();
                return;
            }
        }


        BigDecimal selectedAmount = new BigDecimal(typedAmount);
        if (selectedAmount.equals(expense.getPaid())) {
            indicatorAmountModified.setImage(new Image("client/icons/edit_done.png"));
            setConfirmationLabels();
            return;
        }
        indicatorAmountModified.setImage(new Image("client/icons/edit_done.png"));
        setConfirmationLabels();
        this.expense.setPaid(selectedAmount);
        expenseAmountLabel.setText(selectedAmount.toPlainString());
        server.updateExpense(this.expense);
    }


    @FXML
    private void handleRecipientChange(ActionEvent actionEvent) {
        Person previousRecipient = this.expense.getReceiver();
        Person selectedPerson = recipientMenu.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            //already recipient
            if (selectedPerson.equals(expense.getReceiver())) {
                return;
            }
            //new recipient
            indicatorRecipientModified.setImage(new Image("client/icons/edit_done.png"));

            setConfirmationLabels();
            this.expense.setReceiver(selectedPerson);
            //if they used to be a participant, they are no longer, if they used to be an
            // event candidate, they are and won't be a participant, either way, make sure
            // the recipient is not in list of participants
            this.expense.getParticipants().remove(selectedPerson);
            if (previousRecipient != null) {
                this.expense.getParticipants().add(previousRecipient);
            }
            server.updateExpense(this.expense);
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

            setConfirmationLabels();
            this.expense.setPaymentDateTime(selectedDateAsInstant);
            server.updateExpense(this.expense);
        }
    }


    @FXML
    private void handleTagChange(ActionEvent actionEvent) {
        Tag selectedTag = tagMenu.getSelectionModel().getSelectedItem();
        if (selectedTag != null) {
            if (selectedTag.equals(expense.getTag())) {
                return;
            }
            indicatorTagModified.setImage(new Image("client/icons/edit_done.png"));
            setConfirmationLabels();
            this.expense.setTag(selectedTag);
            server.updateExpense(this.expense);
        }
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
            dialog.setTitle(resources.getString("manage-expense.invalid-input"));
            dialog.setContentText(resources.getString("manage-expense.invalid-info"));

            // Adding a custom close button inside the dialog, since default buttons are not used
            ButtonType closeButton =
                new ButtonType(resources.getString("manage-expense.understood"),
                    ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(closeButton);

            // Handling dialog result to perform actions if needed, but it's informational
            dialog.showAndWait();
        }
    }

    private void showUnsavedChangesDialog() {
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

    private boolean amountHasCorrectSyntax() {
        String s = expenseAmountLabel.getText();
        System.out.println(s);
        char[] inChars = s.toCharArray();
        for (char c : inChars) {
            if (!Character.isDigit(c) && (c != '.')) {
                //invalid
                System.err.println("    " + c + " is not a digit or a dot");

                indicatorAmountModified.setImage(new Image("client/icons/edit_invalid.png"));
                setConfirmationLabels();
                return false;
            }
        }
        return true;
    }

    /**
     * Updates the loaded expense and event.
     */
    public void refetch() {
        if (this.expense == null || this.event == null) {
            return;
        }
        this.expense = server.getExpenseById(expense.getId());
        this.event = server.getEventById(event.getId());
        populate();
    }

    /**
     * Set what the screen should show.
     *
     * @param expense the expense
     * @param event   the event
     */
    public void update(Expense expense, Event event) {
        this.expense = expense;
        this.event = event;
        populate();
    }

    /**
     * Sets all the labels confirming changes made by a user.
     */
    public void setConfirmationLabels() {
        if (indicatorNameModified.getImage().getUrl().contains("done")) {
            confirmName.setText("Changed Successfully.");
        }
        if (indicatorAmountModified.getImage().getUrl().contains("done")) {
            confirmAmount.setText("Changed Successfully.");
        } else if (indicatorAmountModified.getImage().getUrl().contains("invalid")) {
            confirmAmount.setText("Invalid Syntax!");
        }
        if (indicatorDateModified.getImage().getUrl().contains("done")) {
            confirmDate.setText("Changed Successfully.");
        }
        if (indicatorTagModified.getImage().getUrl().contains("done")) {
            confirmTag.setText("Changed Successfully.");
        }
        if (indicatorRecipientModified.getImage().getUrl().contains("done")) {
            confirmRecipient.setText("Changed Successfully.");
        }


    }

    /**
     * Sets the default for the status icons and status labels.
     */
    public void defaultStatus() {
        indicatorAmountModified.setImage(new Image("/client/icons/edit_idle.png"));
        indicatorDateModified.setImage(new Image("/client/icons/edit_idle.png"));
        indicatorNameModified.setImage(new Image("/client/icons/edit_idle.png"));
        indicatorRecipientModified.setImage(new Image("/client/icons/edit_idle.png"));
        indicatorTagModified.setImage(new Image("/client/icons/edit_idle.png"));
        confirmRecipient.setText("");
        confirmName.setText("");
        confirmAmount.setText("");
        confirmDate.setText("");
        confirmTag.setText("");
    }
}