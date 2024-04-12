package server.api;

import commons.Expense;
import commons.Person;
import commons.Tag;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.service.ExpenseService;

/**
 * Controller for Expense [CONT -> SERV -> REPO].
 */
@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }


    // TODO move this to admin
    @GetMapping(path = "/expense")
    public List<Expense> getAllExpense() {
        return expenseService.getAllExpense();
    }

    @GetMapping(path = "/expense/{id}")
    public Expense getExpenseById(@PathVariable String id) {
        return expenseService.getExpenseById(id);
    }

    @PostMapping(path = "/expense")
    public ResponseEntity<Object> createExpense(@RequestBody Expense expense) {
        expenseService.createExpense(expense);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "/expense")
    public void updateExpense(@RequestBody Expense expense) {
        expenseService.updateExpense(expense);
    }

    /**
     * Sets (replaces) the participants for a specified Expense.
     *
     * @param id The ID of the Expense for which participants are to be set
     * @param participants The new list of participants for the Expense
     */
    @PutMapping(path = "/expense/{id}/participants")
    public void setParticipants(@PathVariable String id, @RequestBody List<Person> participants) {
        expenseService.setParticipants(id, participants);
    }


    /**
     * Sets the description for a specified Expense.
     *
     * @param id The id of the Expense
     * @param description The new description
     */
    @PutMapping(path = "/expense/{id}/description")
    public void setDescription(@PathVariable String id, @RequestBody String description) {
        expenseService.setDescription(id, description);
    }

    /**
     * Sets the receiver for a specified Expense.
     *
     * @param id The id of the Expense
     * @param receiver The new receiver
     */
    @PutMapping(path = "/expense/{id}/receiver")
    public void setReceiver(@PathVariable String id, @RequestBody Person receiver) {
        expenseService.setReceiver(id, receiver);
    }

    /**
     * Sets the paid amount for a specified Expense.
     *
     * @param id The id of the Expense
     * @param paid The new paid amount
     */
    @PutMapping(path = "/expense/{id}/paid")
    public void setPaid(@PathVariable String id, @RequestBody BigDecimal paid) {
        expenseService.setPaid(id, paid);
    }

    /**
     * Sets the tag for a specified Expense.
     *
     * @param id The id of the Expense
     * @param tag The new tag
     */
    @PutMapping(path = "/expense/{id}/tag")
    public void setTag(@PathVariable String id, @RequestBody Tag tag) {
        expenseService.setTag(id, tag);
    }

    /**
     * Sets the paymentDateTime for a specified Expense.
     *
     * @param id The id of the Expense
     * @param paymentDateTime The new paymentDateTime
     */
    @PutMapping(path = "/expense/{id}/paymentDateTime")
    public void setPaymentDateTime(@PathVariable String id, @RequestBody Instant paymentDateTime) {
        expenseService.setPaymentDateTime(id, paymentDateTime);
    }

    /**
     * Adds a participant to an Expense.
     *
     * @param id The ID of the Expense to which a participant will be added
     * @param participant The participant to be added
     */
    @PostMapping(path = "/expense/{id}/participant")
    public void addParticipant(@PathVariable String id, @RequestBody Person participant) {
        expenseService.addParticipant(id, participant);
    }


    /**
     * Adds multiple participants to an Expense.
     *
     * @param id The id of the Expense
     * @param participants The participants to add
     */
    @PostMapping(path = "/expense/{id}/addParticipants")
    public void addParticipants(@PathVariable String id, @RequestBody List<Person> participants) {
        expenseService.addParticipants(id, participants);
    }

    /**
     * Removes a participant from an Expense.
     *
     * @param expenseId The ID of the Expense from which the participant will be removed
     * @param participantId The ID of the participant to remove
     */
    @DeleteMapping(path = "/expense/{expenseId}/participants/{participantId}")
    public void removeParticipant(
        @PathVariable String expenseId,
        @PathVariable String participantId
    ) {
        expenseService.removeParticipant(expenseId, participantId);
    }


    /**
     * Returns the share for the specified Expense.
     *
     * @param id The id of the Expense for which the share should be returned
     * @return The requested share
     */
    @GetMapping(path = "/expense/{id}/share")
    public BigDecimal getShare(@PathVariable String id) {
        return expenseService.getShare(id);
    }

    /**
     * Gets the participants of a specified Expense.
     *
     * @param id The ID of the Expense for which participants are requested
     * @return A list of participants involved in the Expense
     */
    @GetMapping(path = "/expense/{id}/participants")
    public List<Person> getParticipants(@PathVariable String id) {
        return expenseService.getParticipants(id);
    }


    /**
     * Returns the description for the specified Expense.
     *
     * @param id The id of the Expense for which the description should be returned
     * @return The requested description
     */
    @GetMapping(path = "/expense/{id}/description")
    public String getDescription(@PathVariable String id) {
        return expenseService.getDescription(id);
    }

    /**
     * Returns the receiver for the specified Expense.
     *
     * @param id The id of the Expense for which the receiver should be returned
     * @return The requested receiver
     */
    @GetMapping(path = "/expense/{id}/receiver")
    public Person getReceiver(@PathVariable String id) {
        return expenseService.getReceiver(id);
    }

    /**
     * Returns the paid variable for the specified Expense.
     *
     * @param id The id of the Expense for which the paid variable should be returned
     * @return The requested paid variable
     */
    @GetMapping(path = "/expense/{id}/paid")
    public BigDecimal getPaid(@PathVariable String id) {
        return expenseService.getPaid(id);
    }

    /**
     * Returns the tag for the specified Expense.
     *
     * @param id The id of the Expense for which the tag should be returned
     * @return The requested tag
     */
    @GetMapping(path = "/expense/{id}/tag")
    public Tag getTag(@PathVariable String id) {
        return expenseService.getTag(id);
    }

    /**
     * Returns the paymentDateTime for the specified Expense.
     *
     * @param id The id of the Expense for which the paymentDateTime should be returned
     * @return The requested paymentDateTime
     */
    @GetMapping(path = "/expense/{id}/paymentDateTime")
    public Instant getPaymentDateTime(@PathVariable String id) {
        return expenseService.getPaymentDateTime(id);
    }

    /**
     * Deletes an Expense by its ID.
     *
     * @param id The ID of the Expense to delete
     */
    @DeleteMapping(path = "/expense/{id}")
    public void deleteExpense(@PathVariable String id) {
        expenseService.deleteExpense(id);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex) {
        // Return a ResponseEntity with the NOT_FOUND status
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
