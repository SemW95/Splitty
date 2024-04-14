package client.scenes;

import client.utils.ScreenUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Payment;
import commons.Person;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 * Open debts screen.
 */
public class OpenDebtsCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private ResourceBundle resources;
    private Event event;
    @FXML
    private Pane root;
    @FXML
    private VBox itemListVbox;

    @Inject
    public OpenDebtsCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        root.addEventFilter(KeyEvent.KEY_PRESSED,
            ScreenUtils.exitHandler(resources, this::handleExit));
    }

    private void populate() {
        if (event == null) {
            return;
        }

        itemListVbox.getChildren()
            .setAll(event.calculateSettlements().stream().map(this::createItem).toList());
    }

    private VBox createItem(Payment payment) {
        Person from = payment.getPayer();
        Person to = payment.getReceiver();

        if (from == null || to == null) {
            return null;
        }

        // VBox
        VBox main = new VBox();
        main.setStyle("-fx-border-color: #000000; -fx-border-width: 1 0 1 0;");

        // Pane inside VBox
        Pane header = new Pane();
        header.setPrefHeight(80);

        // HBox inside Pane
        HBox description = new HBox();
        description.setLayoutX(14);
        description.setLayoutY(12);
        description.setPrefHeight(16);
        description.setSpacing(4);

        // Labels inside HBox
        Label payerLabel = new Label(from.getFirstName() + " " + from.getLastName());
        payerLabel.setStyle("-fx-font-weight: bold;");
        Label givesLabel = new Label(resources.getString("open-debts.gives"));
        Label amountLabel = new Label((char) 8364 + payment.getAmount().toPlainString());
        amountLabel.setStyle("-fx-font-weight: bold;");
        String toTranslation = resources.getString("open-debts.to");
        Label toLabel = new Label(toTranslation);
        Label receiverLabel = new Label(to.getFirstName() + " " + to.getLastName());
        receiverLabel.setStyle("-fx-font-weight: bold;");

        // Add labels to HBox
        if (toTranslation.isBlank()) {
            description.getChildren()
                .setAll(payerLabel, givesLabel, amountLabel, receiverLabel);
        } else {
            description.getChildren()
                .setAll(payerLabel, givesLabel, amountLabel, toLabel, receiverLabel);
        }

        Button markReceivedBtn = new Button(resources.getString("open-debts.mark-received"));
        markReceivedBtn.setLayoutX(239);
        markReceivedBtn.setLayoutY(40);
        markReceivedBtn.setPrefHeight(30);
        markReceivedBtn.setPrefWidth(160);
        markReceivedBtn.setOnAction((e) -> {
            // `payment` is the full settlement
            server.createPaymentForEvent(payment, event);
        });

        Button partialPaymentBtn = new Button(resources.getString("open-debts.partial-payment"));
        partialPaymentBtn.setLayoutX(419);
        partialPaymentBtn.setLayoutY(40);
        partialPaymentBtn.setPrefHeight(30);
        partialPaymentBtn.setPrefWidth(160);
        partialPaymentBtn.setOnAction((e) -> {
            Payment newPayment =
                new Payment(payment.getPayer(), payment.getReceiver(), BigDecimal.ZERO);
            mainCtrl.showEditPaymentPopup(newPayment, event);
        });

        // Add buttons to Pane
        header.getChildren().setAll(description, markReceivedBtn, partialPaymentBtn);

        // Content inside TitledPane
        Pane contentPane = new Pane();
        contentPane.setPrefHeight(100);

        HBox accountHolderHbox = new HBox();
        accountHolderHbox.setLayoutX(14);
        accountHolderHbox.setLayoutY(14);
        accountHolderHbox.setSpacing(4);
        Label accountHolderLabel = new Label(resources.getString("open-debts.account-holder"));
        Label accountHolderValueLabel = new Label(to.getFirstName() + " " + to.getLastName());
        accountHolderHbox.getChildren().setAll(accountHolderLabel, accountHolderValueLabel);

        HBox ibanHbox = new HBox();
        ibanHbox.setLayoutX(14);
        ibanHbox.setLayoutY(34);
        ibanHbox.setSpacing(4);
        Label ibanLabel = new Label(resources.getString("open-debts.iban"));
        Label ibanValueLabel =
            new Label(to.getIban() == null || to.getIban().isBlank() ? "-" : to.getIban());
        ibanHbox.getChildren().setAll(ibanLabel, ibanValueLabel);

        HBox bicHbox = new HBox();
        bicHbox.setLayoutX(14);
        bicHbox.setLayoutY(54);
        bicHbox.setSpacing(4);
        Label bicLabel = new Label(resources.getString("open-debts.bic"));
        Label bicValueLabel =
            new Label(to.getBic() == null || to.getBic().isBlank() ? "-" : to.getBic());
        bicHbox.getChildren().setAll(bicLabel, bicValueLabel);

        HBox emailHbox = new HBox();
        emailHbox.setLayoutX(14);
        emailHbox.setLayoutY(74);
        emailHbox.setSpacing(4);
        Label emailLabel = new Label(resources.getString("open-debts.email"));
        Label emailValueLabel =
            new Label(to.getEmail() == null || to.getEmail().isBlank() ? "-" : to.getEmail());
        emailHbox.getChildren().setAll(emailLabel, emailValueLabel);

        // Add HBoxes to contentPane
        contentPane.getChildren().setAll(accountHolderHbox, ibanHbox, bicHbox, emailHbox);

        // TitledPane
        TitledPane titledPane =
            new TitledPane(resources.getString("open-debts.additional-information"), contentPane);
        titledPane.setExpanded(false);

        // Add components to VBox
        main.getChildren().setAll(header, titledPane);

        return main;
    }

    /**
     * Updates the current event by getting the same one from the server and populates the screen.
     */
    public void refetch() {
        if (event == null) {
            return;
        }
        event = server.getEventById(event.getId());
        populate();
    }

    /**
     * Sets the current event to the provided one and populates the screen.
     *
     * @param event the event to show
     */
    public void update(Event event) {
        this.event = event;
        populate();
    }

    @FXML
    private void handleExit() {
        mainCtrl.showEventOverview(event, false);
    }

    @FXML
    private void clickCurrency() {
        // TODO
    }

    @FXML
    private void clickLanguage() {
        mainCtrl.showLanguageSelectPopup();
    }

    public Event getEvent() {
        return event;
    }
}