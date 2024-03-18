package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentTest {

    private Payment payment1;
    private Payment payment2;
    private Person payer1;
    private Person payer2;
    private Person receiver1;
    private Person receiver2;
    private BigDecimal amount1;
    private BigDecimal amount2;

    @BeforeEach
    void setUp() {
        payer1 = new Person("Alice", "needs a surname", "Alice@domain.com",
            "AL35202111090000000001234567",
            "ZUOBJEO6XXX");
        payer2 = new Person("John", "needs a surname", "John@domain.com",
            "AD1400080001001234567890",
            "ZUOBJEO6XXX"); //different valid iban
        receiver1 = new Person("Alice", "needs a surname", "Alice@domain.com",
            "AL35202111090000000001234567",
            "ZUOBJEO6XXX");
        receiver2 = new Person("John", "needs a surname", "John@domain.com",
            "AD1400080001001234567890",
            "ZUOBJEO6XXX"); //different valid iban
        amount1 = new BigDecimal("14.00");
        amount2 = new BigDecimal("20.00");
        payment1 = new Payment(payer1, receiver2, amount1);
        payment2 = new Payment(payer2, receiver1, amount2);

    }

    @Test
    void testEquals() {
        assertEquals(payment1, payment1);
        assertNotEquals(payment1, payment2);
    }

    @Test
    void testHashCode() {
        assertEquals(payment1.hashCode(), payment1.hashCode(), "Hash codes should be equal");
    }

    @Test
    void getPayer() {
        assertEquals(payer1, payment1.getPayer(), "Incorrect payer");
    }

    @Test
    void setPayer() {
        payment1.setPayer(payer2);
        assertEquals(payer2, payment1.getPayer(), "Setting payer failed");
    }

    @Test
    void getReceiver() {
        assertEquals(receiver2, payment1.getReceiver(), "Incorrect receiver");
    }

    @Test
    void setReceiver() {
        payment1.setReceiver(receiver1);
        assertEquals(receiver1, payment1.getReceiver(), "Setting receiver failed");
    }

    @Test
    void getAmount() {
        assertEquals(amount1, payment1.getAmount(), "Incorrect amount");
    }

    @Test
    void setAmount() {
        payment1.setAmount(amount2);
        assertEquals(amount2, payment1.getAmount(), "Setting amount failed");
    }

}