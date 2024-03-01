package commons;

import java.util.ArrayList;
import java.util.Objects;

/** The class that contains all the info for an expense.
 */
public class Expense {
    ArrayList<Person> participants;
    Person receiver;
    Money paid;
    Tag tag;

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
        return Objects.equals(participants, expense.participants) &&
            Objects.equals(receiver, expense.receiver) &&
            Objects.equals(paid, expense.paid) && Objects.equals(tag, expense.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participants, receiver, paid, tag);
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

    public Money getPaid() {
        return paid;
    }

    public void setPaid(Money paid) {
        this.paid = paid;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
