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

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;


/**
 * Main controller for scenes.
 */
public class MainCtrl {
    /*
    How to insert a new page: (you can use home as reference)
    Make sure you created a controller and your Page.fxml links:"client.scenes.PageCtrl"
    Fields:
    Step 1: Initialize 2 fields, "PageCtrl pageCtrl" and "Scene page"
    Constructor:
    Step 2: add a "Pair [PageController, Parent] page" ( [] = <>) parameter.
    Step 3: this.pageCtrl = page.getKey() + this.page = new Scene(page.getValue()).
    Step 4: Create a function showPage() for handling transition. setScene + setTitle.
    MyModule:
    Step 5: add controller to MyModule "binder.bind(PageCtrl.class).in(Scopes.SINGLETON);"
    Main
    Step 6: add "var page = FXML.load(PageCtrl.class, "client", "scenes", "Home.fxml");"
    */
    private Stage primaryStage;

    private HomeCtrl homeCtrl;
    private Scene home;
    //step 1 below.

    /**
     * Main controller initialization.
     *
     * @param primaryStage the primary stage
     * @param home the loaded Home controller
     */
    public void initialize(Stage primaryStage, Pair<HomeCtrl, Parent> home) {
        this.primaryStage = primaryStage;
        this.homeCtrl = home.getKey();
        this.home = new Scene(home.getValue());
        //add step 2 and 3 below.

        showHome();
        primaryStage.show();
    }

    /**
     * Sets primary stage to the Home scene.
     */
    public void showHome() {
        primaryStage.setTitle("Home");
        primaryStage.setScene(home);
    }

    //add step 4 here.
}