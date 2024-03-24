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

package client;

import static com.google.inject.Guice.createInjector;

import client.scenes.AddParticipantCtrl;
import client.scenes.AdminCredentialsCtrl;
import client.scenes.AdminOverviewCtrl;
import client.scenes.DeleteEventConfirmationCtrl;
import client.scenes.DeleteParticipantConfirmationCtrl;
import client.scenes.EditParticipantCtrl;
import client.scenes.ExpenseOverviewCtrl;
import client.scenes.HomeCtrl;
import client.scenes.MainCtrl;
import client.scenes.ManageParticipantsCtrl;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the entry point for the client.
 */
public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        var home = FXML.load(HomeCtrl.class, "client", "scenes", "Home.fxml");
        var adminCredentials =
            FXML.load(AdminCredentialsCtrl.class, "client", "scenes", "AdminCredentials.fxml");
        var expenseOverview = FXML.load(ExpenseOverviewCtrl.class, "client", "scenes",
            "ExpenseOverview.fxml");
        var addParticipant = FXML.load(AddParticipantCtrl.class,
            "client", "scenes", "AddParticipant.fxml");
        var manageParticipants = FXML.load(ManageParticipantsCtrl.class,
            "client", "scenes", "ManageParticipants.fxml");
        var editParticipant = FXML.load(EditParticipantCtrl.class,
            "client", "scenes", "EditParticipant.fxml");
        var deleteParticipantConfirmation = FXML.load(DeleteParticipantConfirmationCtrl.class,
            "client", "scenes", "DeleteParticipantConfirmation.fxml");
        var adminOverview = FXML.load(AdminOverviewCtrl.class,
            "client", "scenes", "AdminOverview.fxml");
        var deleteEventConfirmation = FXML.load(DeleteEventConfirmationCtrl.class,
            "client", "scenes", "DeleteEventConfirmation.fxml");
        //step 6 add new page here


        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.initialize(primaryStage, FXML, home, adminCredentials, expenseOverview,
            addParticipant, manageParticipants, editParticipant,
            deleteParticipantConfirmation, adminOverview, deleteEventConfirmation);
    }
}