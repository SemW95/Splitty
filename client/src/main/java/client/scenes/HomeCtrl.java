/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client.scenes;

import client.Main;
import client.utils.PaneCreator;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

/**
 * Home screen.
 */
public class HomeCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ResourceBundle resources;

    @FXML
    private ComboBox<String> dropDown;

    @FXML
    private VBox eventList;

    @Inject
    public HomeCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        /*
        Makes options for the dropdown menu
        TODO should set a new server in the config file and ask to reboot
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
     * Gets called on showHome to request data.
     * It reads all stored event codes from config files and iterates through them.
     */
    public void getData() {
        String codes = Main.configManager.getCodes();
        List<Event> events = new ArrayList<>();

        if (codes != null) {
            String[] eventCodes = codes.split(",");

            for (String code : eventCodes) {
                events.add(server.getEventByCode(code));
            }
        }

        eventList.getChildren()
            .setAll(events.stream().map(PaneCreator::createEventItem).toList());
    }

    public static void handleClickEvent(Event event) {
        System.out.println("pressed " + event.getTitle());
    }

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

    /**
     * Logic for the "settings" button.
     */
    public void clickSettings() {
        System.out.println("Pressed settings.");
    }

    /**
     * Logic for the "add event" button.
     */
    public void addEvent() {
        System.out.println("Pressed add event");
    }

    @FXML
    private void clickAdminView(ActionEvent actionEvent) {
        mainCtrl.showAdminCredentialsPopup();
    }
}