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
import javafx.stage.Modality;
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
    private Stage adminCredentialsPopup;
    private HomeCtrl homeCtrl;
    private Scene home;
    private AdminCredentialsCtrl adminCredentialsCtrl;
    private Scene adminCredentials;
    private ExpenseOverviewCtrl expenseOverviewCtrl;
    private Scene expenseOverview;
    //private ParticipantCtrl participantCtrl;
    //private Scene participant;
    private MyFXML fxml;
    //step 1 below.

    /**
     * Main controller initialization.
     *
     * @param primaryStage         the primary stage
     * @param fxml                 MyFXML class
     * @param homePair             a pair of the home controller and node
     * @param adminCredentialsPair a pair of the admin credentials controller and node
     * @param expenseOverviewPair  a pair of the expense overview controller and node
     */
    public void initialize(Stage primaryStage, MyFXML fxml, Pair<HomeCtrl, Parent> homePair,
                           Pair<AdminCredentialsCtrl, Parent> adminCredentialsPair,
                           Pair<ExpenseOverviewCtrl, Parent> expenseOverviewPair) {
        this.primaryStage = primaryStage;
        this.fxml = fxml;

        //add step 2 and 3 below.
        this.homeCtrl = homePair.getKey();
        this.home = new Scene(homePair.getValue());

        this.adminCredentialsCtrl = adminCredentialsPair.getKey();
        this.adminCredentials = new Scene(adminCredentialsPair.getValue());

        this.expenseOverviewCtrl = expenseOverviewPair.getKey();
        this.expenseOverview = new Scene(expenseOverviewPair.getValue());

        showHome();
        primaryStage.show();

        //        TODO Make the expense control fetch data from database and delete this!
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
        homeCtrl.getData();
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
        this.homeCtrl = homePair.getKey();
        this.home = new Scene(homePair.getValue());

        var adminCredentialsPair =
            fxml.load(AdminCredentialsCtrl.class, "client", "scenes", "AdminCredentials.fxml");
        this.adminCredentialsCtrl = adminCredentialsPair.getKey();
        this.adminCredentials = new Scene(adminCredentialsPair.getValue());

        var expenseOverviewPair =
            fxml.load(ExpenseOverviewCtrl.class, "client", "scenes", "ExpenseOverview.fxml");
        this.expenseOverviewCtrl = expenseOverviewPair.getKey();
        this.expenseOverview = new Scene(expenseOverviewPair.getValue());

        showHome();
    }

    /**
     * Creates an admin credential popup (dialog window) that blocks other windows.
     * Should never be called twice before closing one of the popups.
     */
    public void showAdminCredentialsPopup() {
        adminCredentialsPopup = new Stage();
        // Set it to block other windows (you can only click on this popup)
        adminCredentialsPopup.initModality(Modality.APPLICATION_MODAL);
        adminCredentialsPopup.initOwner(primaryStage);
        adminCredentialsPopup.setTitle("Admin credentials");
        adminCredentialsPopup.setScene(adminCredentials);
        // Making it not resizable also sets it to the size specified in the .fxml file
        // This was the only way I found that fixed that problem
        // (Except the .setMaximized(true), which makes the window flash when it appears)
        // Also, this might be a linux issue only
        adminCredentialsPopup.setResizable(false);
        adminCredentialsPopup.show();
    }

    /**
     * Closes the admin credentials popup.
     * Should never be called if the popup window is not shown.
     */
    public void closeAdminCredentialsPopup() {
        adminCredentialsPopup.close();
        adminCredentialsPopup = null;
    }

    /**
     * Shows the admin view.
     * This method should only be called after entering the correct admin password.
     */
    public void showAdminView() {
        // TODO:
        System.out.println("Show admin view");
    }
    //add step 4 here.
}