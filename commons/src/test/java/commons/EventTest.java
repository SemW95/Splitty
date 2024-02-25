package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    private Event test3;
    private Event test4;
    // private ArrayList<Expense> expenses1;
    // private ArrayList<Expense> expenses2;
    // private Expense expense1;
    // private Expense expense2;
    // private Person receiver;
    // private TotalDebt totalDebt;
    // private Currency c1;
    // private List<Person> l1;
    // private TotalDebt totalDebt;
    // private Event test5;
    // private Event test6;

    @BeforeEach
    void setUp() {
        test1 = new Event("Dinner and Drinks", "Dinner and drinks with the group");
        test2 = new Event("Dinner and Drinks", "Dinner and drinks with the group");

        tags1 = new ArrayList<Tag>();
        tags2 = new ArrayList<Tag>();
        tag1 = new Tag("Blue", "#0000FF");
        tag2 = new Tag("Pink", "#FFC0CB");
        tags1.add(tag1);
        tags1.add(tag2);
        tags2.add(tag1);
        tags2.add(tag2);
        test3 = new Event("Pizza", "Pizza with the group", tags1);
        test4 = new Event("Pizza", "Pizza with the group", tags2);

        // STUFF FOR CONSTRUCTOR 3, ADD THIS WHEN THE REST OF THE CLASSES ARE SECURE
        // expenses1 = new ArrayList<Expense>();
        // expenses2 = new ArrayList<Expense>();

        // c1 = new Currency("EUR");
        // l1 = new ArrayList<>();
        // receiver =
        //    new Person("Emma", "Emma@hotmail.com", "ES9121000418450200051332", "CAIXESBBXXX");
        // totalDebt = new TotalDebt(BigDecimal.valueOf(172), c1, receiver, l1.size())

        // expense1 = new Expense(totalDebt, receiver, )
        // expense2 = new Expense(totalDebt, receiver, )
        // expenses1.add(expense1);
        // expenses1.add(expense2);
        // expenses2.add(expense1);
        // expenses2.add(expense2);

        // test5 = new Event("Pizza", "Pizza with the group", tags1,expenses1);
        // test6 = new Event("Pizza", "Pizza with the group", tags1, expenses2);
    }

    void setId() {
        String newId = "newId";
        test1.setTitle(newId);
        assertEquals(newId, test1.getTitle(), "Setting Id failed");
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

    // TO DO
    @Test
    void getTags() {
    }

    @Test
    void setTags() {
    }

    @Test
    void getExpenses() {
    }

    @Test
    void setExpenses() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
        assertEquals(test1.hashCode(), test2.hashCode(), "Hash codes should be equal");
    }
}