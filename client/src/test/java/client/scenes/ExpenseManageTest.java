/*
TODO: understand why the tests randomly break in headless mode and fix it

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
    }

    @Order(1)
    @Test
    public void eventCreateTest() {
        ResourceBundle bundle = Main.FXML.getBundle();
        clickOn(bundle.getString("home.create-event")).sleep(2000);

        assertTrue(lookup(bundle.getString("create-event.create-event")).tryQuery().isPresent());

        clickOn("#eventName").write("a");
        clickOn("#eventDescription").write("b");
        clickOn("#create").sleep(2000);

        assertTrue(lookup("a").tryQuery().isPresent());

        type(KeyCode.ESCAPE).sleep(2000);
        type(KeyCode.ENTER).sleep(2000);
    }

     @Order(2)
     @Test
     public void renameTest() {
         ResourceBundle bundle = Main.FXML.getBundle();

         clickOn(bundle.getString("home.create-event")).sleep(2000);

         assertTrue(lookup(bundle.getString("create-event.create-event")).tryQuery().isPresent());

         doubleClickOn("#eventName").write("b");
         doubleClickOn("#eventDescription").write("c");
         clickOn("#create").sleep(2000);

         assertTrue(lookup("b").tryQuery().isPresent());

         clickOn("#addExpenseButton").sleep(2000);
         doubleClickOn("#expenseNameLabel").write("test").type(KeyCode.ENTER).sleep(2000);
         assertTrue(lookup(bundle.getString("manage-expense.changed")).tryQuery().isPresent());

         type(KeyCode.ESCAPE).sleep(2000);
         type(KeyCode.ENTER).sleep(2000);

         type(KeyCode.ESCAPE).sleep(2000);
         type(KeyCode.ENTER).sleep(2000);

         type(KeyCode.ESCAPE).sleep(2000);
         type(KeyCode.ENTER).sleep(2000);

         clickOn(bundle.getString("home.create-event")).sleep(5000);

         assertTrue(lookup("#recentOverview").tryQuery().isPresent());
     }

     @Order(3)
     @Test
     public void amountChangeTest() {
         ResourceBundle bundle = Main.FXML.getBundle();
         clickOn(bundle.getString("home.create-event")).sleep(1000);

         assertTrue(lookup(bundle.getString("create-event.create-event")).tryQuery().isPresent());

         doubleClickOn("#eventName").write("c");
         doubleClickOn("#eventDescription").write("d");
         clickOn("#create").sleep(2000);

         assertTrue(lookup("c").tryQuery().isPresent());

         clickOn("#addExpenseButton").sleep(2000);
         doubleClickOn("#expenseAmountLabel").write("7321").type(KeyCode.ENTER).sleep(2000);

         assertTrue(lookup(bundle.getString("manage-expense.changed")).tryQuery().isPresent());

         type(KeyCode.ESCAPE).sleep(2000);
         type(KeyCode.ENTER).sleep(2000);

         type(KeyCode.ESCAPE).sleep(2000);
         type(KeyCode.ENTER).sleep(2000);

         type(KeyCode.ESCAPE).sleep(2000);
         type(KeyCode.ENTER).sleep(2000);
     }

     @Order(4)
     @Test
     public void invalidSyntaxAmountTest() {
         ResourceBundle bundle = Main.FXML.getBundle();
         clickOn(bundle.getString("home.create-event")).sleep(2000);

         assertTrue(lookup(bundle.getString("create-event.create-event")).tryQuery().isPresent());

         doubleClickOn("#eventName").write("d");
         doubleClickOn("#eventDescription").write("e");
         clickOn("#create").sleep(2000);

         assertTrue(lookup("d").tryQuery().isPresent());

         clickOn("#addExpenseButton").sleep(2000);
         doubleClickOn("#expenseAmountLabel").write("712.five").type(KeyCode.ENTER).sleep(2000);

         assertTrue(lookup(bundle.getString("manage-expense.invalid")).tryQuery().isPresent());

         doubleClickOn("#expenseAmountLabel").write("712").type(KeyCode.ENTER).sleep(2000);

         assertTrue(lookup(bundle.getString("manage-expense.changed")).tryQuery().isPresent());

         type(KeyCode.ESCAPE).sleep(2000);
         type(KeyCode.ENTER).sleep(2000);

         type(KeyCode.ESCAPE).sleep(2000);
         type(KeyCode.ENTER).sleep(2000);

         type(KeyCode.ESCAPE).sleep(2000);
         type(KeyCode.ENTER).sleep(2000);
     }
}
*/
