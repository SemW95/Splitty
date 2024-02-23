package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    private Event test1;
    private Event test2;
    private ArrayList<Tag> tags1;
    private ArrayList<Tag> tags2;
    private Tag tag1;
    private Tag tag2;
    private Event test3;
    private Event test4;
    private ArrayList<Expense> expenses1;
    private ArrayList<Expense> expenses2;
    private Expense expense1;
    private Expense expense2;
    private Event test5;
    private Event test6;

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


        test5 = new Event("Pizza", "Pizza with the group", tags1,expenses1 );
        test6 = new Event("Pizza", "Pizza with the group", tags1, expenses2);

    }

    // TO DO: test the ID setter
    void setId() {
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
        assertEquals("Dinner and drinks with the group", test1.getDescription(), "Incorrect description");
    }

    @Test
    void setDescription() {
        String newDescription = "NewDescription";
        test1.setDescription(newDescription);
        assertEquals(newDescription, test1.getDescription(), "Setting description failed");
    }

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
    void testEquals1() {
        assertEquals(test1,test2);
    }

    @Test
    void testEquals2() {
        assertEquals(test3, test4);
    }

    @Test
    void testEquals3() {
        assertEquals(test5, test6);
    }

    @Test
    void testHashCode() {
        assertEquals(test1.hashCode(),test2.hashCode(), "Hash codes should be equal");
    }
}