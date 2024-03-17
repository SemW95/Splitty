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
//import commons.Colour;
//import commons.Expense;
//import commons.Person;
//import commons.Tag;
//import java.math.BigDecimal;
//import java.time.Instant;
//import java.util.ArrayList;
import java.util.Locale;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * This is the main controller, which holds references to all controllers and scenes.
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
    Step 7: add the same to the refresh method. Also add more stuff there too. Kind of TODO
    */
    private Stage primaryStage;

    private HomeCtrl homeCtrl;
    private Scene home;
    private ExpenseOverviewCtrl expenseOverviewCtrl;
    private Scene expenseOverview;
    private MyFXML fxml;
    //step 1 below.

    /**
     * Main controller initialization.
     *
     * @param primaryStage the primary stage
     * @param fxml         the loaded Home controller
     * @param homePair     a pair of the home controller and node
     */
    public void initialize(Stage primaryStage, MyFXML fxml, Pair<HomeCtrl, Parent> homePair,
                           Pair<ExpenseOverviewCtrl, Parent> expenseOverviewPair) {
        this.primaryStage = primaryStage;
        this.fxml = fxml;

        //add step 2 and 3 below.
        this.homeCtrl = homePair.getKey();
        this.home = new Scene(homePair.getValue());

        this.expenseOverviewCtrl = expenseOverviewPair.getKey();
        this.expenseOverview = new Scene(expenseOverviewPair.getValue());

        showHome();
        primaryStage.show();

        //        Wing debug dummy expense scene

        //        primaryStage.setScene(expenseOverview);
        //
        //        Instant now = Instant.now();
        //
        //        Person person1 = new Person("Alice", "needs a surname", "Alice@domain.com",
        //            "AL35202111090000000001234567",
        //            "ZUOBJEO6XXX");
        //        Person person2 = new Person("John", "needs a surname", "Alice@domain.com",
        //            "AD1400080001001234567890",
        //            "ZUOBJEO6XXX");
        //        Person person3 = new Person("Henry", "needs a surname", "henry@domain.com",
        //            "AD1400080001001234567890",
        //            "ZUOBJEO9XXX");
        //
        //        Tag tag1 = new Tag("Food", new Colour("#0000FF"));
        //
        //        ArrayList<Person> participants = new ArrayList<>();
        //        participants.add(person1);
        //        participants.add(person2);
        //        participants.add(person3);
        //
        //        Expense expense1 =
        //            new Expense("Food", participants, person1, new BigDecimal(14.00), tag1, now);
        //        expenseOverviewCtrl.setExpense(expense1);
        //        expenseOverviewCtrl.populate();
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
        // STEP 7
        // TODO: maybe find a way to remove the code duplication
        var homePair = fxml.load(HomeCtrl.class, "client", "scenes", "Home.fxml");
        var expenseOverviewPair =
            fxml.load(ExpenseOverviewCtrl.class, "client", "scenes", "ExpenseOverview.fxml");

        this.homeCtrl = homePair.getKey();
        this.home = new Scene(homePair.getValue());

        this.expenseOverviewCtrl = expenseOverviewPair.getKey();
        this.expenseOverview = new Scene(expenseOverviewPair.getValue());

        showHome();
    }
    //add step 4 here.
}