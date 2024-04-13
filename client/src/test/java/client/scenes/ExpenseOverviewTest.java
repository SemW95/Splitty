package client.scenes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.util.StringUtils.hasText;
import static org.testfx.api.FxAssert.verifyThat;

import client.Main;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

/**
 * Main test.
 */
public class ExpenseOverviewTest extends FxRobot {
    private static Application application;

    @BeforeAll
    public static void setup() throws Exception {
        FxToolkit.registerPrimaryStage();
        application = FxToolkit.setupApplication(Main.class);
    }

    @AfterAll
    public static void cleanup() throws Exception {
        FxToolkit.cleanupApplication(application);
        FxToolkit.cleanupStages();
    }

    @Test
    public void eventCreateTest() {
        ResourceBundle bundle = Main.FXML.getBundle();
        clickOn(bundle.getString("home.create-event"));

        assertTrue(lookup(bundle.getString("create-event.create-event")).tryQuery()
            .isPresent());

        clickOn("#eventName").type(KeyCode.A,KeyCode.B);
        clickOn("#eventDescription").type(KeyCode.B,KeyCode.C);
        clickOn("#create");

        assertTrue(lookup("ab").tryQuery().isPresent());

    }

    @Test
    public void renameTest() {
        ResourceBundle bundle = Main.FXML.getBundle();
        clickOn(bundle.getString("home.create-event"));

        assertTrue(lookup(bundle.getString("create-event.create-event")).tryQuery()
            .isPresent());

        clickOn("#eventName").type(KeyCode.A,KeyCode.B);
        clickOn("#eventDescription").type(KeyCode.B,KeyCode.C);
        clickOn("#create");

        assertTrue(lookup("ab").tryQuery().isPresent());

        clickOn("#addExpenseButton");
        clickOn("#expenseNameLabel").type(KeyCode.E,KeyCode.X);
        press(KeyCode.ENTER);
//        assertTrue(lookup("Successfully").tryQuery().isPresent());

    }

    @Test
    public void amountChangeTest() {
        ResourceBundle bundle = Main.FXML.getBundle();
        clickOn(bundle.getString("home.create-event"));

        assertTrue(lookup(bundle.getString("create-event.create-event")).tryQuery()
            .isPresent());

        clickOn("#eventName").type(KeyCode.A,KeyCode.B);
        clickOn("#eventDescription").type(KeyCode.B,KeyCode.C);
        clickOn("#create");

        assertTrue(lookup("ab").tryQuery().isPresent());

        clickOn("#addExpenseButton");
        clickOn("#expenseAmountLabel").type(KeyCode.E,KeyCode.X);
        press(KeyCode.ENTER);
//        assertTrue(lookup("Successfully").tryQuery().isPresent());

    }

    @Test
    public void invalidSyntaxAmountTest() {
        ResourceBundle bundle = Main.FXML.getBundle();
        clickOn(bundle.getString("home.create-event"));

        assertTrue(lookup(bundle.getString("create-event.create-event")).tryQuery()
            .isPresent());

        clickOn("#eventName").type(KeyCode.A,KeyCode.B);
        clickOn("#eventDescription").type(KeyCode.B,KeyCode.C);
        clickOn("#create");

        assertTrue(lookup("ab").tryQuery().isPresent());

        clickOn("#addExpenseButton");
        clickOn("#expenseAmountLabel");
        write("712.five");
        press(KeyCode.ENTER);
//        assertTrue(lookup("Successfully").tryQuery().isPresent());

    }


    @Test
    public void recipientChangeTest() {
        ResourceBundle bundle = Main.FXML.getBundle();
        clickOn(bundle.getString("home.create-event"));

        assertTrue(lookup(bundle.getString("create-event.create-event")).tryQuery()
            .isPresent());

        clickOn("#eventName").type(KeyCode.A,KeyCode.B);
        clickOn("#eventDescription").type(KeyCode.B,KeyCode.C);
        clickOn("#create");

        assertTrue(lookup("ab").tryQuery().isPresent());

        clickOn("#addExpenseButton");
        clickOn("#recipientMenu");
        press(KeyCode.ENTER);
//        assertTrue(lookup("Successfully").tryQuery().isPresent());

    }

    @Test
    public void dateChangeTest() {
        ResourceBundle bundle = Main.FXML.getBundle();
        clickOn(bundle.getString("home.create-event"));

        assertTrue(lookup(bundle.getString("create-event.create-event")).tryQuery()
            .isPresent());

        clickOn("#eventName").type(KeyCode.A,KeyCode.B);
        clickOn("#eventDescription").type(KeyCode.B,KeyCode.C);
        clickOn("#create");

        assertTrue(lookup("ab").tryQuery().isPresent());

        clickOn("#addExpenseButton");
        clickOn("#expenseDate");
        press(KeyCode.ENTER);
//        assertTrue(lookup("Successfully").tryQuery().isPresent());

    }

    @Test
    public void tagMenuChange() {
        ResourceBundle bundle = Main.FXML.getBundle();
        clickOn(bundle.getString("home.create-event"));

        assertTrue(lookup(bundle.getString("create-event.create-event")).tryQuery()
            .isPresent());

        clickOn("#eventName").type(KeyCode.A,KeyCode.B);
        clickOn("#eventDescription").type(KeyCode.B,KeyCode.C);
        clickOn("#create");

        assertTrue(lookup("ab").tryQuery().isPresent());

        clickOn("#addExpenseButton");
        clickOn("#tagMenu");
        press(KeyCode.ENTER);
//        assertTrue(lookup("Successfully").tryQuery().isPresent());

    }




}