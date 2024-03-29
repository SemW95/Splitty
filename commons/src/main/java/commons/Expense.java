package commons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class that contains all the info for an expense.
 */
@Entity
/*Had to add this because JSON parser found a field "share" and broke, but share is
not initialized anywhere. This ignores unknown fields.*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String description;
    @ManyToMany
    List<Person> participants;
    @ManyToOne
    Person receiver;
    BigDecimal paid;
    @ManyToOne
    Tag tag;
    @Column(columnDefinition = "TIMESTAMP")
    Instant paymentDateTime; // "Detailed Expenses" extension

    /**
     * Creates the Expense class.
     *
     * @param receiver The Person that has paid for the Expense.
     * @param paid     The amount that the Person paid for the Expense.
     */
    public Expense(Person receiver, BigDecimal paid) {
        this(
            "",
            new ArrayList<Person>(),
            receiver,
            paid,
            null,
            Instant.now()
        );
    }


    /**
     * Creates the Expense class with a date.
     *
     * @param receiver        The Person that has paid for the Expense.
     * @param paid            The amount that the Person paid for the Expense.
     * @param paymentDateTime Creation date of the Expense.
     */
    public Expense(Person receiver, BigDecimal paid, Instant paymentDateTime) {
        this(
            "",
            new ArrayList<Person>(),
            receiver,
            paid,
            null,
            paymentDateTime
        );
    }

    /**
     * The Expense constructor used for imports.
     *
     * @param description     The description of this Expense
     * @param participants    The list of participants (excluding receiver)
     * @param receiver        The person that paid for this and should be compensated
     * @param paid            The amount that was paid
     * @param tag             The tag on this Expense
     * @param paymentDateTime The DateTime for when this was paid
     */
    public Expense(
        String description,
        List<Person> participants,
        Person receiver,
        BigDecimal paid,
        Tag tag,
        Instant paymentDateTime
    ) {
        this.description = description;
        this.participants = participants;
        this.receiver = receiver;
        this.paid = paid;
        this.tag = tag;
        this.paymentDateTime = paymentDateTime;
    }

    /**
     * Empty constructor for JPA.
     */
    protected Expense() {
    }

    private boolean containsPersonWithId(long id) {
        for (Person participant : this.participants) {
            if (participant.getId() == id) {
                return true; // Found a participant with the same ID
            }
        }
        return false; // No participant with the same ID found
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /** Adds a participant to an Expense.
     *
     * @param participant The participant that should be added
     * @throws IllegalStateException When there already is a Person with that id present
     */
    public void addParticipant(Person participant) throws IllegalStateException {
        if (containsPersonWithId(participant.getId())) {
            participants.add(participant);
        } else {
            throw new IllegalStateException(
                "There already is a Person with this id in the participants ArrayList"
            );
        }
    }

    /** Adds participants to an Expense.
     *
     * @param newParticipants The participants that should be added
     * @throws IllegalStateException When there is one duplicate id
     */
    public void addParticipants(List<Person> newParticipants) throws IllegalStateException {
        for (Person participant : newParticipants) {
            if (containsPersonWithId(participant.getId())) {
                throw new IllegalStateException(
                    "There already is a Person with this id in the participants ArrayList"
                );
            }
        }
        for (Person participant : newParticipants) {
            addParticipant(participant);
        }
    }

    /** Removes a participant.
     *
     * @param participant The participant that should be removed
     */
    public void removeParticipant(Person participant) {
        participants.remove(participant);
    }

    /** Removes a participant.
     *
     * @param id The id of the participant that should be removed
     */
    public void removeParticipant(long id) {
        if (containsPersonWithId(id)) {
            participants.remove(getParticipantById(id));
        }
    }

    /** gets the share that should be paid /person (rounded up to a cent).
     *
     * @return the share a person needs to pay for this expense
     */
    public BigDecimal getShare() {
        BigDecimal totalNoParticipants = new BigDecimal(participants.size() + 1);
        return paid.divide(totalNoParticipants, 2, RoundingMode.CEILING);
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
        return id == expense.id && Objects.equals(description, expense.description)
            && Objects.equals(participants, expense.participants)
            && Objects.equals(receiver, expense.receiver)
            && Objects.equals(paid, expense.paid)
            && Objects.equals(tag, expense.tag)
            && Objects.equals(paymentDateTime, expense.paymentDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, participants, receiver, paid, tag, paymentDateTime);
    }

    public List<Person> getParticipants() {
        return participants;
    }

    /** Gets a participant by it's id.
     *
     * @param id The id of the participant that should be returned
     * @return The requested participant
     * @throws IllegalStateException When there isn't a participant with this id
     */
    public Person getParticipantById(long id) throws IllegalStateException {
        for (Person participant : this.participants) {
            if (participant.getId() == id) {
                return participant;
            }
        }
        throw new IllegalStateException("There is no participant with this id");
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

    public Instant getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(Instant dateOfPayment) {
        this.paymentDateTime = dateOfPayment;
    }

    public long getId() {
        return id;
    }
}
