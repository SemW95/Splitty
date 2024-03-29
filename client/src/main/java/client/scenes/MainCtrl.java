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
import client.utils.CsPair;
import commons.Event;
import commons.Expense;
import java.io.File;
import java.util.Locale;
import java.util.Stack;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This is the main controller, which holds references to all controllers and scenes.
 */
public class MainCtrl {
    /*
    How to insert a new page: (you can use home as reference)
    Make sure you created a controller and your Page.fxml links:"client.scenes.PageCtrl"
    TODO
    */
    private Stage primaryStage;
    private Stage primaryPopup;
    /**
     * Usually used for confirmation popups.
     */
    private Stage secondaryPopup;
    private MyFXML fxml;
    private String savedAdminPassword;
    private Stack<CsPair<Initializable>> stack;
    private CsPair<HomeCtrl> homePair;
    private CsPair<AdminCredentialsCtrl> adminCredentialsPair;
    private CsPair<ExpenseOverviewCtrl> expenseOverviewPair;
    private CsPair<EventOverviewCtrl> eventOverviewPair;
    private CsPair<ManageExpenseCtrl> manageExpensePair;
    private CsPair<AddParticipantCtrl> addParticipantPair;
    private CsPair<ManageParticipantsCtrl> manageParticipantsPair;
    private CsPair<EditParticipantCtrl> editParticipantPair;
    private CsPair<DeleteParticipantConfirmationCtrl> deleteParticipantConfirmationPair;
    private CsPair<AdminOverviewCtrl> adminOverviewPair;
    private CsPair<DeleteEventConfirmationCtrl> deleteEventConfirmationPair;
    // private Pair<ExpenseCardCtrl, Parent> expenseCard;

    /**
     * Main controller initialization.
     *
     * @param primaryStage the primary stage
     * @param fxml         MyFXML class
     */
    public void initialize(Stage primaryStage, MyFXML fxml) {
        this.primaryStage = primaryStage;
        this.fxml = fxml;
        loadAllPairs();

        showHome();
        primaryStage.show();
    }

    private void loadAllPairs() {
        homePair = fxml.load(HomeCtrl.class, "client", "scenes", "Home.fxml");
        adminCredentialsPair =
            fxml.load(AdminCredentialsCtrl.class,
                "client", "scenes", "AdminCredentials.fxml");
        expenseOverviewPair = fxml.load(ExpenseOverviewCtrl.class, "client", "scenes",
            "ExpenseOverview.fxml");
        eventOverviewPair = fxml.load(EventOverviewCtrl.class,
            "client", "scenes", "EventOverview.fxml");
        manageExpensePair = fxml.load(ManageExpenseCtrl.class, "client", "scenes",
            "ManageExpense.fxml");
        addParticipantPair = fxml.load(AddParticipantCtrl.class,
            "client", "scenes", "AddParticipant.fxml");
        manageParticipantsPair = fxml.load(ManageParticipantsCtrl.class,
            "client", "scenes", "ManageParticipants.fxml");
        editParticipantPair = fxml.load(EditParticipantCtrl.class,
            "client", "scenes", "EditParticipant.fxml");
        deleteParticipantConfirmationPair = fxml.load(DeleteParticipantConfirmationCtrl.class,
            "client", "scenes", "DeleteParticipantConfirmation.fxml");
        adminOverviewPair = fxml.load(AdminOverviewCtrl.class,
            "client", "scenes", "AdminOverview.fxml");
        deleteEventConfirmationPair = fxml.load(DeleteEventConfirmationCtrl.class,
            "client", "scenes", "DeleteEventConfirmation.fxml");
    }

    /**
     * Updates all screens with data from the database.
     */
    public void updateAll() {
        homePair.ctrl.refetch();
        expenseOverviewPair.ctrl.refetch();
        eventOverviewPair.ctrl.refetch();
        manageExpensePair.ctrl.refetch();
        // addParticipantPair.ctrl
        // manageParticipantsPair.ctrl
        // editParticipantPair.ctrl
        adminOverviewPair.ctrl.refetch();
    }

