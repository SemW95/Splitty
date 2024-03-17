package client.scenes;

import commons.Expense;
import commons.Person;
import commons.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Controller class for the Expense Overview FXML UI.
 */
public class ExpenseOverviewCtrl {

    Tag tag;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private Label expenseNameLabel;
    @FXML
    private Label expenseAmountLabel;
    @FXML
    private Button addParticipantButton;
    @FXML
    private Button manageButton;
    @FXML
    private Label participantCountLabel;
    @FXML
    private Label tagLabel;
    @FXML
    private FlowPane participantsFlowPane;
    private Expense expense;

    /**
     * Initialises the UI with appropiate data from the expense object.
     */
    public void initialize() {
        // Initialize UI with expense data
        expenseNameLabel.setText(expense.getDescription());
        expenseAmountLabel.setText("€ " + expense.getPaid().toString());
        participantCountLabel.setText(Integer.toString(expense.getParticipants().size()));

        // tag and its style
        tag = expense.getTag();
        tagLabel.setText(tag.getName());
        // background color
        String backgroundColor = tag.getColour().toHexString();
        rootAnchorPane.setStyle("-fx-background-color: " + backgroundColor + ";");
        // text color with 50% brightness
        int red = tag.getColour().getRed() / 2;
        int green = tag.getColour().getGreen() / 2;
        int blue = tag.getColour().getBlue() / 2;
        Color textColor = Color.valueOf(String.format("#%02x%02x%02x", red, green, blue));
        tagLabel.setTextFill(textColor);


        // Populate participants
        for (Person participant : expense.getParticipants()) {
            AnchorPane participantCard = createParticipantCard(participant);
            participantsFlowPane.getChildren().add(participantCard);
        }
    }

    private AnchorPane createParticipantCard(Person participant) {
        AnchorPane card = new AnchorPane();
        card.setPrefSize(290, 50);
        card.setStyle(
            "-fx-border-color: lightgrey; -fx-border-width: 2px; -fx-border-radius: 5px;");

        Label participantLabel = new Label(participant.getFirstName());
        participantLabel.setFont(new Font("System Bold", 24));
        participantLabel.setLayoutX(14);
        participantLabel.setLayoutY(8);
        participantLabel.setOnMouseEntered(
            event -> participantLabel.setText("ID: " + participant.getId()));
        participantLabel.setOnMouseExited(
            event -> participantLabel.setText(participant.getFirstName()));

        card.getChildren().add(participantLabel);
        return card;
    }


    @FXML
    private void onAddParticipantClicked() {
        // Handle add participant button click
        // Implementation as per your requirements
    }

    @FXML
    private void onManageClicked() {
        // Handle manage button click
        // Implementation as per your requirements
    }

    // Other methods and event handlers as needed

    public void setExpense(Expense expense) {
        this.expense = expense;
    }
}
