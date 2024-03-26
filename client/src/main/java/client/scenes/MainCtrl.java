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
import client.components.ExpenseCardCtrl;
import commons.Event;
import java.io.File;
import java.util.Locale;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
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
    private Stage primaryPopup;
    /**
     * Usually used for confirmation popups.
     */
    private Stage secondaryPopup;
    private HomeCtrl homeCtrl;
    private Scene home;
    private AdminCredentialsCtrl adminCredentialsCtrl;
    private Scene adminCredentials;
    private ExpenseOverviewCtrl expenseOverviewCtrl;
    private Scene manageExpense;
    private ManageExpenseCtrl manageExpenseCtrl;
    private Scene expenseOverview;
    private EventOverviewCtrl eventOverviewCtrl;
    private Scene eventOverview;
    //private ParticipantCtrl participantCtrl;
    //private Scene participant;
    private AddParticipantCtrl addParticipantCtrl;
    private Scene addParticipant;
    private ManageParticipantsCtrl manageParticipantsCtrl;
    private Scene manageParticipants;
    private EditParticipantCtrl editParticipantCtrl;
    private Scene editParticipant;
    private DeleteParticipantConfirmationCtrl deleteParticipantConfirmationCtrl;
    private Scene deleteParticipantConfirmation;
    private AdminOverviewCtrl adminOverviewCtrl;
    private Scene adminOverview;
    private DeleteEventConfirmationCtrl deleteEventConfirmationCtrl;
    private Scene deleteEventConfirmation;
    private MyFXML fxml;
    private String savedAdminPassword;
    //step 1 below.
    private Pair<ExpenseCardCtrl, javafx.scene.Parent> expenseCard;

    /**
     * Main controller initialization.
     *
     * @param primaryStage                                  the primary stage
     * @param fxml                                          MyFXML class
     * @param homePair                                      a pair of the home controller and node
     * @param adminCredentialsPair                          a pair of the admin credentials
     *                                                      controller and node
     * @param expenseOverviewPair                           a pair of the expense overview
     *                                                      controller and node
     * @param eventOverviewPair                             a pair of the event overview
     *                                                      controller and node
     * @param manageExpensePair                             a pair of the manage expense
     *                                                      controller and node
     * @param addParticipantPair                            a pair of the add participant
     *                                                      controller and node
     * @param manageParticipantsPair                        a pair of the manage participant
     *                                                      controller and node
     * @param editParticipantPair                           a pair of the edit participant
     *                                                      controller and node
     * @param deleteParticipantConfirmationCtrlParentPair   a pair of the delete participant
     *                                                      confirmation controller and node
     * @param adminOverviewPair                             a pair of the admin overview controller
     *                                                      and node
     * @param deleteEventConfirmationPair                   a pair of the delete event confirmation
     *                                                      controller and node
     */
    public void initialize(Stage primaryStage, MyFXML fxml, Pair<HomeCtrl, Parent> homePair,
                           Pair<AdminCredentialsCtrl, Parent> adminCredentialsPair,
                           Pair<ExpenseOverviewCtrl, Parent> expenseOverviewPair,
                           Pair<EventOverviewCtrl, Parent> eventOverviewPair,
                           Pair<ManageExpenseCtrl, Parent> manageExpensePair,
                           Pair<AddParticipantCtrl, Parent> addParticipantPair,
                           Pair<ManageParticipantsCtrl, Parent> manageParticipantsPair,
                           Pair<EditParticipantCtrl, Parent> editParticipantPair,
                           Pair<DeleteParticipantConfirmationCtrl, Parent>
                               deleteParticipantConfirmationCtrlParentPair,
                           Pair<AdminOverviewCtrl, Parent> adminOverviewPair,
                           Pair<DeleteEventConfirmationCtrl, Parent> deleteEventConfirmationPair) {

        this.primaryStage = primaryStage;
        this.fxml = fxml;

        //add step 2 and 3 below.
        this.homeCtrl = homePair.getKey();
        this.home = new Scene(homePair.getValue());

        this.adminCredentialsCtrl = adminCredentialsPair.getKey();
        this.adminCredentials = new Scene(adminCredentialsPair.getValue());

        this.expenseOverviewCtrl = expenseOverviewPair.getKey();
        this.expenseOverview = new Scene(expenseOverviewPair.getValue());

        this.eventOverviewCtrl = eventOverviewPair.getKey();
        this.eventOverview = new Scene(eventOverviewPair.getValue());

        this.expenseCard = expenseCard;

        this.manageExpenseCtrl = manageExpensePair.getKey();
        this.manageExpense = new Scene(manageExpensePair.getValue());

        this.addParticipantCtrl = addParticipantPair.getKey();
        this.addParticipant = new Scene(addParticipantPair.getValue());

        this.manageParticipantsCtrl = manageParticipantsPair.getKey();
        this.manageParticipants = new Scene(manageParticipantsPair.getValue());

        this.editParticipantCtrl = editParticipantPair.getKey();
        this.editParticipant = new Scene(editParticipantPair.getValue());

        this.deleteParticipantConfirmationCtrl =
            deleteParticipantConfirmationCtrlParentPair.getKey();
        this.deleteParticipantConfirmation =
            new Scene(deleteParticipantConfirmationCtrlParentPair.getValue());

        this.adminOverviewCtrl = adminOverviewPair.getKey();
        this.adminOverview = new Scene(adminOverviewPair.getValue());

        this.deleteEventConfirmationCtrl = deleteEventConfirmationPair.getKey();
        this.deleteEventConfirmation = new Scene(deleteEventConfirmationPair.getValue());

        showHome();
        // TODO Remove when done this is only for testing (for the EventOverview)
        //        var person1 = new Person("Alice", "needs a surname", "Alice@domain.com",
        //        "AL35202111090000000001234567", "ZUOBJEO6XXX");
        //        var participants = new ArrayList<Person>();
        //        participants.add(person1);
        //        var list = List.of(new Expense("Food", participants, person1,
        //        new BigDecimal(14.00), null, Instant.now()),
        //            new Expense("Drinks", participants, person1, new BigDecimal(14.00),
        //            null, Instant.now()));
        //        var event = new Event("Dinner and Drinks", "Dinner and drinks with the group",
        //            new ArrayList<>(), new ArrayList<>(), list, new ArrayList<>(),
        //            LocalDate.now(), LocalDate.now(), Instant.now());
        //        showEventOverview(event);
        // TODO: Remove until here
        primaryStage.show();

        // TODO Make the expense control fetch data from database and delete this!

        // primaryStage.setScene(expenseOverview);
        // expenseOverviewCtrl.populate();
        // primaryStage.setScene(manageExpense);
        // manageExpenseCtrl.populate();


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

        var manageExpensePair =
            fxml.load(ManageExpenseCtrl.class, "client", "scenes", "ManageExpense.fxml");
        this.manageExpenseCtrl = manageExpensePair.getKey();
        this.manageExpense = new Scene(manageExpensePair.getValue());

        showHome();
    }

    /**
     * Creates an admin credential popup (dialog window) that blocks other windows.
     * Should never be called twice before closing one of the popups.
     */
    public void showAdminCredentialsPopup() {
        // If the user already entered the password, it should directly open the admin overview
        if (adminCredentialsCtrl.savedPasswordIsCorrect()) {
            showAdminOverview();
            return;
        }

        primaryPopup = new Stage();
        // Set it to block other windows (you can only click on this popup)
        primaryPopup.initModality(Modality.APPLICATION_MODAL);
        primaryPopup.initOwner(primaryStage);
        primaryPopup.setTitle("Admin credentials");
        primaryPopup.setScene(adminCredentials);
        // Making it not resizable also sets it to the size specified in the .fxml file
        // This was the only way I found that fixed that problem
        // (Except the .setMaximized(true), which makes the window flash when it appears)
        // Also, this might be a linux issue only
        primaryPopup.setResizable(false);
        primaryPopup.show();
    }

    /**
     * Closes the primary popup.
     * Should never be called if the primary popup window is not shown.
     */
    public void closePrimaryPopup() {
        primaryPopup.close();
        primaryPopup = null;
    }

    /**
     * Closes the secondary popup.
     * Should never be called if the secondary popup window is not shown.
     */
    public void closeSecondaryPopup() {
        secondaryPopup.close();
        secondaryPopup = null;
    }

    /**
     * Shows the admin view.
     * This method should only be called after entering the correct admin password.
     */
    public void showAdminOverview() {
        primaryStage.setScene(adminOverview);
        primaryStage.setTitle("Admin Overview");
        adminOverviewCtrl.populate();
    }

    /**
     * Opens a file chooser dialog which creates a file.
     *
     * @param initialFileName the suggested file name of the file
     * @return the created file. Can be null
     */
    public File createSaveFile(String initialFileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(initialFileName);
        return fileChooser.showSaveDialog(primaryStage);
    }

    /**
     * Opens a file chooser dialog which opens a file.
     *
     * @return the opened file. Can be null
     */
    public File openSavedFile() {
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showOpenDialog(primaryStage);
    }

    /**
     * Show the AddParticipant popup.
     */
    public void showAddParticipantPopup() {
        primaryPopup = new Stage();
        primaryPopup.setTitle("Add Participant");
        primaryPopup.setScene(addParticipant);
        primaryPopup.show();
    }


    /**
     * Show the ManageParticipants screen.
     */
    public void showManageParticipantsScreen() {
        primaryStage.setTitle("Manage Participants");
        primaryStage.setScene(manageParticipants);
    }

    /**
     * Show the EditParticipant popup.
     */
    public void showEditParticipantPopup() {
        primaryPopup = new Stage();
        primaryPopup.setTitle("Edit Participant");
        primaryPopup.setScene(editParticipant);
        primaryPopup.show();
        primaryPopup.setResizable(false);
    }

    /**
     * Show the DeleteParticipantConfirmation popup.
     */
    public void showDeleteParticipantConfirmationPopup() {
        secondaryPopup = new Stage();
        secondaryPopup.setTitle("Delete Participant Confirmation");
        secondaryPopup.setScene(deleteParticipantConfirmation);
        secondaryPopup.show();
        secondaryPopup.setResizable(false);
    }

    /**
     * Show the DeleteEventConfirmation popup.
     *
     * @param deleteCallback the function to be called if the user confirms their action
     */
    public void showDeleteEventConfirmationPopup(Runnable deleteCallback) {
        primaryPopup = new Stage();
        primaryPopup.initModality(Modality.APPLICATION_MODAL);
        primaryPopup.setTitle("Delete Event Confirmation");
        primaryPopup.setScene(deleteEventConfirmation);
        primaryPopup.show();
        primaryPopup.setResizable(false);
        deleteEventConfirmationCtrl.setCallback(deleteCallback);
    }

    //add step 4 here.

    public Pair<ExpenseCardCtrl, Parent> getExpenseCard() {
        return expenseCard;
    }

    /**
     * Sets primary stage to the Home scene.
     */
    public void showEventOverview(Event event) {
        primaryStage.setTitle(fxml.getBundle().getString("home.title"));
        // TODO: send event with it (is a lot neater)
        eventOverviewCtrl.refresh(event);
        primaryStage.setScene(eventOverview);
    }

    public String getSavedAdminPassword() {
        return savedAdminPassword;
    }

    public void setSavedAdminPassword(String savedAdminPassword) {
        this.savedAdminPassword = savedAdminPassword;
    }
}