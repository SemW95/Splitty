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

import client.MyFXML;
import java.util.Locale;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * This is the main controller, which holds references to all controllers and scenes.
 */
public class MainCtrl {

    private Stage primaryStage;

    private HomeCtrl homeCtrl;
    private Scene home;
    private MyFXML fxml;

    /**
     * Main controller initialization.
     *
     * @param primaryStage the primary stage
     * @param fxml         the loaded Home controller
     * @param homePair     a pair of the home controller and node
     */
    public void initialize(Stage primaryStage, MyFXML fxml, Pair<HomeCtrl, Parent> homePair) {
        this.primaryStage = primaryStage;
        this.fxml = fxml;

        this.homeCtrl = homePair.getKey();
        this.home = new Scene(homePair.getValue());

        showHome();
        primaryStage.show();
    }

    /**
     * Sets primary stage to the Home scene.
     */
    public void showHome() {
        primaryStage.setTitle(fxml.getBundle().getString("home.title"));
        primaryStage.setScene(home);
    }

    /**
     * Changes the current language and refreshes everything.
     *
     * @param language a new language (example: "en", "lt")
     */
    public void changeLanguage(String language) {
        Locale locale = Locale.of(language);
        fxml.changeLocale(locale);

        refresh();
    }

    public String getCurrentLanguage() {
        return fxml.getCurrentLocale().toLanguageTag();
    }

    private void refresh() {
        var homePair = fxml.load(HomeCtrl.class, "client", "scenes", "Home.fxml");

        this.homeCtrl = homePair.getKey();
        this.home = new Scene(homePair.getValue());

        showHome();
    }
}