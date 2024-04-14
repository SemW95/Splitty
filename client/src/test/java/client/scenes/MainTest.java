package client.scenes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import client.Main;
import java.util.Optional;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

/**
 * Main test.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    @Order(1)
    @Test
    public void eventNamesTest() {
        //tries to find all seeded events
        assertTrue(lookup("Just food with friends").tryQuery().isPresent());
        assertTrue(lookup("Celebration Dinner").tryQuery().isPresent());
        assertTrue(lookup("Uber ride").tryQuery().isPresent());
        assertTrue(lookup("Paintball").tryQuery().isEmpty());

        //checks if the recent overview label matches the picked translation
        String recentOverview = Main.FXML.getBundle().getString("home.recent-overview");
        Optional<Node> recentEventOverviewNode = lookup("#recentOverview").tryQuery();
        assertTrue(recentEventOverviewNode.isPresent());
        assertEquals(((Text) recentEventOverviewNode.get()).getText(), recentOverview);
    }

    @Order(2)
    @Test
    public void checkComponents() {
        assertTrue(lookup("#logoLabel").tryQuery().isPresent());
        assertTrue(lookup("#joinEvent").tryQuery().isPresent());
        assertTrue(lookup("#createEvent").tryQuery().isPresent());
        assertTrue(lookup("#adminButton").tryQuery().isPresent());
        assertTrue(lookup("#currencyButton").tryQuery().isPresent());
        assertTrue(lookup("#languageButton").tryQuery().isPresent());
        assertTrue(lookup("#eventCodeTextField").tryQuery().isPresent());
        assertTrue(lookup("#serverStatusText").tryQuery().isPresent());
        assertTrue(lookup("#serverStatus").tryQuery().isPresent());
    }

    @Order(3)
    @Test
    public void findEventTest() {
        // Writes a valid code in eventCode text field
        clickOn("#eventCodeTextField").write("MLzTfy2P");
        clickOn("#joinEvent");

        // Get the text field and check text
        String answerText = Main.FXML.getBundle().getString("home.event-added");
        Optional<Node> eventCodeNode = lookup("#eventCodeTextField").tryQuery();
        assertTrue(eventCodeNode.isPresent());
        TextField textfield = (TextField) eventCodeNode.get();
        assertEquals(textfield.getPromptText(), answerText);

        // Writes an invalid code and check text
        clickOn("#eventCodeTextField").write("Not a Code");
        clickOn("#joinEvent");
        String answerTextWrong = Main.FXML.getBundle().getString("home.event-not-found");
        assertEquals(textfield.getPromptText(), answerTextWrong);

        // Deletes everything in
        clickOn("#eventCodeTextField").push(KeyCode.CONTROL, KeyCode.A).type(KeyCode.DELETE);
        clickOn("#joinEvent");
        String answerTextNothing = Main.FXML.getBundle().getString("home.enter-code");
        assertEquals(answerTextNothing, textfield.getPromptText());
    }

    @Order(4)
    @Test
    void clickLanguageTest() {
        clickOn("#languageButton");

        // Check if the popup exists
        assertTrue(listWindows().size() > 1);
        Optional<Node> languageTextNode = lookup("#languageSelection").tryQuery();
        assertTrue(languageTextNode.isPresent());

        // Choose Dutch and check changes
        clickOn("#clickDutch");
        languageTextNode = lookup("#languageSelection").tryQuery();
        assertTrue(languageTextNode.isPresent());
        Label languageSelection = (Label) languageTextNode.get();
        String languageDutch = "Selecteer een taal";
        assertEquals(languageDutch, languageSelection.getText());

        // Choose Lithuanian and check
        clickOn("#clickLith");
        languageTextNode = lookup("#languageSelection").tryQuery();
        assertTrue(languageTextNode.isPresent());
        languageSelection = (Label) languageTextNode.get();
        assertEquals("Pasirinkite kalbą", languageSelection.getText());

        // Back to english
        clickOn("#clickEng");
        languageTextNode = lookup("#languageSelection").tryQuery();
        assertTrue(languageTextNode.isPresent());
        languageSelection = (Label) languageTextNode.get();
        String languageEn = "Select a language";
        assertEquals(languageEn, languageSelection.getText());
    }
}
