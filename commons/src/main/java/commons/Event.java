package commons;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;


/**
 * This is the data Object for an Event.
 */
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @Column(unique = true)
    String code;
    String title;
    String description;
    @OneToMany
    ArrayList<Person> people;
    @ManyToMany
    ArrayList<Tag> tags;
    @OneToMany
    ArrayList<Expense> expenses;
    @OneToMany
    ArrayList<Payment> payments;
    Instant creationDate;

    /**
     * Creates a new Event.
     *
     * @param title       The title of the Event.
     * @param description The description of the Event.
     */
    public Event(
        String title,
        String description
    ) {
        this.code = generateInviteCode();
        this.title = title;
        this.description = description;
        this.people = new ArrayList<Person>();
        // TODO: add the three standard Tags (extended feature)
        this.tags = new ArrayList<Tag>();
        this.expenses = new ArrayList<Expense>();
        this.payments = new ArrayList<Payment>();
        this.creationDate = Instant.now();
    }

    /**
     * Creates a new Event with predefined Tags.
     *
     * @param title       The title of the Event.
     * @param description The description of the Event.
     * @param tags        The Tags of the Event
     */
    public Event(
        String title,
        String description,
        ArrayList<Tag> tags
    ) {
        this.code = generateInviteCode();
        this.title = title;
        this.description = description;
        this.people = new ArrayList<Person>();
        this.tags = tags;
        this.expenses = new ArrayList<Expense>();
        this.payments = new ArrayList<Payment>();
        this.creationDate = Instant.now();
    }

    /**
     * Empty constructor for JPA.
     */
    protected Event() {
    }

    /**
     * The Event constructor used for imports.
     *
     * @param title        The Event title.
     * @param description  The Event description.
     * @param people       The ArrayList with all Persons in the Event.
     * @param tags         The ArrayList with all the Tags in the Event.
     * @param expenses     The ArrayList with all the Expenses in the Event.
     * @param payments     The ArrayList with all the Payments in the Event.
     * @param creationDate Creation date of the Event.
     */
    public Event(String title, String description, ArrayList<Person> people,
                 ArrayList<Tag> tags, ArrayList<Expense> expenses, ArrayList<Payment> payments,
                 Instant creationDate) {
        this.code = generateInviteCode();
        this.title = title;
        this.description = description;
        this.people = people;
        this.tags = tags;
        this.expenses = expenses;
        this.payments = payments;
        this.creationDate = creationDate;
    }

    /**
     * A UUID is not unique, however the chance of having duplicates is minimal.
     */
    private static String generateInviteCode() {
        return UUID.randomUUID().toString();
    }

    /**
     * Checks if the Object that is provided is equal to this Event object.
     *
     * @param o The Object that has to be compared to this Event Object
     * @return true if they are equal, false when they are not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Event event = (Event) o;

        if (!Objects.equals(code, event.code)) {
            return false;
        }
        if (!Objects.equals(title, event.title)) {
            return false;
        }
        if (!Objects.equals(description, event.description)) {
            return false;
        }
        if (!Objects.equals(people,
            event.people)) {  // <-- problem! This checks if the lists are identical,
            // but not if their contents are the same, so we have to
            // iterate over the lists
            return false;
        }
        if (!Objects.equals(tags, event.tags)) {
            return false;
        }
        if (!Objects.equals(expenses, event.expenses)) {
            return false;
        }
        if (!Objects.equals(payments, event.payments)) {
            return false;
        }
        return Objects.equals(creationDate, event.creationDate);
    }

    /**
     * Provides a hash for the current Object.
     *
     * @return the hash of this Object
     */
    @Override
    public int hashCode() {
        return Objects.hash(code, title, description, tags, expenses, payments, creationDate);
    }

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public void setPayments(ArrayList<Payment> payments) {
        this.payments = payments;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

}
