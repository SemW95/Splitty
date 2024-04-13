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
import client.MyFXML;
import client.utils.CsPair;
import client.utils.ScreenUtils;
import client.utils.ServerUtils;
import client.utils.WebSocketClient;
import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import commons.Payment;
import commons.Person;
import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
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
    private Stage popupStage;
    private MyFXML fxml;
    private String savedAdminPassword;
    private CsPair<HomeCtrl> homePair;
    private CsPair<AdminCredentialsCtrl> adminCredentialsPair;
    private CsPair<ExpenseOverviewCtrl> expenseOverviewPair;
    private CsPair<EventOverviewCtrl> eventOverviewPair;
    private CsPair<ManageExpenseCtrl> manageExpensePair;
    private CsPair<ExpenseAddParticipantCtrl> expenseAddParticipantPair;
    private CsPair<AddParticipantCtrl> addParticipantPair;
    private CsPair<ManageParticipantsCtrl> manageParticipantsPair;
    private CsPair<EditParticipantCtrl> editParticipantPair;
    private CsPair<DeleteParticipantConfirmationCtrl> deleteParticipantConfirmationPair;
    private CsPair<AdminOverviewCtrl> adminOverviewPair;
    private CsPair<DeleteEventConfirmationCtrl> deleteEventConfirmationPair;
    private CsPair<CreateEventCtrl> createEventPair;
    private CsPair<LanguageSelectCtrl> languageSelectPair;
    private CsPair<ManageExpenseListCtrl> manageExpenseListPair;
    private CsPair<DeleteExpenseConfirmationCtrl> deleteExpenseConfirmationPair;
    private CsPair<OpenDebtsCtrl> openDebtsPair;
    private CsPair<ManagePaymentsCtrl> managePaymentsPair;
    private CsPair<EditPaymentCtrl> editPaymentPair;
    private CsPair<StatisticsCtrl> statisticsPair;
    private Initializable currentCtrl;
    // private Pair<ExpenseCardCtrl, Parent> expenseCard;
    private WebSocketClient websocketClient;
    private final ServerUtils server;

    @Inject
    public MainCtrl(ServerUtils server) {
        this.server = server;
    }

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

        primaryStage.setResizable(false);
        primaryStage.show();

        popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(primaryStage);
        popupStage.setResizable(false);

        // Create a websocket client which tries to connect to the websocket server
        websocketClient = new WebSocketClient(Main.configManager.getWsServer(),
            () -> Platform.runLater(this::updateAll));

        Thread timerThread = new Thread(() -> {
            // It will sleep at least this long in ms before trying to call the server again
            long minSleepMillis = 2000;

            Instant before = Instant.now().minusMillis(minSleepMillis);
            while (true) {
                // Try to reconnect to the websocket server just in case it was disconnected
                websocketClient.connect();

                long duration = Duration.between(before, Instant.now()).toMillis();
                long timeToSleep = Math.max(minSleepMillis - duration, 0);

                try {
                    Thread.sleep(timeToSleep);
                } catch (InterruptedException e) {
                    break;
                }

                before = Instant.now();

                boolean statusOk = server.serverOnline();

                // Update the actual status text in JavaFX's thread
                Platform.runLater(() -> homePair.ctrl.updateStatus(statusOk));
            }
        });
        timerThread.start();

        // Stop the timer thread when the application is closed
        primaryStage.setOnHiding(event -> {
            // Not the nicest way to stop the thread but the `server.serverOnline()` blocks,
            // and this was the easiest way to solve the problem
            System.exit(0);
        });

        // Add an event listeners for Ctrl+Z
        primaryStage
            .addEventHandler(KeyEvent.ANY, ScreenUtils.undoHandler(server::undo));
        popupStage
            .addEventHandler(KeyEvent.ANY, ScreenUtils.undoHandler(server::undo));
    }


    private void loadAllPairs() {
        homePair = fxml.load(HomeCtrl.class, "client", "scenes", "Home.fxml");

        languageSelectPair =
            fxml.load(LanguageSelectCtrl.class,
                "client", "scenes", "LanguageSelection.fxml");

        adminCredentialsPair =
            fxml.load(AdminCredentialsCtrl.class,
                "client", "scenes", "AdminCredentials.fxml");

        expenseOverviewPair = fxml.load(ExpenseOverviewCtrl.class, "client", "scenes",
            "ExpenseOverview.fxml");

        eventOverviewPair = fxml.load(EventOverviewCtrl.class,
            "client", "scenes", "EventOverview.fxml");

        manageExpensePair = fxml.load(ManageExpenseCtrl.class, "client", "scenes",
            "ManageExpense.fxml");

        expenseAddParticipantPair = fxml.load(ExpenseAddParticipantCtrl.class, "client",
            "scenes", "ExpenseAddParticipant.fxml");

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

        createEventPair = fxml.load(CreateEventCtrl.class, "client", "scenes", "CreateEvent.fxml");

        manageExpenseListPair = fxml.load(ManageExpenseListCtrl.class,
            "client", "scenes", "ManageExpenseList.fxml");

        deleteExpenseConfirmationPair = fxml.load(DeleteExpenseConfirmationCtrl.class,
            "client", "scenes", "DeleteExpenseConfirmation.fxml");

        openDebtsPair = fxml.load(OpenDebtsCtrl.class, "client", "scenes", "OpenDebts.fxml");

        managePaymentsPair =
            fxml.load(ManagePaymentsCtrl.class, "client", "scenes", "ManagePayments.fxml");

        editPaymentPair =
            fxml.load(EditPaymentCtrl.class, "client", "scenes", "EditPayment.fxml");

        statisticsPair =
            fxml.load(StatisticsCtrl.class, "client", "scenes", "Statistics.fxml");
    }

    /**
     * Updates all screens with data from the database.
     */
    private void updateAll() {
        homePair.ctrl.refetch();
        expenseOverviewPair.ctrl.refetch();
        eventOverviewPair.ctrl.refetch();
        manageExpensePair.ctrl.refetch();
        manageParticipantsPair.ctrl.refetch();
        editParticipantPair.ctrl.refetch();
        adminOverviewPair.ctrl.refetch();
        manageExpenseListPair.ctrl.refetch();
        openDebtsPair.ctrl.refetch();
        managePaymentsPair.ctrl.refetch();
    }

    /**
     * Sets primary stage to the Home scene.
     */
    public void showHome() {
        primaryStage.setTitle(fxml.getBundle().getString("home.title"));
        homePair.ctrl.refetch();
        primaryStage.setScene(homePair.scene);
        currentCtrl = homePair.ctrl;
    }

    /**
     * Changes the current language and refreshes everything.
     *
     * @param language a new language (example: "en", "lt")
     */
    public void changeLanguage(String language) {
        Locale locale = Locale.of(language);
        fxml.changeLocale(locale);
        loadAllPairs();
        updateCurrentScreen();
    }

    public String getCurrentLanguage() {
        return fxml.getCurrentLocale().toLanguageTag();
    }

    /**
     * Sets the scene to the screen it was showing before.
     * This is called after changing the language
     */
    private void updateCurrentScreen() {
        switch (currentCtrl) {
            case HomeCtrl homeCtrl -> showHome();

            case ExpenseOverviewCtrl expenseOverviewCtrl ->
                showExpenseOverview(expenseOverviewCtrl.getExpense(),
                    expenseOverviewCtrl.getEvent());

            case EventOverviewCtrl eventOverviewCtrl ->
                showEventOverview(eventOverviewCtrl.getEvent(),
                    eventOverviewCtrl.getGoBackToAdmin());

            case AdminOverviewCtrl adminOverviewCtrl -> showAdminOverview();

            case ManageParticipantsCtrl manageParticipantsCtrl ->
                showManageParticipantsScreen(manageParticipantsCtrl.getEvent());

            case OpenDebtsCtrl openDebtsCtrl -> showOpenDebtsScreen(openDebtsCtrl.getEvent());

            case ManagePaymentsCtrl managePaymentsCtrl ->
                showManagePaymentScreen(managePaymentsCtrl.getEvent());

            default -> {
                System.err.println("Tried to switch language from an unknown screen");
                showHome();
            }
        }

        showLanguageSelectPopup();
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

        // Set it to block other windows (you can only click on this popup)

        popupStage.setTitle(fxml.getBundle().getString("admin-credentials.title"));
        popupStage.setScene(adminCredentialsPair.scene);
        popupStage.show();
    }

    /**
     * Creates a language selection popup (dialog window) that blocks other windows.
     * Should never be called twice before closing one of the popups.
     */
    public void showLanguageSelectPopup() {
        popupStage.setTitle(fxml.getBundle().getString("language-select.title"));
        popupStage.setScene(languageSelectPair.scene);
        popupStage.show();
    }

    /**
     * Closes the primary popup.
     * Should never be called if the primary popup window is not shown.
     */
    public void closePopup() {
        popupStage.close();
    }

    /**
     * Shows the admin view.
     * This method should only be called after entering the correct admin password.
     */
    public void showAdminOverview() {
        primaryStage.setScene(adminOverviewPair.scene);
        primaryStage.setTitle(fxml.getBundle().getString("admin-overview.title"));
        adminOverviewPair.ctrl.refetch();
        currentCtrl = adminOverviewPair.ctrl;
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
     *
     * @param callback function which will be called if the person is added successfully
     */
    public void showAddParticipantPopup(Consumer<Person> callback) {
        popupStage.setTitle(fxml.getBundle().getString("add-participant.title"));
        popupStage.setScene(addParticipantPair.scene);
        addParticipantPair.ctrl.setCallback(callback);
        popupStage.show();
    }


    /**
     * Show the ManageParticipants screen.
     */
    public void showManageParticipantsScreen(Event event) {
        primaryStage.setTitle(fxml.getBundle().getString("manage-participants.title"));
        primaryStage.setScene(manageParticipantsPair.scene);
        manageParticipantsPair.ctrl.update(event);
        currentCtrl = manageParticipantsPair.ctrl;
    }

    /**
     * Show the EditParticipant popup.
     */
    public void showEditParticipantPopup(Person person) {
        popupStage.setTitle(fxml.getBundle().getString("edit-participant.title"));
        popupStage.setScene(editParticipantPair.scene);
        editParticipantPair.ctrl.update(person);
        popupStage.show();
    }

    /**
     * Show the DeleteParticipantConfirmation popup.
     */
    public void showDeleteParticipantConfirmationPopup(Runnable callback) {
        popupStage.setTitle(fxml.getBundle().getString("delete-participant-confirmation.title"));
        popupStage.setScene(deleteParticipantConfirmationPair.scene);
        deleteParticipantConfirmationPair.ctrl.setCallback(callback);
        popupStage.show();
    }

    /**
     * Show the DeleteEventConfirmation popup.
     *
     * @param deleteCallback the function to be called if the user confirms their action
     */
    public void showDeleteEventConfirmationPopup(Runnable deleteCallback) {
        popupStage.setTitle(fxml.getBundle().getString("delete-event-confirmation.title"));
        popupStage.setScene(deleteEventConfirmationPair.scene);
        deleteEventConfirmationPair.ctrl.setCallback(deleteCallback);
        popupStage.show();
    }

    /**
     * Sets primary stage to the Event overview scene.
     *
     * @param fromAdmin whether the user is coming to this screen from the admin overview
     */
    public void showEventOverview(Event event, boolean fromAdmin) {
        primaryStage.setTitle(fxml.getBundle().getString("event-overview.title"));
        eventOverviewPair.ctrl.update(event);
        if (fromAdmin) {
            eventOverviewPair.ctrl.setGoBackToAdmin(true);
        }
        primaryStage.setScene(eventOverviewPair.scene);
        currentCtrl = eventOverviewPair.ctrl;
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
        primaryStage.setTitle(fxml.getBundle().getString("expense-overview.title"));
        expenseOverviewPair.ctrl.update(expense, event);
        primaryStage.setScene(expenseOverviewPair.scene);
        currentCtrl = expenseOverviewPair.ctrl;
    }

    /**
     * Show the manage expense popup.
     *
     * @param expense the expense to manage
     * @param event   which event the expense belongs to
     */
    public void showManageExpensePopup(Expense expense, Event event) {
        popupStage.setTitle(fxml.getBundle().getString("manage-expense.title"));
        popupStage.setScene(manageExpensePair.scene);
        manageExpensePair.ctrl.update(expense, event);
        manageExpensePair.ctrl.defaultStatus();
        popupStage.show();
    }

    /**
     * Show the manage expense popup.
     *
     * @param expense the expense to manage
     * @param event   which event the expense belongs to
     */
    public void showExpenseAddParticipantPopup(Expense expense, Event event) {
        popupStage.setTitle(fxml.getBundle().getString("expense-add-participant.title"));
        popupStage.setScene(expenseAddParticipantPair.scene);
        expenseAddParticipantPair.ctrl.update(expense, event);
        expenseAddParticipantPair.ctrl.defaultStatus();
        popupStage.show();
    }

    /**
     * Show the Event creation popup.
     */
    public void showEventCreationPopup() {
        popupStage.setTitle(fxml.getBundle().getString("create-event.title"));
        popupStage.setScene(createEventPair.scene);
        popupStage.show();
    }

    /**
     * Show the ManageExpenseListCtrl screen.
     *
     * @param event the event
     */
    public void showManageExpenseListScreen(Event event) {
        primaryStage.setTitle(fxml.getBundle().getString("manage-expense-list.title"));
        primaryStage.setScene(manageExpenseListPair.scene);
        manageExpenseListPair.ctrl.update(event);
        currentCtrl = manageExpenseListPair.ctrl;
    }

    /**
     * Show the DeleteExpenseConfirmation popup.
     */
    public void showDeleteExpenseConfirmationPopup(Runnable callback) {
        popupStage.setTitle(fxml.getBundle().getString("delete-expense-confirmation.title"));
        popupStage.setScene(deleteExpenseConfirmationPair.scene);
        deleteExpenseConfirmationPair.ctrl.setCallback(callback);
        popupStage.show();
    }

    /**
     * Show the Open debts screen.
     *
     * @param event the event
     */
    public void showOpenDebtsScreen(Event event) {
        primaryStage.setScene(openDebtsPair.scene);
        primaryStage.setTitle(fxml.getBundle().getString("open-debts.title"));
        openDebtsPair.ctrl.update(event);
        currentCtrl = openDebtsPair.ctrl;
    }

    /**
     * Show the Manage payments screen.
     *
     * @param event the event
     */
    public void showManagePaymentScreen(Event event) {
        primaryStage.setScene(managePaymentsPair.scene);
        primaryStage.setTitle(fxml.getBundle().getString("manage-payments.title"));
        managePaymentsPair.ctrl.update(event);
        currentCtrl = managePaymentsPair.ctrl;
    }

    /**
     * Show the Edit payment popup.
     *
     * @param payment the payment
     * @param event   the event
     */
    public void showEditPaymentPopup(Payment payment, Event event) {
        popupStage.setScene(editPaymentPair.scene);
        popupStage.setTitle(fxml.getBundle().getString("edit-payment.title"));
        editPaymentPair.ctrl.update(payment, event);
        popupStage.show();
    }

    // TODO: not the best way to do this
    private EventHandler<KeyEvent> statisticsCloseHandler;

    /**
     * Show the Statistics popup.
     *
     * @param event the event
     */
    public void showStatisticsPopup(Event event) {
        popupStage.setScene(statisticsPair.scene);
        popupStage.setTitle(fxml.getBundle().getString("statistics.title"));
        statisticsPair.ctrl.update(event);

        // Remove the old event handler if it is already there
        if (statisticsCloseHandler != null) {
            popupStage
                .removeEventHandler(KeyEvent.KEY_PRESSED, statisticsCloseHandler);
        }
        statisticsCloseHandler = ScreenUtils.exitHandler(fxml.getBundle(), this::closePopup);
        popupStage
            .addEventHandler(KeyEvent.KEY_PRESSED, statisticsCloseHandler);

        popupStage.show();
    }
}