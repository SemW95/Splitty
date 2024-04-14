package client.scenes;

import static org.junit.jupiter.api.Assertions.assertTrue;

import client.Main;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
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
        FxToolkit.cleanupStages();
    }

    @Order(1)
    @Test
    public void eventCreateTest() {
        ResourceBundle bundle = Main.FXML.getBundle();
        clickOn(bundle.getString("home.create-event"));

        sleep(500);
        assertTrue(lookup(bundle.getString("create-event.create-event")).tryQuery().isPresent());

        clickOn("#eventName").type(KeyCode.A, KeyCode.B);
        clickOn("#eventDescription").type(KeyCode.B, KeyCode.C);
        clickOn("#create");

        assertTrue(lookup("ab").tryQuery().isPresent());

        sleep(500);
        type(KeyCode.ESCAPE);
        sleep(500);
        type(KeyCode.SPACE);
        sleep(500);
        type(KeyCode.ESCAPE);
        sleep(500);


    }

    @Order(2)
    @Test
    public void renameTest() {
        sleep(500);
        ResourceBundle bundle = Main.FXML.getBundle();


        clickOn(bundle.getString("home.create-event"));

        sleep(500);
        assertTrue(lookup(bundle.getString("create-event.create-event")).tryQuery().isPresent());

        sleep(500);
        doubleClickOn("#eventName").type(KeyCode.A, KeyCode.B);
        sleep(500);
        doubleClickOn("#eventDescription").type(KeyCode.B, KeyCode.C);
        clickOn("#create");

        sleep(500);

        assertTrue(lookup("ab").tryQuery().isPresent());

        sleep(500);
        clickOn("#addExpenseButton");
        clickOn("#expenseNameLabel").type(KeyCode.E, KeyCode.X);
        sleep(500);
        press(KeyCode.ENTER);
        sleep(500);
        release(KeyCode.ENTER);
        sleep(500);
        String goal = bundle.getString("manage-expense.changed");
        sleep(500);
        assertTrue(lookup(goal).tryQuery().isPresent());

        sleep(500);
        type(KeyCode.ESCAPE);
        sleep(500);
        type(KeyCode.SPACE);
        sleep(500);

        sleep(500);
        type(KeyCode.ESCAPE);
        sleep(500);
        type(KeyCode.SPACE);
        sleep(500);

        sleep(500);
        type(KeyCode.ESCAPE);
        sleep(500);
        type(KeyCode.SPACE);
        sleep(500);

    }

    @Order(3)
    @Test
    public void amountChangeTest() {
        sleep(500);
        ResourceBundle bundle = Main.FXML.getBundle();
        clickOn(bundle.getString("home.create-event"));

        assertTrue(lookup(bundle.getString("create-event.create-event")).tryQuery().isPresent());

        doubleClickOn("#eventName").type(KeyCode.A, KeyCode.B);
        doubleClickOn("#eventDescription").type(KeyCode.B, KeyCode.C);
        clickOn("#create");

        assertTrue(lookup("ab").tryQuery().isPresent());

        clickOn("#addExpenseButton");
        clickOn("#expenseAmountLabel");
        write("7321");
        sleep(500);
        press(KeyCode.ENTER);
        sleep(500);

        String goal = bundle.getString("manage-expense.changed");
        assertTrue(lookup(goal).tryQuery().isPresent());

        sleep(500);
        type(KeyCode.ESCAPE);
        sleep(500);
        type(KeyCode.SPACE);
        sleep(500);

        sleep(500);
        type(KeyCode.ESCAPE);
        sleep(500);
        type(KeyCode.SPACE);
        sleep(500);

        sleep(500);
        type(KeyCode.ESCAPE);
        sleep(500);
        type(KeyCode.SPACE);
        sleep(500);
    }

    @Order(4)
    @Test
    public void invalidSyntaxAmountTest() {
        ResourceBundle bundle = Main.FXML.getBundle();
        clickOn(bundle.getString("home.create-event"));

        sleep(500);
        assertTrue(lookup(bundle.getString("create-event.create-event")).tryQuery().isPresent());

        doubleClickOn("#eventName").type(KeyCode.A, KeyCode.B);
        doubleClickOn("#eventDescription").type(KeyCode.B, KeyCode.C);
        clickOn("#create");

        sleep(500);
        assertTrue(lookup("ab").tryQuery().isPresent());

        clickOn("#addExpenseButton");
        clickOn("#expenseAmountLabel");
        write("712.five");
        sleep(500);
        press(KeyCode.ENTER);
        sleep(500);

        String goal = bundle.getString("manage-expense.invalid");
        sleep(500);
        assertTrue(lookup(goal).tryQuery().isPresent());

        sleep(500);
        press(KeyCode.ENTER);
        sleep(500);
        doubleClickOn("#expenseAmountLabel");

        write("712");
        sleep(500);
        press(KeyCode.ENTER);
        sleep(500);

        sleep(500);
        type(KeyCode.ESCAPE);
        sleep(500);
        type(KeyCode.SPACE);
        sleep(500);

        sleep(500);
        type(KeyCode.ESCAPE);
        sleep(500);
        type(KeyCode.SPACE);
        sleep(500);

        sleep(500);
        type(KeyCode.ESCAPE);
        sleep(500);
        type(KeyCode.SPACE);
        sleep(500);
    }

}