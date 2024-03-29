package server.api;

import commons.Expense;
import commons.Person;
import commons.Tag;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping(path = "/expense")
    public void createExpense(@RequestBody Expense expense) {
        expenseService.createExpense(expense);
    }

    @PutMapping (path = "/expense")
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
    public void setParticipants(@PathVariable Long id, @RequestBody List<Person> participants) {
        expenseService.setParticipants(id, participants);
    }


    /**
     * Sets the description for a specified Expense.
     *
     * @param id The id of the Expense
     * @param description The new description
     */
    @PutMapping(path = "/expense/{id}/description")
    public void setDescription(@PathVariable Long id, @RequestBody String description) {
        expenseService.setDescription(id, description);
    }

    /**
     * Sets the receiver for a specified Expense.
     *
     * @param id The id of the Expense
     * @param receiver The new receiver
     */
    @PutMapping(path = "/expense/{id}/receiver")
    public void setReceiver(@PathVariable Long id, @RequestBody Person receiver) {
        expenseService.setReceiver(id, receiver);
    }

    /**
     * Sets the paid amount for a specified Expense.
     *
     * @param id The id of the Expense
     * @param paid The new paid amount
     */
    @PutMapping(path = "/expense/{id}/paid")
    public void setPaid(@PathVariable Long id, @RequestBody BigDecimal paid) {
        expenseService.setPaid(id, paid);
    }

    /**
     * Sets the tag for a specified Expense.
     *
     * @param id The id of the Expense
     * @param tag The new tag
     */
    @PutMapping(path = "/expense/{id}/tag")
    public void setTag(@PathVariable Long id, @RequestBody Tag tag) {
        expenseService.setTag(id, tag);
    }

    /**
     * Sets the paymentDateTime for a specified Expense.
     *
     * @param id The id of the Expense
     * @param paymentDateTime The new paymentDateTime
     */
    @PutMapping(path = "/expense/{id}/paymentDateTime")
    public void setPaymentDateTime(@PathVariable Long id, @RequestBody Instant paymentDateTime) {
        expenseService.setPaymentDateTime(id, paymentDateTime);
    }

    /**
     * Adds a participant to an Expense.
     *
     * @param id The ID of the Expense to which a participant will be added
     * @param participant The participant to be added
     */
    @PostMapping(path = "/expense/{id}/participant")
    public void addParticipant(@PathVariable Long id, @RequestBody Person participant) {
        expenseService.addParticipant(id, participant);
    }


    /**
     * Adds multiple participants to an Expense.
     *
     * @param id The id of the Expense
     * @param participants The participants to add
     */
    @PostMapping(path = "/expense/{id}/addParticipants")
    public void addParticipants(@PathVariable Long id, @RequestBody List<Person> participants) {
        expenseService.addParticipants(id, participants);
    }

    /**
     * Removes a participant from an Expense.
     *
     * @param expenseId The ID of the Expense from which the participant will be removed
     * @param participantId The ID of the participant to remove
     */
    @DeleteMapping(path = "/expense/{expenseId}/participants/{participantId}")
    public void removeParticipant(@PathVariable Long expenseId, @PathVariable Long participantId) {
        expenseService.removeParticipant(expenseId, participantId);
    }


    /**
     * Returns the share for the specified Expense.
     *
     * @param id The id of the Expense for which the share should be returned
     * @return The requested share
     */
    @GetMapping(path = "/expense/{id}/share")
    public BigDecimal getShare(@PathVariable Long id) {
        return expenseService.getShare(id);
    }

    /**
     * Gets the participants of a specified Expense.
     *
     * @param id The ID of the Expense for which participants are requested
     * @return A list of participants involved in the Expense
     */
    @GetMapping(path = "/expense/{id}/participants")
    public List<Person> getParticipants(@PathVariable Long id) {
        return expenseService.getParticipants(id);
    }


    /**
     * Returns the description for the specified Expense.
     *
     * @param id The id of the Expense for which the description should be returned
     * @return The requested description
     */
    @GetMapping(path = "/expense/{id}/description")
    public String getDescription(@PathVariable Long id) {
        return expenseService.getDescription(id);
    }

    /**
     * Returns the receiver for the specified Expense.
     *
     * @param id The id of the Expense for which the receiver should be returned
     * @return The requested receiver
     */
    @GetMapping(path = "/expense/{id}/receiver")
    public Person getReceiver(@PathVariable Long id) {
        return expenseService.getReceiver(id);
    }

    /**
     * Returns the paid variable for the specified Expense.
     *
     * @param id The id of the Expense for which the paid variable should be returned
     * @return The requested paid variable
     */
    @GetMapping(path = "/expense/{id}/paid")
    public BigDecimal getPaid(@PathVariable Long id) {
        return expenseService.getPaid(id);
    }

    /**
     * Returns the tag for the specified Expense.
     *
     * @param id The id of the Expense for which the tag should be returned
     * @return The requested tag
     */
    @GetMapping(path = "/expense/{id}/tag")
    public Tag getTag(@PathVariable Long id) {
        return expenseService.getTag(id);
    }

    /**
     * Returns the paymentDateTime for the specified Expense.
     *
     * @param id The id of the Expense for which the paymentDateTime should be returned
     * @return The requested paymentDateTime
     */
    @GetMapping(path = "/expense/{id}/paymentDateTime")
    public Instant getPaymentDateTime(@PathVariable Long id) {
        return expenseService.getPaymentDateTime(id);
    }

    /**
     * Deletes an Expense by its ID.
     *
     * @param id The ID of the Expense to delete
     */
    @DeleteMapping(path = "/expense/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }
}
