package client.scenes;


import client.Main;
import client.components.ExpenseCardCtrl;
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
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
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
    @FXML
    private Label eventNameLabel; // TODO: change this such that label is seen when changing the name and otherwise text
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
    }

    /**
     * This method fills the flowpane with expenses (expenseCard).
     */
    public void refresh(Event event){
        this.event = event;
        if(event.getTitle() != null){
            this.eventNameLabel.setText(event.getTitle());
        }
        if(event.getDescription() != null){
            this.eventDescription.setText(event.getDescription());
        }
        // TODO: start and enddate
        if(event.getLastModifiedDateTime() != null){
            this.eventLastModified.setText(event.getLastModifiedDateTime().toString());
        }
        if(event.getPeople() != null){
            this.amountOfParticipants.setText(event.getPeople().toString());
        }

        for (Expense expense : event.getExpenses()) {
            var expenseCard = Main.FXML.loadComponent(ExpenseCardCtrl.class, "client", "components", "ExpenseCard.fxml");
            expenseCard.getKey().setExpense(expense);
            expensesFlowPane.getChildren().add(expenseCard.getValue());
        }
    }

//    /**
//     * Gets called on showHome to request data.
//     */
//    public void getData() {
//        eventList = server.getEvents();
//        listView.getItems().addAll(eventList);
//    }

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
     * Logic for the "language" button on home.
     */
    public void clickLanguage() {
        System.out.println("Pressed language");
        testing();
    }

    /**
     * Logic for the "currency" button on home.
     */
    public void clickCurrency() {
        System.out.println("Pressed currency.");
    }

    /**
     * Logic for the home title.
     */
    public void clickHome() {
        System.out.println("Pressed home.");
    }

    // TODO
    public void handleLanguageClick(MouseEvent mouseEvent) {
    }

    // TODO
    public void handleCurrencyClick(MouseEvent mouseEvent) {
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

    // TODO: go to add participants
    public void handleAddParticipants(ActionEvent actionEvent) {
    }

    // TODO: go to manage participants
    public void handleManageParticipants(ActionEvent actionEvent) {
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

}