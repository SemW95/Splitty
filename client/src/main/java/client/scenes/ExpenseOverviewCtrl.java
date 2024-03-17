package client.scenes;

import commons.Expense;
import commons.Person;
import commons.Tag;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Controller class for the Expense Overview FXML UI.
 */
public class ExpenseOverviewCtrl implements Initializable {


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
    private Tag tag;
    private ResourceBundle resources;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    /**
     * populates the UI with appropiate data from the expense object.
     */
    public void populate() {
        // Initialize UI with expense data
        expenseNameLabel.setText(expense.getDescription());
        expenseAmountLabel.setText("€ " + expense.getPaid().toString());
        participantCountLabel.setText(Integer.toString(expense.getParticipants().size()));

        // tag and its style
        tag = expense.getTag();
        tagLabel.setText(tag.getName());


        // new background version
        var oldFills = tagLabel.getBackground().getFills();
        var newFills = new ArrayList<>(oldFills);
        newFills.add(new BackgroundFill(Color.web(tag.getColour().toHexString()), null, null));
        //TODO make sure the background radius property from the css in expenseoverview fxml is not overwritten by this background set call

        tagLabel.setBackground(new Background(newFills, null));

        // text color with 50% brightness
        int red = tag.getColour().getRed();
        int green = tag.getColour().getGreen();
        int blue = tag.getColour().getBlue();
        if (red * 0.299 + green * 0.587 + blue * 0.114 > 186) {
            tagLabel.setTextFill(Color.web("#000000"));
        } else {
            tagLabel.setTextFill(Color.web("#ffffff"));
        }


        // Populate participants
        for (Person participant : expense.getParticipants()) {
            AnchorPane participantCard = createParticipantCard(participant);
            participantsFlowPane.getChildren().add(participantCard);
        }
    }

    /**
     * Creates a new Participant card for the dynamically scaled FlowPane.
     * @param participant
     * @return
     */
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
        // TODO go to add participant UI
        System.out.println("Pressed add participant button.");
    }

    @FXML
    private void onManageClicked() {
        // TODO go to EDIT expense UI
        System.out.println("Pressed currency.");
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }
}
