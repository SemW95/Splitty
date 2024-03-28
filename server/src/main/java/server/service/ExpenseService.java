package server.service;

import commons.Expense;
import commons.Person;
import commons.Tag;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.ExpenseRepository;

/**
 * Service for Person. [CONT -> SERV -> REPO]
 */
@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getAllExpense() {
        return expenseRepository.findAll();
    }

    /** Gets an Expense by its ID.
     *
     * @param id The ID of the Expense that is requested
     * @return The requested Expense
     */
    public Expense getExpenseById(Long id) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);

        if (optionalExpense.isEmpty()) {
            throw new IllegalStateException("There is no Expense with this id");
        }

        return optionalExpense.get();
    }

    /** Creates an expense. Fails if an expense exists with the same id or invite code.
     *
     * @param expense the expense to be created.
     */
    public void createExpense(Expense expense) {
        Optional<Expense> optionalExpense = expenseRepository.findById(expense.getId());

        if (optionalExpense.isPresent()) {
            throw new IllegalStateException("There already exists an Expense with this id");
        }

        expenseRepository.save(expense);
    }

    /** Updates an expense.
     *
     * @param expense expense
     */
    public void updateExpense(Expense expense) {
        Optional<Expense> optionalExpense = expenseRepository.findById(expense.getId());

        if (optionalExpense.isEmpty()) {
            throw new IllegalStateException("An Expense with this ID doesn't exist");
        }

        expenseRepository.save(expense);
    }

    /** Returns a description for a specified Expense.
     *
     * @param id The id of the Expense for which the description should be returned
     * @return The requested description
     */
    public List<Person> getParticipants(Long id) {
        return getExpenseById(id).getParticipants();
    }

    /** Returns the description for the specified Expense.
     *
     * @param id The id of the Expense for which the description should be returned
     * @return The requested description
     */
    public String getDescription(Long id) {
        return getExpenseById(id).getDescription();
    }

    /** Returns the receiver for the specified Expense.
     *
     * @param id The id of the Expense for which the receiver should be returned
     * @return The requested receiver
     */
    public Person getReceiver(Long id) {
        return getExpenseById(id).getReceiver();
    }

    /** Returns the paid variable for the specified Expense.
     *
     * @param id The id of the Expense for which the paid variable should be returned
     * @return The requested paid variable
     */
    public BigDecimal getPaid(Long id) {
        return getExpenseById(id).getPaid();
    }

    /** Returns the tag for the specified Expense.
     *
     * @param id The id of the Expense for which the tag should be returned
     * @return The requested tag
     */
    public Tag getTag(Long id) {
        return getExpenseById(id).getTag();
    }

    /** Returns the paymentDateTime for the specified Expense.
     *
     * @param id The id of the Expense for which the paymentDateTime should be returned
     * @return The requested paymentDateTime
     */
    public Instant getPaymentDateTime(Long id) {
        return getExpenseById(id).getPaymentDateTime();
    }

    /** Sets a description for a specified Expense.
     *
     * @param id The id of the Expense for which the description should be set
     */
    public void setParticipants(Long id, List<Person> participants) {
        getExpenseById(id).setParticipants(new ArrayList<>(participants));
    }

    /** Sets the description for the specified Expense.
     *
     * @param id The id of the Expense for which the description should be set
     */
    public void setDescription(Long id, String description) {
        getExpenseById(id).setDescription(description);
    }

    /** Sets the receiver for the specified Expense.
     *
     * @param id The id of the Expense for which the receiver should be set
     */
    public void setReceiver(Long id, Person receiver) {
        getExpenseById(id).setReceiver(receiver);
    }

    /** Sets the paid variable for the specified Expense.
     *
     * @param id The id of the Expense for which the paid variable should be set
     */
    public void setPaid(Long id, BigDecimal paid) {
        getExpenseById(id).setPaid(paid);
    }

    /** Sets the tag for the specified Expense.
     *
     * @param id The id of the Expense for which the tag should be set
     */
    public void setTag(Long id, Tag tag) {
        getExpenseById(id).setTag(tag);
    }

    /** Sets the paymentDateTime for the specified Expense.
     *
     * @param id The id of the Expense for which the paymentDateTime should be set
     */
    public void setPaymentDateTime(Long id, Instant paymentDateTime) {
        getExpenseById(id).setPaymentDateTime(paymentDateTime);
    }

    public void addParticipant(Long id, Person newParticipant) {
        getExpenseById(id).addParticipant(newParticipant);
    }

    public void addParticipants(Long id, List<Person> newParticipants) {
        getExpenseById(id).addParticipants(newParticipants);
    }

    public void removeParticipant(Long expenseId, Long participantId) {
        getExpenseById(expenseId).removeParticipant(participantId);
    }

    public BigDecimal getShare(Long id) {
        return getExpenseById(id).getShare();
    }

    /** Deletes an Expense.
     *
     * @param id The id of the Expense that should be deleted
     */
    public void deleteExpense(Long id) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);

        if (optionalExpense.isEmpty()) {
            throw new IllegalStateException("There is no Expense with this id");
        }

        expenseRepository.deleteById(id);
    }
}
