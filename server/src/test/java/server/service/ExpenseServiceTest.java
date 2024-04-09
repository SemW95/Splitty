package server.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import commons.Expense;
import commons.Person;
import commons.Tag;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.database.ExpenseRepository;

class ExpenseServiceTest {

    private ExpenseService expenseService;
    @Mock
    private ExpenseRepository expenseRepository;
    private Expense testExpense;
    private Person receiver;
    private Person receiver2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        expenseService = new ExpenseService(expenseRepository);

        // Initialize a test Expense for reuse
        receiver = new Person(
            "Receiver",
            "Last",
            "receiver@example.com",
            "NL78ABNA2490067532",
            "DNBANOKK");
        receiver.setId("receiverId");
        receiver2 = new Person(
            "Receiver2",
            "Last2",
            "receiver2@example.com",
            "NL78ABNA2490067532",
            "DNBANOKK");
        receiver2.setId("receiverId2");
        testExpense = new Expense(receiver, BigDecimal.valueOf(100), Instant.now(), new Tag());
        testExpense.setId("testId");
    }

    @Test
    void getAllExpense() {
        when(expenseRepository.findAll()).thenReturn(Collections.singletonList(testExpense));
        List<Expense> expenses = expenseService.getAllExpense();
        assertFalse(expenses.isEmpty());
        assertEquals(1, expenses.size());
        verify(expenseRepository).findAll();
    }

    @Test
    void getExpenseById() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.of(testExpense));
        Expense expense = expenseService.getExpenseById("testId");
        assertNotNull(expense);
        verify(expenseRepository).findById("testId");
    }

    @Test
    void deleteExpense() {
        when(expenseRepository.findById("testId")).thenReturn(Optional.of(testExpense));
        assertDoesNotThrow(() -> expenseService.deleteExpense("testId"));
        verify(expenseRepository).deleteById("testId");
    }

    @Test
    void createExpense() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> expenseService.createExpense(testExpense));
        verify(expenseRepository).save(testExpense);
    }

    @Test
    void updateExpense() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.of(testExpense));
        assertDoesNotThrow(() -> expenseService.updateExpense(testExpense));
        verify(expenseRepository).save(testExpense);
    }

    @Test
    void getParticipants() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.of(testExpense));
        List<Person> participants = expenseService.getParticipants("testId");
        assertNotNull(participants);
        verify(expenseRepository).findById("testId");
    }

    @Test
    void getDescription() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.of(testExpense));
        String description = expenseService.getDescription("testId");
        assertNotNull(description);
        verify(expenseRepository).findById("testId");
    }

    @Test
    void getReceiver() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.of(testExpense));
        Person receiver = expenseService.getReceiver("testId");
        assertNotNull(receiver);
        verify(expenseRepository).findById("testId");
    }

    @Test
    void getPaid() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.of(testExpense));
        BigDecimal paid = expenseService.getPaid("testId");
        assertNotNull(paid);
        verify(expenseRepository).findById("testId");
    }

    @Test
    void getTag() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.of(testExpense));
        assertNotNull(expenseService.getTag("testId"));
        verify(expenseRepository).findById("testId");
    }

    @Test
    void getPaymentDateTime() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.of(testExpense));
        Instant paymentDateTime = expenseService.getPaymentDateTime("testId");
        assertNotNull(paymentDateTime);
        verify(expenseRepository).findById("testId");
    }

    @Test
    void setParticipants() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.of(testExpense));
        assertDoesNotThrow(() -> expenseService.setParticipants(
            "testId",
            Collections.singletonList(new Person())));
        verify(expenseRepository).save(testExpense);
    }

    @Test
    void setDescription() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.of(testExpense));
        assertDoesNotThrow(() -> expenseService.setDescription("testId", "New Description"));
        verify(expenseRepository).save(testExpense);
    }

    @Test
    void setReceiver() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.of(testExpense));
        assertDoesNotThrow(() -> expenseService.setReceiver("testId", receiver));
        verify(expenseRepository).save(testExpense);
    }

    @Test
    void setPaid() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.of(testExpense));
        assertDoesNotThrow(() -> expenseService.setPaid("testId", BigDecimal.TEN));
        verify(expenseRepository).save(testExpense);
    }

    @Test
    void setTag() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.of(testExpense));
        assertDoesNotThrow(() -> expenseService.setTag(
            "testId",
            new commons.Tag(
                "tagName",
                50,
                100,
                150)));
        verify(expenseRepository).save(testExpense);
    }

    @Test
    void setPaymentDateTime() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.of(testExpense));
        assertDoesNotThrow(() -> expenseService.setPaymentDateTime("testId", Instant.now()));
        verify(expenseRepository).save(testExpense);
    }

    @Test
    void addParticipant() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.of(testExpense));
        assertDoesNotThrow(() -> expenseService.addParticipant("testId", receiver2));
        verify(expenseRepository).save(testExpense);
    }

    @Test
    void addParticipants() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.of(testExpense));
        assertDoesNotThrow(() -> expenseService.addParticipants(
            "testId",
            Collections.singletonList(receiver2)));
        verify(expenseRepository).save(testExpense);
    }

    @Test
    void removeParticipant() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.of(testExpense));
        assertDoesNotThrow(() -> expenseService.removeParticipant(
            "testId",
            "participantId"));
        verify(expenseRepository).save(testExpense);
    }

    @Test
    void getShare() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.of(testExpense));
        BigDecimal share = expenseService.getShare("testId");
        assertThat(share).isNotNull();
        verify(expenseRepository).findById("testId");
    }

    @Test
    void getExpenseByIdThrowsWhenNotFound() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(
            IllegalStateException.class,
            () -> expenseService.getExpenseById("nonexistentId"),
            "Expected getExpenseById() to throw, but it didn't");
        verify(expenseRepository).findById("nonexistentId");
    }

    @Test
    void deleteExpenseThrowsWhenNotFound() {
        when(expenseRepository.findById("nonexistentId")).thenReturn(Optional.empty());
        assertThrows(
            IllegalStateException.class,
            () -> expenseService.deleteExpense("nonexistentId"),
            "Expected deleteExpense() to throw, but it didn't");
        verify(expenseRepository).findById("nonexistentId");
    }

    @Test
    void createExpenseThrowsWhenExpenseExists() {
        when(expenseRepository.existsById(anyString())).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> expenseService.createExpense(testExpense),
            "Expected createExpense() to throw due to existing expense, but it didn't");
        verify(expenseRepository).existsById(testExpense.getId());
    }

    @Test
    void updateExpenseThrowsWhenExpenseNotFound() {
        when(expenseRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> expenseService.updateExpense(testExpense),
            "Expected updateExpense() to throw due to nonexistent expense, but it didn't");
        verify(expenseRepository).findById(testExpense.getId());
    }
}
