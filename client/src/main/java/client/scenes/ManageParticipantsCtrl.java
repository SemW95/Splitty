package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
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
    private Button cross;

    @FXML
    private Button deleteParticipant;

    @FXML
    private Button editParticipant;

    @FXML
    private Button save;

    @FXML
    private void editParticipant() {
        // TODO: Popup EditParticipant.
    }

    @FXML
    private void deleteParticipant() {
        // TODO: Popup DeleteParticipantConfirmation.
    }

    @FXML
    private void save() {
        // TODO: save the current information
    }

    @FXML
    private void cross() {
        // TODO: Go back to the Event Overview scene.
    }


}