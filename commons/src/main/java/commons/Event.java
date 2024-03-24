package commons;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    List<Person> people;
    @ManyToMany
    List<Tag> tags;
    @OneToMany
    List<Expense> expenses;
    @OneToMany
    List<Payment> payments;
    @Column(columnDefinition = "TIMESTAMP")
    LocalDate startDate;
    @Column(columnDefinition = "TIMESTAMP")
    LocalDate endDate;
    @Column(columnDefinition = "TIMESTAMP")
    Instant lastModifiedDateTime;

    /**
     * Creates a new Event without dates.
     *
     * @param title       The title of the Event.
     * @param description The description of the Event.
     */
    public Event(
        String title,
        String description
    ) {
        this(
            title,
            description,
            new ArrayList<Tag>(),
            LocalDate.now(),
            LocalDate.now()
        );
    }

    /**
     * Creates a new Event.
     *
     * @param title       The title of the Event.
     * @param description The description of the Event.
     * @param startDate   The date that this Event started.
     * @param endDate     The date that this Event ended.
     */
    public Event(
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate
    ) {
        this(
            title,
            description,
            new ArrayList<Tag>(),
            startDate,
            endDate
        );
    }

    /**
     * Creates a new Event with predefined Tags and without dates.
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
        this(
            title,
            description,
            new ArrayList<Person>(),
            tags,
            new ArrayList<Expense>(),
            new ArrayList<Payment>(),
            LocalDate.now(),
            LocalDate.now()
        );
    }

    /**
     * Creates a new Event with predefined Tags.
     *
     * @param title       The title of the Event.
     * @param description The description of the Event.
     * @param tags        The Tags of the Event
     * @param startDate   The date that this Event started.
     * @param endDate     The date that this Event ended.
     */
    public Event(
        String title,
        String description,
        ArrayList<Tag> tags,
        LocalDate startDate,
        LocalDate endDate
    ) {
        this(
            title,
            description,
            new ArrayList<Person>(),
            tags,
            new ArrayList<Expense>(),
            new ArrayList<Payment>(),
            startDate,
            endDate
        );
    }

    /**
     * Empty constructor for JPA.
     */
    protected Event() {
    }

    /**
     * The Event constructor that is the base for all non-import constructors.
     *
     * @param title       The Event title.
     * @param description The Event description.
     * @param people      The ArrayList with all Persons in the Event.
     * @param tags        The ArrayList with all the Tags in the Event.
     * @param expenses    The ArrayList with all the Expenses in the Event.
     * @param payments    The ArrayList with all the Payments in the Event.
     * @param startDate   The date that this Event started.
     * @param endDate     The date that this Event ended.
     */
    public Event(
        String title,
        String description,
        List<Person> people,
        List<Tag> tags,
        List<Expense> expenses,
        List<Payment> payments,
        LocalDate startDate,
        LocalDate endDate
    ) {
        this.code = generateInviteCode();
        this.title = title;
        this.description = description;
        this.people = people;
        this.tags = tags;
        this.expenses = expenses;
        this.payments = payments;
        this.startDate = startDate;
        this.endDate = endDate;
        updateLastModifiedDateTime();
    }

    /**
     * The Event constructor used for imports.
     *
     * @param title                The Event title.
     * @param description          The Event description.
     * @param people               The ArrayList with all Persons in the Event.
     * @param tags                 The ArrayList with all the Tags in the Event.
     * @param expenses             The ArrayList with all the Expenses in the Event.
     * @param payments             The ArrayList with all the Payments in the Event.
     * @param startDate            The date that this Event started.
     * @param endDate              The date that this Event ended.
     * @param lastModifiedDateTime The date of when this Event was last modified.
     */
    public Event(
        String title,
        String description,
        List<Person> people,
        List<Tag> tags,
        List<Expense> expenses,
        List<Payment> payments,
        LocalDate startDate,
        LocalDate endDate,
        Instant lastModifiedDateTime
    ) {
        this.code = generateInviteCode();
        this.title = title;
        this.description = description;
        this.people = people;
        this.tags = tags;
        this.expenses = expenses;
        this.payments = payments;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    /**
     * A UUID is not unique, however the chance of having duplicates is minimal.
     */
    private static String generateInviteCode() {
        // TODO: check if it already exists
        return UUID.randomUUID().toString();
    }

    public void updateLastModifiedDateTime() {
        // TODO: update the lastModifiedDateTime variable
        this.lastModifiedDateTime = Instant.now();
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
        return id == event.id && Objects.equals(code, event.code)
            && Objects.equals(title, event.title)
            && Objects.equals(description, event.description)
            && Objects.equals(people, event.people)
            && Objects.equals(tags, event.tags)
            && Objects.equals(expenses, event.expenses)
            && Objects.equals(payments, event.payments)
            && Objects.equals(startDate, event.startDate)
            && Objects.equals(endDate, event.endDate)
            && Objects.equals(lastModifiedDateTime, event.lastModifiedDateTime);
    }

    /**
     * Provides a hash for the current Object.
     *
     * @return the hash of this Object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, code, title, description, people, tags, expenses, payments,
            startDate,
            endDate, lastModifiedDateTime);
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

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Instant getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(Instant creationDate) {
        this.lastModifiedDateTime = creationDate;
    }

    @Override
    public String toString() {
        return "Event{"
            + "title='" + title + '\''
            + ", description='" + description + '\''
            + ", tags=" + tags
            + ", lastModifiedDateTime=" + lastModifiedDateTime
            + '}';
    }
}