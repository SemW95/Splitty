package server.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import commons.Payment;
import commons.Person;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.database.PaymentRepository;

class PaymentServiceTest {

    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    private Payment payment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentService(paymentRepository);

        Person payer = new Person(
            "John",
            "Doe",
            "john.doe@example.com",
            "LT601010012345678901",
            "BOOKTP99A3E");
        Person receiver = new Person(
            "Jane",
            "Doe",
            "jane.doe@example.com",
            "LT601010012345678901",
            "BOOKTP99A3E");
        payment = new Payment(payer, receiver, new BigDecimal("100.00"));
    }

    @Test
    void addPayment() {
        when(paymentRepository.findById(any())).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> paymentService.addPayment(payment));
        verify(paymentRepository).save(payment);
    }

    @Test
    void addPaymentThrowsExceptionWhenPaymentExists() {
        when(paymentRepository.existsById(any())).thenReturn(true);
        payment.setId("random");
        assertThrows(IllegalStateException.class, () -> paymentService.addPayment(payment));
    }

    @Test
    void getAllPayments() {
        when(paymentRepository.findAll()).thenReturn(Arrays.asList(payment));
        List<Payment> payments = paymentService.getAllPayments();
        assertFalse(payments.isEmpty());
        assertEquals(1, payments.size());
        assertEquals(payment, payments.get(0));
    }

    @Test
    void getPaymentById() {
        when(paymentRepository.findById(any())).thenReturn(Optional.of(payment));
        Payment foundPayment = paymentService.getPaymentById("1");
        assertEquals(payment, foundPayment);
    }

    @Test
    void getPaymentByIdThrowsExceptionWhenNotFound() {
        when(paymentRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> paymentService.getPaymentById("1"));
    }

    @Test
    void getPayer() {
        when(paymentRepository.findById(any())).thenReturn(Optional.of(payment));
        Person foundPayer = paymentService.getPayer("1");
        assertEquals(payment.getPayer(), foundPayer);
    }

    @Test
    void getReceiver() {
        when(paymentRepository.findById(any())).thenReturn(Optional.of(payment));
        Person foundReceiver = paymentService.getReceiver("1");
        assertEquals(payment.getReceiver(), foundReceiver);
    }

    @Test
    void getPayerId() {
        when(paymentRepository.findById(any())).thenReturn(Optional.of(payment));
        String payerId = paymentService.getPayerId("1");
        assertEquals(payment.getPayer().getId(), payerId);
    }

    @Test
    void getReceiverId() {
        when(paymentRepository.findById(any())).thenReturn(Optional.of(payment));
        String receiverId = paymentService.getReceiverId("1");
        assertEquals(payment.getReceiver().getId(), receiverId);
    }

    @Test
    void getAmount() {
        when(paymentRepository.findById(any())).thenReturn(Optional.of(payment));
        BigDecimal amount = paymentService.getAmount("1");
        assertEquals(payment.getAmount(), amount);
    }

    @Test
    void setPayer() {
        when(paymentRepository.findById(any())).thenReturn(Optional.of(payment));
        Person newPayer = new Person(
            "Alice",
            "Wonderland",
            "alice@example.com",
            "NL03INGB4597485589",
            "UCJAES2MXXX");
        assertDoesNotThrow(() -> paymentService.setPayer("1", newPayer));
        verify(paymentRepository).save(payment);
        assertEquals(newPayer, payment.getPayer());
    }

    @Test
    void setReceiver() {
        when(paymentRepository.findById(any())).thenReturn(Optional.of(payment));
        Person newReceiver = new Person(
            "Bob",
            "Builder",
            "bob@example.com",
            "NL54RABO4370891833",
            "CMCIFRPP");
        assertDoesNotThrow(() -> paymentService.setReceiver("1", newReceiver));
        verify(paymentRepository).save(payment);
        assertEquals(newReceiver, payment.getReceiver());
    }

    @Test
    void setAmount() {
        when(paymentRepository.findById(any())).thenReturn(Optional.of(payment));
        BigDecimal newAmount = new BigDecimal("200.00");
        assertDoesNotThrow(() -> paymentService.setAmount("1", newAmount));
        verify(paymentRepository).save(payment);
        assertEquals(newAmount, payment.getAmount());
    }

    @Test
    void deletePayment() {
        when(paymentRepository.findById(any())).thenReturn(Optional.of(payment));
        assertDoesNotThrow(() -> paymentService.deletePayment("1"));
        verify(paymentRepository).deleteById("1");
    }

    @Test
    void deletePaymentThrowsExceptionWhenNotFound() {
        when(paymentRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> paymentService.deletePayment("1"));
    }
}
