package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventTest {

    private Event test1;
    private Event test2;
    private ArrayList<Tag> tags1;
    private ArrayList<Tag> tags2;
    private Tag tag1;
    private Tag tag2;

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

        Instant now = Instant.now();
        test1 = new Event("Dinner and Drinks", "Dinner and drinks with the group",
            new ArrayList<Person>(), tags1, new ArrayList<Expense>(),
            new ArrayList<Payment>(), now);
        test2 = new Event("Dinner and Drinks", "Dinner and drinks with the group",
            new ArrayList<Person>(), tags2, new ArrayList<Expense>(),
            new ArrayList<Payment>(), now);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventTest eventTest = (EventTest) o;

        if (!Objects.equals(test1, eventTest.test1)) {
            return false;
        }
        if (!Objects.equals(test2, eventTest.test2)) {
            return false;
        }
        if (!Objects.equals(tags1, eventTest.tags1)) {
            return false;
        }
        if (!Objects.equals(tags2, eventTest.tags2)) {
            return false;
        }
        if (!Objects.equals(tag1, eventTest.tag1)) {
            return false;
        }
        return Objects.equals(tag2, eventTest.tag2);
    }

    @Test
    void testHashCode() {
        assertEquals(test1.hashCode(), test2.hashCode(), "Hash codes should be equal");
    }
}