package commons;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExpenseTest {

    private Expense expense1;
    private Expense expense2;
    private Person person1;
    private Person person2;
    private Tag tag1;
    private Tag tag2;
    private Instant now = Instant.now();
    @BeforeEach
    void setUp() {
        person1 = new Person("Alice", "needs a surname", "Alice@domain.com",
            "AL35202111090000000001234567",
            "ZUOBJEO6XXX");
        person2 = new Person("John", "needs a surname", "Alice@domain.com",
            "AD1400080001001234567890",
            "ZUOBJEO6XXX");

        tag1 = new Tag("Food", new Colour("#0000FF"));
        tag2 = new Tag("Drinks", new Colour("#FFC0CB"));

        expense1 = new Expense("Food", new ArrayList<Person>(), person1, new BigDecimal(14.00), tag1, now);
        expense2 = new Expense("Drinks", new ArrayList<Person>(), person2, new BigDecimal(20.00), tag2, now);
    }

    @Test
    void getDescription() {
        assertEquals("Food", expense1.getDescription(), "Incorrect description");
    }

    @Test
    void setDescription() {
        String newDescription = "Movie";
        expense1.setDescription(newDescription);
        assertEquals("Movie", expense1.getDescription(), "Setting description failed");
    }

    @Test
    void addParticipant() {
    }

    @Test
    void removeParticipant() {
    }

    @Test
    void getShare() {
    }

    @Test
    void getParticipants() {
    }

    @Test
    void setParticipants() {
    }

    @Test
    void getReceiver() {
        assertEquals(person1, expense1.getReceiver());
    }

    @Test
    void setReceiver() {
        Person person3 = new Person("John", "needs a surname", "Alice@domain.com",
            "AD1400080001001234567890",
            "ZUOBJEO6XXX");
        expense1.setReceiver(person3);
        assertEquals(person3, expense1.getReceiver());
    }

    @Test
    void getPaid() {
        assertEquals(new BigDecimal(14.00), expense1.getPaid());
    }

    @Test
    void setPaid() {
        BigDecimal newPaid = new BigDecimal(25.00);
        expense1.setPaid(newPaid);
        assertEquals(new BigDecimal(25.00), expense1.getPaid());
    }

    @Test
    void getTag() {
        assertEquals(tag1, expense1.getTag());
    }

    @Test
    void setTag() {
        Tag newTag = new Tag("Travel", new Colour("#008000"));
        expense1.setTag(newTag);
        assertEquals(newTag, expense1.getTag());
    }

    @Test
    void getPaymentDateTime() {
        assertEquals(now, expense1.getPaymentDateTime());
    }

    @Test
    void setPaymentDateTime() {
        Instant newDateTime = Instant.now().minusSeconds(3600);
        expense1.setPaymentDateTime(newDateTime);
        assertEquals(newDateTime, expense1.getPaymentDateTime(), "Setting payment date and time failed");
    }
}
