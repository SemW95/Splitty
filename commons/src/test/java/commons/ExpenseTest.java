package commons;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExpenseTest {

    private Expense expense1;
    private Expense expense2;
    private Person person1;
    private Person person2;
    private Instant now = Instant.now();
    @BeforeEach
    void setUp() {
        person1 = new Person("Alice", "needs a surname", "Alice@domain.com",
            "AL35202111090000000001234567",
            "ZUOBJEO6XXX");
        person2 = new Person("John", "needs a surname", "Alice@domain.com",
            "AD1400080001001234567890",
            "ZUOBJEO6XXX");
        expense1 = new Expense(person1, new BigDecimal(14.00), now);
        expense2 = new Expense(person2, new BigDecimal(20.00), now);
    }

    @Test
    void getDescription() {
    }

    @Test
    void setDescription() {
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
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void getParticipants() {
    }

    @Test
    void setParticipants() {
    }

    @Test
    void getReceiver() {
    }

    @Test
    void setReceiver() {
    }

    @Test
    void getPaid() {
    }

    @Test
    void setPaid() {
    }

    @Test
    void getTag() {
    }

    @Test
    void setTag() {
    }

    @Test
    void getCreationDate() {
    }

    @Test
    void setCreationDate() {

}