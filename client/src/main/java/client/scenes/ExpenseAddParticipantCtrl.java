package client.scenes;

import client.utils.PaneCreator;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import commons.Person;
import commons.Tag;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ExpenseAddParticipantCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private Label expenseNameLabel;

    @FXML
    private FlowPane availableParticipants;

    @FXML
    private FlowPane currentParticipants;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label tagLabel;
    private ResourceBundle resources;
    private Expense expense;
    private Event event;

    @Inject
    public ExpenseAddParticipantCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    /**
     * populates the UI with appropiate data from the expense object.
     */
    public void populate() {
        Event event = server.getEvents().getFirst();
        this.expense = event.getExpenses().getFirst();

        // Initialize UI with expense data
        expenseNameLabel.setText(expense.getDescription());

        // Create tag
        expenseNameLabel.setGraphic(PaneCreator.createTagItem(expense.getTag()));


        currentParticipants.getChildren().setAll();
        availableParticipants.getChildren().setAll();

        // Populate available participants
        for (Person participant : event.getPeople()) {
            if (!expense.getParticipants().contains(participant) && !participant.equals(expense.getReceiver())) {
                addParticipantCardToDualFlowPane(availableParticipants, currentParticipants, participant);
            }
        }

        // Populate current participants
        for (Person participant : expense.getParticipants()) {
            addParticipantCardToDualFlowPane(currentParticipants, availableParticipants, participant);
        }


    }

    /**
     * Createse a new Participant card for the dynamically scaled FlowPane, and allows switching to other flowpane.
     *
     * @param flowPane
     * @param complementFlowPane
     * @param participant
     *
     * @return An anchor pane associated to the new card.
     */
    private void addParticipantCardToDualFlowPane(FlowPane flowPane, FlowPane complementFlowPane, Person participant) {
        AnchorPane card = new AnchorPane();
        card.setPrefSize(475, 50);
        card.setStyle(
            "-fx-border-color: lightgrey; -fx-border-width: 2px; -fx-border-radius: 5px;");

        String participantRepresentation = participant.getFirstName().concat("#"
            + participant.getId().substring(0,8));
        Label participantLabel = new Label(participantRepresentation);
        Font globalFont = new Font("System Bold", 24);
        participantLabel.setFont(globalFont);
        participantLabel.setLayoutX(12.5);
        participantLabel.setLayoutY(7.5);
        participantLabel.setOnMousePressed(event -> {
                if (this.expense.getParticipants().contains(participant)) {
                    //push to available participant pane and remove from expense
                    this.expense.getParticipants().remove(participant);
                    flowPane.getChildren().remove(card);
                    complementFlowPane.getChildren().add(card);
                } else {
                    //push to current participant pane and remove from available and add to expense
                    this.expense.getParticipants().add(participant);
                    flowPane.getChildren().add(card);
                    complementFlowPane.getChildren().remove(card);
                }
                flowPane.requestLayout();
                complementFlowPane.requestLayout();
                server.updateExpense(this.expense);
                mainCtrl.updateAll();
            }
        );

        card.getChildren().add(participantLabel);
        flowPane.getChildren().add(card);
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
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
}
