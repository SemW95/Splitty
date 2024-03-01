package commons;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;


/**
 * This is the data Object for an Event.
 */
public class Event {
    String id;
    String title;
    String description;
    ArrayList<Person> people;
    ArrayList<Tag> tags;
    ArrayList<Expense> expenses;
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
        // TODO: generate id
        this.title = title;
        this.description = description;
        this.people = new ArrayList<Person>();
        // TODO: add the three standard Tags
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
        // TODO: generate id
        this.title = title;
        this.description = description;
        this.people = new ArrayList<Person>();
        this.tags = tags;
        this.expenses = new ArrayList<Expense>();
        this.payments = new ArrayList<Payment>();
        this.creationDate = Instant.now();
    }

    /**
     * The Event constructor used for imports.
     *
     * @param id           The Event id.
     * @param title        The Event title.
     * @param description  The Event description.
     * @param people       The ArrayList with all Persons in the Event.
     * @param tags         The ArrayList with all the Tags in the Event.
     * @param expenses     The ArrayList with all the Expenses in the Event.
     * @param payments     The ArrayList with all the Payments in the Event.
     * @param creationDate Creation date of the Event.
     */
    public Event(String id, String title, String description, ArrayList<Person> people,
                 ArrayList<Tag> tags, ArrayList<Expense> expenses, ArrayList<Payment> payments,
                 Instant creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.people = people;
        this.tags = tags;
        this.expenses = expenses;
        this.payments = payments;
        this.creationDate = creationDate;
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
        return Objects.equals(id, event.id) && Objects.equals(title, event.title)
            && Objects.equals(description, event.description) && Objects.equals(tags, event.tags)
            && Objects.equals(expenses, event.expenses) && Objects.equals(people, event.people)
            && Objects.equals(payments, event.payments)
            && Objects.equals(creationDate, event.creationDate);
    }

    /**
     * Provides a hash for the current Object.
     *
     * @return the hash of this Object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, tags, expenses, payments, creationDate);
    }

    public String getId() {
        return id;
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

    public ArrayList<Payment> getPayments() {
        return payments;
    }


    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }
}
