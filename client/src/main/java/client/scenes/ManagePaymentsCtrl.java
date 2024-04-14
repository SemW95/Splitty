package client.scenes;

import client.utils.ScreenUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Payment;
import commons.Person;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 * Manage payments screen.
 */
public class ManagePaymentsCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private ResourceBundle resources;
    private Event event;
    @FXML
    private Pane root;
    @FXML
    private VBox itemListVbox;

    @Inject
    public ManagePaymentsCtrl(MainCtrl mainCtrl, ServerUtils server) {
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
            .setAll(event.getPayments().stream().map(this::createItem).toList());
    }

    private Pane createItem(Payment payment) {
        Pane main = new Pane();
        main.setPrefHeight(60);
        main.setPrefWidth(800);
        main.setStyle("-fx-border-color: #000000; -fx-border-width: 0 0 1 0;");

        HBox description = new HBox();
        description.setLayoutX(14);
        description.setLayoutY(14);
        description.setPrefHeight(16);
        description.setSpacing(4);

        Person from = payment.getPayer();
        Person to = payment.getReceiver();

        Label payerLabel = new Label(from.getFirstName() + " " + from.getLastName());
        payerLabel.setStyle("-fx-font-weight: bold;");
        Label paidLabel = new Label(resources.getString("manage-payments.paid"));
        Label receiverLabel = new Label(to.getFirstName() + " " + to.getLastName());
        receiverLabel.setStyle("-fx-font-weight: bold;");
        Label amountLabel = new Label("€" + payment.getAmount().toPlainString());
        amountLabel.setStyle("-fx-font-weight: bold;");

        description.getChildren().setAll(payerLabel, paidLabel, receiverLabel, amountLabel);

        Button editButton = new Button();
        editButton.setLayoutX(700);
        editButton.setLayoutY(16);
        editButton.setStyle("-fx-background-color: transparent;");
        editButton.setOnAction((e) -> mainCtrl.showEditPaymentPopup(payment, event));

        ImageView editImage = new ImageView("client/icons/edit.png");
        editImage.setFitHeight(20);
        editImage.setFitWidth(20);
        editImage.setPickOnBounds(true);
        editImage.setPreserveRatio(true);
        editButton.setGraphic(editImage);

        Button deleteButton = new Button();
        deleteButton.setLayoutX(738);
        deleteButton.setLayoutY(14);
        deleteButton.setStyle("-fx-background-color: transparent;");
        deleteButton.setOnAction((e) -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle(resources.getString("manage-payments.delete-payment"));
            dialog.setContentText(resources.getString("manage-payments.delete-confirmation"));
            ButtonType yesButton =
                new ButtonType(resources.getString("manage-payments.yes"),
                    ButtonBar.ButtonData.YES);
            ButtonType noButton =
                new ButtonType(resources.getString("manage-payments.no"),
                    ButtonBar.ButtonData.NO);
            dialog.getDialogPane().getButtonTypes().setAll(yesButton, noButton);

            dialog.showAndWait()
                .filter(response -> response.equals(yesButton))
                .ifPresent(response -> server.deletePaymentFromEvent(payment, event));
        });

        ImageView deleteImage = new ImageView("client/icons/trashcan.png");
        deleteImage.setFitHeight(24);
        deleteImage.setFitWidth(24);
        deleteImage.setPickOnBounds(true);
        deleteImage.setPreserveRatio(true);
        deleteButton.setGraphic(deleteImage);

        main.getChildren().setAll(description, editButton, deleteButton);

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