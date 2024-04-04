package client.scenes;

import client.utils.PaneCreator;
import client.utils.ServerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.Inject;
import commons.Event;
import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Admin overview screen.
 */
public class AdminOverviewCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private ChoiceBox<String> orderByChoiceBox;
    @FXML
    private ChoiceBox<String> directionChoiceBox;
    @FXML
    private VBox eventList;
    @FXML
    private Pane root;
    private ResourceBundle resources;
    private List<Event> events;

    @Inject
    public AdminOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        String title = resources.getString("admin-overview.by-title");
        String creationDate = resources.getString("admin-overview.creation-date");
        String lastModifiedDate = resources.getString("admin-overview.last-modified-date");
        orderByChoiceBox.getItems().addAll(title, creationDate, lastModifiedDate);
        //TODO: The order choice box gets called on "weird" occasions and creates errors.
        //orderByChoiceBox.getSelectionModel().selectFirst();

        String ascending = resources.getString("admin-overview.ascending");
        String descending = resources.getString("admin-overview.descending");
        directionChoiceBox.getItems().addAll(ascending, descending);
        //TODO: The order choice box gets called on "weird" occasions and creates errors.
        //directionChoiceBox.getSelectionModel().selectFirst();

        root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                // Creating a confirmation dialog
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirmation");
                confirmAlert.setHeaderText(null); // Optional: No header
                confirmAlert.setContentText("You have pressed Escape, "
                    +
                    "\nare you sure you want to go back?");

                // This will show the dialog and wait for the user response
                Optional<ButtonType> result = confirmAlert.showAndWait();

                // Checking the user's decision
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // If user clicks OK, then perform the action to go back/close
                    handleExit(); // Now handleExit() is called only after user confirmation
                }
                event.consume(); // Prevents the event from propagating further
            }
        });
    }

    @FXML
    private void handleExit() {
        mainCtrl.showHome();
    }

    /**
     * Fetches all events from the database, orders them by the specified order and direction.
     * Then creates a Pane for each event and adds them to the event list vbox.
     */
    public void refetch() {
        events = server.getEvents();

        populate();
    }

    /**
     * Populate the screen.
     */
    public void populate() {
        if (events == null) {
            return;
        }
        int orderByIndex = orderByChoiceBox.getSelectionModel().getSelectedIndex();
        switch (orderByIndex) {
            case 0 -> events.sort(Comparator.comparing(Event::getTitle));
            case 1 -> events.sort(Comparator.comparing(Event::getStartDate));
            case 2 -> events.sort(Comparator.comparing(Event::getLastModifiedDateTime));
            default -> System.err.println("Unknown order by option was selected");
        }

        int directionIndex = directionChoiceBox.getSelectionModel().getSelectedIndex();
        if (directionIndex == 1) {
            events = events.reversed();
        }

        eventList.getChildren()
            .setAll(events.stream().map(this::createEventItem).toList());
    }

    private Pane createEventItem(Event event) {
        ImageView trashIcon = new ImageView(new Image("client/icons/trashcan.png"));
        trashIcon.setLayoutX(647);
        trashIcon.setLayoutY(84);
        trashIcon.setFitHeight(24);
        trashIcon.setFitWidth(24);
        trashIcon.setCursor(Cursor.HAND);
        trashIcon.setPickOnBounds(true);
        trashIcon.setPreserveRatio(true);
        trashIcon.setOnMouseClicked((e) -> handleEventDelete(event));

        ImageView downloadIcon = new ImageView(new Image("client/icons/download.png"));
        downloadIcon.setLayoutX(644);
        downloadIcon.setLayoutY(112);
        downloadIcon.setFitHeight(30);
        downloadIcon.setFitWidth(30);
        downloadIcon.setCursor(Cursor.HAND);
        downloadIcon.setPickOnBounds(true);
        downloadIcon.setPreserveRatio(true);
        downloadIcon.setOnMouseClicked((e) -> handleDownloadEvent(event));

        Pane pane = PaneCreator.createEventItem(event, (e) -> mainCtrl.showEventOverview(e, true));

        pane.getChildren().addAll(trashIcon, downloadIcon);

        return pane;
    }

    private void handleDownloadEvent(Event event) {
        try {
            // the JavaTimeModule:
            // https://stackoverflow.com/questions/47120363/java-8-date-time-types-serialized-as-object-with-spring-boot
            String eventJson =
                new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(event);

            File file = mainCtrl.createSaveFile(String.format("event_%s.json", event.getId()));
            if (file == null) {
                return;
            }

            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println(eventJson);
            }

            System.out.println("Saved an event into file " + file.getName());

            // Copy it to the clipboard maybe?
            // Clipboard clipboard = Clipboard.getSystemClipboard();
            // ClipboardContent content = new ClipboardContent();
            // content.putString(eventJson);
            // clipboard.setContent(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleImportEvent(ActionEvent actionEvent) {
        File file = mainCtrl.openSavedFile();
        if (file == null) {
            return;
        }
        try {
            String eventJson = Files.readString(file.toPath());
            Event event =
                new ObjectMapper().registerModule(new JavaTimeModule())
                    .readValue(eventJson, Event.class);
            server.createEvent(event);
            // TODO: could use optimistic ui
            refetch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleEventDelete(Event event) {
        mainCtrl.showDeleteEventConfirmationPopup(() -> {
            server.deleteEvent(event, mainCtrl.getSavedAdminPassword());
            mainCtrl.updateAll();
        });
    }
}