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


import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;


/**
 * Home screen.
 */
public class HomeCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ResourceBundle resources;

    @FXML
    private ListView<Event> listView;
    private List<Event> eventList;

    @FXML
    private ComboBox<String> dropDown;

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
        TODO should call actual servers and handle these options
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
     */
    public void getData() {
        eventList = server.getEvents();
        listView.getItems().addAll(eventList);
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