package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    private ArrayList<Person> participants;
    private Instant now = Instant.now();

    @BeforeEach
    void setUp() {
        person1 = new Person("Alice", "needs a surname", "Alice@domain.com",
            "AL35202111090000000001234567",
            "ZUOBJEO6XXX");
        person1.setId("person1");
        person2 = new Person("John", "needs a surname", "Alice@domain.com",
            "AD1400080001001234567890",
            "ZUOBJEO6XXX");
        person2.setId("person2");

        tag1 = new Tag("Food", new Colour("#0000FF"));
        tag2 = new Tag("Drinks", new Colour("#FFC0CB"));

        participants = new ArrayList<>();
        participants.add(person1);

        expense1 = new Expense("Food", participants, person1, new BigDecimal(14.00), tag1, now);
        expense2 = new Expense("Food", participants, person1, new BigDecimal(14.00), tag1,
            now); // The same as expense1
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
        expense1.addParticipant(person2);
        assertEquals(2, expense1.getParticipants().size());
    }

    @Test
    void removeParticipant() {
        expense1.removeParticipant(person2);
        assertEquals(1, expense1.getParticipants().size());
    }

    @Test
    void getParticipants() {
        assertEquals(participants, expense1.getParticipants(), "Incorrect list of participants");
    }

    @Test
    void setParticipants() {
        ArrayList<Person> participantsTest = new ArrayList<>();
        expense1.setParticipants(participantsTest);
        assertEquals(participantsTest, expense1.getParticipants(), "Setting participants failed");
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
        assertEquals(person3, expense1.getReceiver(), "Setting receiver failed");
    }

    @Test
    void getPaid() {
        assertEquals(new BigDecimal(14.00), expense1.getPaid(), "Incorrect paid amount");
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
        assertEquals(newTag, expense1.getTag(), "Setting tag failed");
    }

    @Test
    void getPaymentDateTime() {
        assertEquals(now, expense1.getPaymentDateTime(), "Incorrect payment date and time");
    }

    @Test
    void setPaymentDateTime() {
        Instant newDateTime = Instant.now().minusSeconds(3600);
        expense1.setPaymentDateTime(newDateTime);
        assertEquals(newDateTime, expense1.getPaymentDateTime(),
            "Setting payment date and time failed");
    }

    @Test
    void testEquals() {
        assertEquals(expense1, expense2);
    }

    @Test
    void testHashCode() {
        assertEquals(expense1.hashCode(), expense2.hashCode(), "Hash codes should be equal");
    }
}
