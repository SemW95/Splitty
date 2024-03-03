package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventTest {

    private Event test1;
    private Event test2;
    private ArrayList<Tag> tags1;
    private ArrayList<Tag> tags2;
    private Tag tag1;
    private Tag tag2;
    private Instant now;

    @BeforeEach
    void setUp() {
        tags1 = new ArrayList<Tag>();
        tags2 = new ArrayList<Tag>();
        tag1 = new Tag("Blue", new Colour("#0000FF"));
        tag2 = new Tag("Pink", new Colour("#FFC0CB"));
        tags1.add(tag1);
        tags1.add(tag2);
        tags2.add(tag1);
        tags2.add(tag2);

        now = Instant.now();
        test1 = new Event("Dinner and Drinks", "Dinner and drinks with the group",
            new ArrayList<Person>(), tags1, new ArrayList<Expense>(),
            new ArrayList<Payment>(), now);
        test2 = new Event("Dinner and Drinks", "Dinner and drinks with the group",
            new ArrayList<Person>(), tags2, new ArrayList<Expense>(),
            new ArrayList<Payment>(), now);
        test1.setCode("123");
        test2.setCode("123");
    }

    @Test
    void getTitle() {
        assertEquals("Dinner and Drinks", test1.getTitle(), "Incorrect title");
    }

    @Test
    void setTitle() {
        String newTitle = "NewTitle";
        test1.setTitle(newTitle);
        assertEquals(newTitle, test1.getTitle(), "Setting title failed");
    }

    @Test
    void getDescription() {
        assertEquals("Dinner and drinks with the group", test1.getDescription(),
            "Incorrect description");
    }

    @Test
    void setDescription() {
        String newDescription = "NewDescription";
        test1.setDescription(newDescription);
        assertEquals(newDescription, test1.getDescription(), "Setting description failed");
    }

    @Test
    void getTags() {
        assertEquals(tags1, test1.getTags(), "Incorrect tags");
    }

    @Test
    void setTags() {
        ArrayList<Tag> newTags = new ArrayList<>();
        newTags.add(new Tag("Green", new Colour("#008000")));
        test1.setTags(newTags);
        assertEquals(newTags, test1.getTags(), "Setting tags failed");
    }

    @Test
    void getExpenses() {
        assertEquals(new ArrayList<Expense>(), test1.getExpenses(), "Incorrect expenses");
    }

    @Test
    void setExpenses() {
        // Create some expenses
        Person receiver1 = new Person("Alice", "needs a surname", "Alice@domain.com",
            "GB33BUKB20201555555555", "ZUOBJEO6XXX");
        Person receiver2 =
            new Person("Alice", "needs a surname", "Alice@domain.com",
                "GB33BUKB20201555555556", "ZUOBJEO6XXX"); //changed last digit IBAN
        BigDecimal amount1 = new BigDecimal("50");
        BigDecimal amount2 = new BigDecimal("30");
        Expense expense1 = new Expense(receiver1, amount1);
        Expense expense2 = new Expense(receiver2, amount2);

        // Set expenses for test1
        ArrayList<Expense> newExpenses = new ArrayList<>();
        newExpenses.add(expense1);
        newExpenses.add(expense2);
        test1.setExpenses(newExpenses);

        assertEquals(2, test1.getExpenses().size(), "Setting expenses failed");
        assertTrue(test1.getExpenses().contains(expense1), "Expense 1 not found in the list");
        assertTrue(test1.getExpenses().contains(expense2), "Expense 2 not found in the list");
    }

    @Test
    void getCreationDate() {
        assertEquals(now, test1.getCreationDate(), "Incorrect creation date");
    }

    @Test
    void setCreationDate() {
        Instant newDate = Instant.now().minusSeconds(3600);
        test1.setCreationDate(newDate);
        assertEquals(newDate, test1.getCreationDate(), "Setting creation date failed");
    }

    @Test
    void equals() {
        assertEquals(test1, test2);
    }

    @Test
    void testHashCode() {
        assertEquals(test1.hashCode(), test2.hashCode(), "Hash codes should be equal");
    }
}