package commons;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The class that contains all the info for an expense.
 */
public class Expense {
    ArrayList<Person> participants;
    Person receiver;
    BigDecimal paid;
    Tag tag;
    Instant creationDate; // "Detailed Expenses" extension

    /**
     * Creates the Expense class.
     *
     * @param receiver The Person that has paid for the Expense.
     * @param paid     The amount that the Person paid for the Expense.
     */
    public Expense(Person receiver, BigDecimal paid) {
        this.participants = new ArrayList<Person>();
        this.receiver = receiver;
        this.paid = paid;
        this.creationDate = Instant.now();
    }

    /**
     * Creates the Expense class with a date.
     *
     * @param receiver     The Person that has paid for the Expense.
     * @param paid         The amount that the Person paid for the Expense.
     * @param creationDate Creation date of the Expense.
     */
    public Expense(Person receiver, BigDecimal paid, Instant creationDate) {
        this.participants = new ArrayList<Person>();
        this.receiver = receiver;
        this.paid = paid;
        this.creationDate = creationDate;
    }

    public void addParticipant(Person participant) {
        participants.add(participant);
    }

    public void removeParticipant(Person participant) {
        participants.remove(participant);
    }

    /**
     * gets the share that should be paid /person.
     *
     * @return the share a person needs to pay for this expense;
     */
    public BigDecimal getShare() {
        int totalNoParticipants = participants.size() + 1;
        // TODO: return (Money) paid/totalNoParticipants
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Expense expense = (Expense) o;
        return Objects.equals(participants, expense.participants)
            && Objects.equals(receiver, expense.receiver)
            && Objects.equals(paid, expense.paid) && Objects.equals(tag, expense.tag)
            && Objects.equals(creationDate, expense.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participants, receiver, paid, tag, creationDate);
    }

    public ArrayList<Person> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Person> participants) {
        this.participants = participants;
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }
}
