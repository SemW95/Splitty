package client.scenes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import client.Main;
import java.util.Optional;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.text.Text;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

/**
 * Main test.
 */
public class ExpenseManageTest extends FxRobot {
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
    public void eventNamesTest() {
        assertTrue(lookup("Just food with friends").tryQuery().isPresent());
        assertTrue(lookup("Celebration Dinner").tryQuery().isPresent());
        assertTrue(lookup("Uber ride").tryQuery().isPresent());
        assertTrue(lookup("Paintball").tryQuery().isEmpty());
        String recentOverview = Main.FXML.getBundle().getString("home.recent-overview");
        Optional<Node> recentEventOverviewNode = lookup("#recentOverview").tryQuery();
        assertTrue(recentEventOverviewNode.isPresent());
        assertEquals(((Text) recentEventOverviewNode.get()).getText(), recentOverview);
    }
}
