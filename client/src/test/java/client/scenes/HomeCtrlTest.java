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

import static com.google.inject.Guice.createInjector;
import static org.testfx.api.FxAssert.verifyThat;

import client.MyFXML;
import client.utils.CsPair;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/**
 * Home scene test where we can test only a very small part of it.
 */
@ExtendWith(ApplicationExtension.class)
class HomeCtrlTest {
    private CsPair<HomeCtrl> homePair;

    @Start
    void onStart(Stage stage) {
        MyFXML fxml = new MyFXML(createInjector());
        homePair = fxml.load(HomeCtrl.class, "client", "scenes", "Home.fxml");
        stage.setScene(homePair.scene);
        stage.show();
    }

    @Test
    void someTest(FxRobot robot) {
        verifyThat("#recentOverview", (Text t) -> !t.getText().isBlank());
    }
}