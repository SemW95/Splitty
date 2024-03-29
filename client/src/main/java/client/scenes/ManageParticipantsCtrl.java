package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Person;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;


/**
 * ManageParticipants screen.
 */
public class ManageParticipantsCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ResourceBundle resources;
    private Event event;

    @Inject
    public ManageParticipantsCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    @FXML
    private Button deleteParticipant;

    @FXML
    private Button editParticipant;

    @FXML
    private void editParticipant() {
        // TODO: get selected person
        Person person = event.getPeople().getFirst();
        mainCtrl.showEditParticipantPopup(person);
    }

    @FXML
    private void deleteParticipant() {
        // TODO: get selected person
        Person person = event.getPeople().getFirst();
        mainCtrl.showDeleteParticipantConfirmationPopup(() -> {
            if (event.getExpenses().stream().anyMatch(ex ->
                ex.getReceiver().equals(person) || ex.getParticipants().contains(person)
            )) {
                // TODO: show an alert that says that you cannot delete a participant which
                // is participating in an expense
                return;
            }
            event.getPeople().remove(person);
            server.updateEvent(event);
            server.deletePerson(person);
            mainCtrl.updateAll();
        });
    }

    // @FXML
    // private void save() {
    // }

    @FXML
    private void cross() {
        mainCtrl.showEventOverview(event, false);
    }

    public void populate() {
        // TODO
    }

    public void refetch() {
        if (this.event == null) {
            return;
        }
        this.event = server.getEventById(event.getId());
        populate();
    }

    public void update(Event event) {
        this.event = event;
        populate();
    }
}