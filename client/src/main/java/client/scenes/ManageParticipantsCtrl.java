package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Person;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.util.StringConverter;


/**
 * ManageParticipants screen.
 */
public class ManageParticipantsCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private ComboBox<Person> comboBox;
    @FXML
    private ListView<String> listView;
    @FXML
    private AnchorPane rootAnchorPane;
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
        Person selectedParticipant = comboBox.getValue();
        mainCtrl.showEditParticipantPopup(selectedParticipant);
    }

    @FXML
    private void deleteParticipant() {
        Person selectedParticipant = comboBox.getValue();
        mainCtrl.showDeleteParticipantConfirmationPopup(() -> {
            if (event.getExpenses().stream().anyMatch(ex ->
                    ex.getReceiver().equals(selectedParticipant)
                            || ex.getParticipants().contains(selectedParticipant)
            )) {
                // Show a modal dialog to inform the user
                Dialog<String> dialog = new Dialog<>();
                dialog.initModality(Modality.APPLICATION_MODAL); // Make the dialog modal
                dialog.initOwner(rootAnchorPane.getScene().getWindow()); // Set the owner

                // Customize the dialog appearance
                dialog.setTitle("Invalid Input Detected");
                dialog.setContentText(
                        "This Participant is participating in an expense so you cannot delete it."
                                +
                                "\nPlease choose another one.");

                // Adding a custom close button inside the dialog,
                // since default buttons are not used
                ButtonType closeButton = new ButtonType("Understood", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().add(closeButton);

                // Handling dialog result to perform actions if needed, but it's informational
                dialog.showAndWait();

                return;
            }
            event.getPeople().remove(selectedParticipant);
            server.updateEvent(event);
            server.deletePerson(selectedParticipant);
        });
    }

    @FXML
    private void cross() {
        mainCtrl.showEventOverview(event, false);
    }

    /**
     * Populate the screen.
     */
    public void populate() {
        if (event == null) {
            return;
        }
        List<Person> personList = event.getPeople();

        // initialize the Listview
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Person person : personList) {
            items.add(person.getFirstName() + " " + person.getLastName());
        }
        listView = new ListView<>(items);
        comboBox = new ComboBox<>();
        comboBox.setItems(FXCollections.observableArrayList(personList));

        // initialize the ComboBox
        comboBox.setConverter(new StringConverter<Person>() {
            @Override
            public String toString(Person person) {
                if (person == null) {
                    return "";
                } else {
                    return person.getFirstName() + " " + person.getLastName();
                }
            }

            @Override
            public Person fromString(String string) {
                return null;
            }
        });


    }

    /**
     * Update current event.
     */
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