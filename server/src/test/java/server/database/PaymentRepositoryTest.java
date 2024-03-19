package server.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import commons.Payment;
import commons.Person;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PersonRepository personRepository;
    private Payment payment;
    private Person payer;
    private Person receiver;
    private BigDecimal amount;

    @BeforeEach
    void setUp() {
        payer = personRepository.save(new Person("Alice", "needs a surname", "Alice@domain.com",
            "AL35202111090000000001234567",
            "ZUOBJEO6XXX"));
        receiver = personRepository.save(new Person("John", "needs a surname", "John@domain.com",
            "AD1400080001001234567890",
            "ZUOBJEO6XXX")); //different valid iban
        amount = new BigDecimal("14.00");

        payment = paymentRepository.save(new Payment(payer, receiver, amount));

    }

    @AfterEach
    void tearDown() {
        personRepository.delete(payer);
        personRepository.delete(receiver);
        paymentRepository.delete(payment);
    }

    @Test
    void findByAmount() {
        List<Payment> paymentsFoundByAmount = paymentRepository.findByAmount(amount);
        assertEquals(1, paymentsFoundByAmount.size());
        assertEquals(amount, paymentsFoundByAmount.getFirst().getAmount());
    }

    @Test
    void findByReceiver() {
        List<Payment> paymentsFoundByReceiver = paymentRepository.findByReceiver(receiver);
        assertEquals(1, paymentsFoundByReceiver.size());
        assertEquals(receiver, paymentsFoundByReceiver.getFirst().getReceiver());
    }

    @Test
    void findByPayer() {
        List<Payment> paymentsFoundByPayer = paymentRepository.findByPayer(payer);
        assertEquals(1, paymentsFoundByPayer.size());
        assertEquals(payer, paymentsFoundByPayer.getFirst().getPayer());
    }
}