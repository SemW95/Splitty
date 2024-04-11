package client.scenes;

import client.utils.ScreenUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Payment;
import commons.Person;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;


/**
 * Edit payment popup.
 */
public class EditPaymentCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private ResourceBundle resources;
    private Event event;
    private Payment payment;
    @FXML
    private Pane root;
    @FXML
    private ComboBox<Person> fromComboBox;
    @FXML
    private ComboBox<Person> toComboBox;
    @FXML
    private TextField amountTextField;

    @Inject
    public EditPaymentCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        root.addEventFilter(KeyEvent.KEY_PRESSED,
            ScreenUtils.exitHandler(resources, this::handleCancel));
    }

    private void populate() {
        if (payment == null || event == null) {
            return;
        }

        fromComboBox.getSelectionModel().clearSelection();
        toComboBox.getSelectionModel().clearSelection();
        amountTextField.setText("");

        fromComboBox.setCellFactory(p -> new ListCell<>() {
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

        fromComboBox.setButtonCell(new ListCell<>() {
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

        toComboBox.setCellFactory(p -> new ListCell<>() {
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

        toComboBox.setButtonCell(new ListCell<>() {
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

        fromComboBox.getItems().setAll(event.getPeople());
        toComboBox.getItems().setAll(event.getPeople());

        if (payment.getPayer() != null) {
            fromComboBox.getSelectionModel().select(payment.getPayer());
        }
        if (payment.getReceiver() != null) {
            toComboBox.getSelectionModel().select(payment.getReceiver());
        }
        if (payment.getAmount() == null) {
            payment.setAmount(BigDecimal.ZERO);
        }

        amountTextField.setText(payment.getAmount().toPlainString());
    }

    /**
     * Sets the current event to the provided one and populates the screen.
     *
     * @param event the event to show
     */
    public void update(Payment payment, Event event) {
        this.payment = payment;
        this.event = event;
        populate();
    }

    @FXML
    private void handleCancel() {
        mainCtrl.closePopup();
    }

    private static boolean amountIsValid(String amountString) {
        try {
            BigDecimal amount = new BigDecimal(amountString);
            return amount.signum() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    @FXML
    private void handleSave() {
        Person from = fromComboBox.getSelectionModel().getSelectedItem();
        Person to = toComboBox.getSelectionModel().getSelectedItem();
        if (from == null || to == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                resources.getString("edit-payment.from-or-to-equals-null"));
            alert.showAndWait();
            return;
        }
        if (Objects.equals(from, to)) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                resources.getString("edit-payment.from-equals-to"));
            alert.showAndWait();
            return;
        }
        String amountString = amountTextField.getText().trim();
        if (!amountIsValid(amountString)) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                resources.getString("edit-payment.invalid-amount"));
            alert.showAndWait();
            return;
        }

        payment.setAmount(new BigDecimal(amountString));
        payment.setPayer(from);
        payment.setReceiver(to);
        if (server.getPaymentById(payment.getId()) == null) {
            payment = server.createPayment(payment);
            event.getPayments().add(payment);
            server.updateEvent(event);
        } else {
            server.updatePayment(payment);
        }
        mainCtrl.closePopup();
    }

    public Event getEvent() {
        return event;
    }
}