    /**
     * Sets primary stage to the Home scene.
     */
    public void showHome() {
        primaryStage.setTitle(fxml.getBundle().getString("home.title"));
        homePair.ctrl.refetch();
        primaryStage.setScene(homePair.scene);
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
        loadAllPairs();
        // TODO
        showHome();
    }

    /**
     * Creates an admin credential popup (dialog window) that blocks other windows.
     * Should never be called twice before closing one of the popups.
     */
    public void showAdminCredentialsPopup() {
        // If the user already entered the password, it should directly open the admin overview
        if (adminCredentialsPair.ctrl.savedPasswordIsCorrect()) {
            showAdminOverview();
            return;
        }

        primaryPopup = new Stage();
        // Set it to block other windows (you can only click on this popup)
        primaryPopup.initModality(Modality.APPLICATION_MODAL);
        primaryPopup.initOwner(primaryStage);
        primaryPopup.setTitle("Admin credentials");
        primaryPopup.setScene(adminCredentialsPair.scene);
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
        primaryStage.setScene(adminOverviewPair.scene);
        primaryStage.setTitle("Admin Overview");
        adminOverviewPair.ctrl.refetch();
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
        primaryPopup.setScene(addParticipantPair.scene);
        primaryPopup.show();
    }


    /**
     * Show the ManageParticipants screen.
     */
    public void showManageParticipantsScreen() {
        primaryStage.setTitle("Manage Participants");
        primaryStage.setScene(manageParticipantsPair.scene);
    }

    /**
     * Show the EditParticipant popup.
     */
    public void showEditParticipantPopup() {
        primaryPopup = new Stage();
        primaryPopup.setTitle("Edit Participant");
        primaryPopup.setScene(editParticipantPair.scene);
        primaryPopup.show();
        primaryPopup.setResizable(false);
    }

    /**
     * Show the DeleteParticipantConfirmation popup.
     */
    public void showDeleteParticipantConfirmationPopup() {
        secondaryPopup = new Stage();
        secondaryPopup.setTitle("Delete Participant Confirmation");
        secondaryPopup.setScene(deleteParticipantConfirmationPair.scene);
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
        primaryPopup.setScene(deleteEventConfirmationPair.scene);
        primaryPopup.show();
        primaryPopup.setResizable(false);
        deleteEventConfirmationPair.ctrl.setCallback(deleteCallback);
    }

    //add step 4 here.

    // public Pair<ExpenseCardCtrl, Parent> getExpenseCard() {
    //     return expenseCard;
    // }

    /**
     * Sets primary stage to the Event overview scene.
     */
    public void showEventOverview(Event event, boolean fromAdmin) {
        primaryStage.setTitle("Event Overview");
        eventOverviewPair.ctrl.update(event);
        if (fromAdmin) {
            eventOverviewPair.ctrl.setGoBackToAdmin(true);
        }
        primaryStage.setScene(eventOverviewPair.scene);
    }

    public String getSavedAdminPassword() {
        return savedAdminPassword;
    }

    public void setSavedAdminPassword(String savedAdminPassword) {
        this.savedAdminPassword = savedAdminPassword;
    }

    /**
     * Shows the expense overview.
     *
     * @param expense the expense to show
     * @param event   which event the expense belongs to
     */
    public void showExpenseOverview(Expense expense, Event event) {
        primaryStage.setTitle("Expense Overview");
        expenseOverviewPair.ctrl.update(expense, event);
        primaryStage.setScene(expenseOverviewPair.scene);
    }

    /**
     * Show the manage expense popup.
     *
     * @param expense the expense to manage
     * @param event   which event the expense belongs to
     */
    public void showManageExpensePopup(Expense expense, Event event) {
        primaryPopup = new Stage();
        primaryPopup.initModality(Modality.APPLICATION_MODAL);
        primaryPopup.initOwner(primaryStage);
        primaryPopup.setTitle("Manage expense");
        primaryPopup.setScene(manageExpensePair.scene);
        primaryPopup.setResizable(false);
        manageExpensePair.ctrl.update(expense, event);
        primaryPopup.show();
    }
}