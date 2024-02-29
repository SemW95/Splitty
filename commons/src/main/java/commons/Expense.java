package commons;

import java.util.ArrayList;
import java.util.Objects;

/** The class that contains all the info for an expense.
 */
public class Expense {
    ArrayList<Debt> debts;
    TotalDebt totalDebt;
    Person receiver;
    Money paid;

    /** Creates the Expense class.
     *
     * @param receiver The Person that has paid for the Expense.
     * @param paid The amount that the Person paid for the Expense.
     */
    public Expense(Person receiver, Money paid) {
        this.participants = new ArrayList<Person>();
        this.receiver = receiver;
        this.paid = paid;
    }

    public void addParticipant(Person participant) {
        participants.add(participant);
    }

    public void removeParticipant(Person participant) {
        // TODO: get index of participant
        // TODO: remove index
    }

    /** gets the share that should be paid /person.
     *
     * @return the share a person needs to pay for this expense;
     */
    public Money getShare() {
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
        return Objects.equals(debts, expense.debts)
            && Objects.equals(totalDebt, expense.totalDebt)
            && Objects.equals(receiver, expense.receiver);
    }


    /**
     * Provides a hash for the current Object.
     *
     * @return the hash of this Object
     */
    @Override
    public int hashCode() {
        return Objects.hash(debts, totalDebt, receiver);
    }

    public ArrayList<Person> getParticipants() {
        return participants;
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }

    public Money getPaid() {
        return paid;
    }

    public void setPaid(Money paid) {
        this.paid = paid;
    }
}
