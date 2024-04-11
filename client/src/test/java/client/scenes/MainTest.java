package client.scenes;

import static org.junit.jupiter.api.Assertions.assertTrue;

import client.Main;
import javafx.application.Application;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

/**
 * Main test.
 */
public class MainTest extends FxRobot {
    private static Application application;

    @BeforeAll
    public static void setup() throws Exception {
        FxToolkit.registerPrimaryStage();
        application = FxToolkit.setupApplication(Main.class);
    }

    @AfterAll
    public static void cleanup() throws Exception {
        FxToolkit.cleanupApplication(application);
    }

    @Test
    public void testButtonClick() {
        assertTrue(lookup("#recentOverview").tryQuery().isPresent());
    }
}