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
import commons.Colour;
import commons.Person;
import commons.Tag;
import java.time.Instant;
import java.util.ArrayList;
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
    private Stage addParticipantPopup;
    private Stage manageParticipantsScreen;
    private Stage editParticipantPopup;
    private Stage deleteParticipantConfirmationPopup;
    private HomeCtrl homeCtrl;
    private Scene home;
    private AdminCredentialsCtrl adminCredentialsCtrl;
    private Scene adminCredentials;
    private ExpenseOverviewCtrl expenseOverviewCtrl;
    private Scene expenseOverview;
    private AddParticipantCtrl addParticipantCtrl;
    private Scene addParticipant;
    private ManageParticipantsCtrl manageParticipantsCtrl;
    private Scene manageParticipants;
    private EditParticipantCtrl editParticipantCtrl;
    private Scene editParticipant;
    private DeleteParticipantConfirmationCtrl deleteParticipantConfirmationCtrl;
    private Scene deleteParticipantConfirmation;

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
                           Pair<ExpenseOverviewCtrl, Parent> expenseOverviewPair,
                           Pair<AddParticipantCtrl, Parent> addParticipantPair,
                           Pair<ManageParticipantsCtrl, Parent> manageParticipantsPair,
                           Pair<EditParticipantCtrl, Parent> editParticipantPair,
                           Pair<DeleteParticipantConfirmationCtrl, Parent>
                               deleteParticipantConfirmationCtrlParentPair) {
        this.primaryStage = primaryStage;
        this.fxml = fxml;

        //add step 2 and 3 below.
        this.homeCtrl = homePair.getKey();
        this.home = new Scene(homePair.getValue());

        this.adminCredentialsCtrl = adminCredentialsPair.getKey();
        this.adminCredentials = new Scene(adminCredentialsPair.getValue());

        this.expenseOverviewCtrl = expenseOverviewPair.getKey();
        this.expenseOverview = new Scene(expenseOverviewPair.getValue());

        this.addParticipantCtrl = addParticipantPair.getKey();
        this.addParticipant = new Scene(addParticipantPair.getValue());

        this.manageParticipantsCtrl = manageParticipantsPair.getKey();
        this.manageParticipants = new Scene(manageParticipantsPair.getValue());

        this.editParticipantCtrl = editParticipantPair.getKey();
        this.editParticipant = new Scene(editParticipantPair.getValue());

        this.deleteParticipantConfirmationCtrl =
            deleteParticipantConfirmationCtrlParentPair.getKey();
        this.deleteParticipantConfirmation = new Scene(
            deleteParticipantConfirmationCtrlParentPair.getValue());

        showHome();
        primaryStage.show();

        //        TODO Make the expense control fetch data from database and delete this!

        primaryStage.setScene(expenseOverview);
        expenseOverviewCtrl.populate();


        // For testing the following four scenes:
        // showAddParticipantPopup();
        // showManageParticipantsScreen();
        // showEditParticipantPopup();
        // showDeleteParticipantConfirmationPopup();
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

    /**
     * Show the AddParticipant popup.
     */
    public void showAddParticipantPopup() {
        addParticipantPopup = new Stage();
        addParticipantPopup.setTitle("Add Participant");
        addParticipantPopup.setScene(addParticipant);
        addParticipantPopup.show();
    }

    /**
     * Close the AddParticipant popup.
     */
    public void closeAddParticipantPopup() {
        addParticipantPopup.close();
        addParticipantPopup = null;
    }

    /**
     * Show the ManageParticipants screen.
     */
    public void showManageParticipantsScreen() {
        manageParticipantsScreen = new Stage();
        manageParticipantsScreen.setTitle("Manage Participants");
        manageParticipantsScreen.setScene(manageParticipants);
        manageParticipantsScreen.show();
        manageParticipantsScreen.setResizable(false);
    }

    /**
     * Close the ManageParticipants screen.
     */
    public void closeManageParticipantsScreen() {
        manageParticipantsScreen.close();
        manageParticipantsScreen = null;
    }

    /**
     * Show the EditParticipant popup.
     */
    public void showEditParticipantPopup() {
        editParticipantPopup = new Stage();
        editParticipantPopup.setTitle("Edit Participant");
        editParticipantPopup.setScene(editParticipant);
        editParticipantPopup.show();
        editParticipantPopup.setResizable(false);
    }

    /**
     * Close the EditParticipants popup.
     */
    public void closeEditParticipantsPopup() {
        editParticipantPopup.close();
        editParticipantPopup = null;
    }

    /**
     * Show the DeleteParticipantConfirmation popup.
     */
    public void showDeleteParticipantConfirmationPopup() {
        deleteParticipantConfirmationPopup = new Stage();
        deleteParticipantConfirmationPopup.setTitle("Delete Participant Confirmation");
        deleteParticipantConfirmationPopup.setScene(deleteParticipantConfirmation);
        deleteParticipantConfirmationPopup.show();
        deleteParticipantConfirmationPopup.setResizable(false);
    }

    /**
     * Close the DeleteParticipantConfirmation popup.
     */
    public void closeDeleteParticipantConfirmationPopup() {
        deleteParticipantConfirmationPopup.close();
        deleteParticipantConfirmationPopup = null;
    }
    //add step 4 here.
}