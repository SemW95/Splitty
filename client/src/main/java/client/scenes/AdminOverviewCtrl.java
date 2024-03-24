package client.scenes;

import client.utils.ServerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.Inject;
import commons.Event;
import commons.Tag;
import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
    private ResourceBundle resources;

    @Inject
    public AdminOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        orderByChoiceBox.getItems().addAll("Title", "Creation date", "Last modified date");
        orderByChoiceBox.getSelectionModel().selectFirst();

        directionChoiceBox.getItems().addAll("Ascending", "Descending");
        directionChoiceBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleExit(MouseEvent actionEvent) {
        mainCtrl.showHome();
    }

    /**
     * Fetches all events from the database, orders them by the specified order and direction.
     * Then creates a Pane for each event and adds them to the event list vbox.
     */
    public void populate() {
        List<Event> events = server.getEvents();

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
        Pane pane = new Pane();

        pane.setPrefHeight(150);
        pane.setPrefWidth(685);
        pane.setStyle("-fx-border-color: #000000; -fx-border-width: 0 0 1 0;");

        Label eventName = new Label(event.getTitle());
        eventName.setLayoutX(14);
        eventName.setLayoutY(14);
        eventName.setPrefHeight(21);
        eventName.setPrefWidth(300);
        eventName.setFont(Font.font(18));

        // TODO: add functionality for going to the clicked event

        Label eventCode = new Label(event.getCode());
        eventCode.setLayoutX(341);
        eventCode.setLayoutY(14);
        eventCode.setPrefHeight(21);
        eventCode.setPrefWidth(142);
        eventCode.setFont(Font.font(18));

        HBox tagsBox = new HBox();
        tagsBox.setAlignment(Pos.CENTER_LEFT);
        tagsBox.setLayoutX(14);
        tagsBox.setLayoutY(101);
        tagsBox.setPrefHeight(35);
        tagsBox.setPrefWidth(500);
        tagsBox.setSpacing(15);

        tagsBox.getChildren().setAll(event.getTags().stream().map(this::createTagItem).toList());

        Label lastModified = new Label(event.getLastModifiedDateTime().toString().substring(0, 10));
        lastModified.setLayoutX(494);
        lastModified.setLayoutY(12);
        lastModified.setPrefHeight(21);
        lastModified.setPrefWidth(150);
        lastModified.setFont(Font.font(18));
        lastModified.setAlignment(Pos.CENTER_RIGHT);

        // TODO: change users icon to date icon
        ImageView dateIcon = new ImageView(new Image("client/icons/users.png"));
        dateIcon.setLayoutX(647);
        dateIcon.setLayoutY(13);
        dateIcon.setFitHeight(24);
        dateIcon.setFitWidth(24);

        // TODO: add a label and icon for creation date

        Label usersCount = new Label(String.valueOf(event.getPeople().size()));
        usersCount.setLayoutX(614);
        usersCount.setLayoutY(47);
        usersCount.setPrefHeight(20);
        usersCount.setPrefWidth(30);
        usersCount.setFont(Font.font(18));
        usersCount.setAlignment(Pos.CENTER_RIGHT);

        ImageView usersIcon = new ImageView(new Image("client/icons/users.png"));
        usersIcon.setLayoutX(647);
        usersIcon.setLayoutY(48);
        usersIcon.setFitHeight(24);
        usersIcon.setFitWidth(24);

        Label eventDescription = new Label(event.getDescription());
        eventDescription.setLayoutX(14);
        eventDescription.setLayoutY(46);
        eventDescription.setPrefHeight(50);
        eventDescription.setPrefWidth(500);
        eventDescription.setWrapText(true);

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

        pane.getChildren()
            .addAll(eventName, eventCode, tagsBox, lastModified, dateIcon, usersCount, usersIcon,
                eventDescription, trashIcon, downloadIcon);

        return pane;
    }

    private Label createTagItem(Tag tag) {
        Label tagLabel = new Label(tag.getName());

        tagLabel.setStyle(String.format(
            "-fx-background-color: %s; "
                + "-fx-padding: 4px 8px;"
                + "-fx-border-radius: 12px;"
                + "-fx-background-radius: 12px;",
            tag.getColour().toHexString()));

        // calculate what colour the text should be depending on the background
        int red = tag.getColour().getRed();
        int green = tag.getColour().getGreen();
        int blue = tag.getColour().getBlue();
        if (red * 0.299 + green * 0.587 + blue * 0.114 > 186) {
            tagLabel.setTextFill(Color.web("#000000"));
        } else {
            tagLabel.setTextFill(Color.web("#ffffff"));
        }

        return tagLabel;
    }

    private void handleDownloadEvent(Event event) {
        try {
            // the JavaTimeModule:
            // https://stackoverflow.com/questions/47120363/java-8-date-time-types-serialized-as-object-with-spring-boot
            String eventJson =
                new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(event);

            File file = mainCtrl.createSaveFile(String.format("event_%d.json", event.getId()));
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
            populate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleEventDelete(Event event) {
        mainCtrl.showDeleteEventConfirmationPopup(() -> {
            server.deleteEvent(event, mainCtrl.getSavedAdminPassword());
            populate();
        });
    }
}