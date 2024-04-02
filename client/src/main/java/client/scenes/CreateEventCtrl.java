package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import commons.Person;
import jakarta.inject.Inject;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateEventCtrl {

    private ServerUtils server;
    private MainCtrl mainCtrl;
    @FXML
    private TextField eventName;
    @FXML
    private TextField eventDescription;
    private String name;
    private String description;
    @FXML
    private Label invalidFieldsMessage;
    private Consumer<Event> callback;

    @Inject
    public CreateEventCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @FXML
    private void save() {
        if (eventName.getText().isBlank() || eventDescription.getText().isBlank()) {
            name = eventName.getText();
            description = eventDescription.getText();
            invalidFieldsMessage.setVisible(false);
        } else {
            invalidFieldsMessage.setVisible(true);
        }
        
        Event event = new Event(name, description);
        callback.accept(event);

    }

    public void handleCross(ActionEvent actionEvent) {
    }

    public void handleCancel(ActionEvent actionEvent) {
    }

    public void handleCreate(ActionEvent actionEvent) {
    }

    public void setCallback(Consumer<Event> callback) {
        this.callback = callback;
    }

}
