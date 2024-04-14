package commons;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * This is the data Object for an Event.
 */
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(unique = true)
    String code;
    String title;
    String description;
    @OneToMany
    List<Person> people;
    @OneToMany
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
     * A code is not unique, however the chance of having duplicates is minimal.
     */
    private static String generateInviteCode() {
        // TODO: check if it already exists
        return RandomStringUtils.randomAlphanumeric(8);
    }

    public void updateLastModifiedDateTime() {
        this.lastModifiedDateTime = Instant.now();
    }


    /**
     * Calculates how much 'person' owes everyone else (also takes "payments" into account).
     * For example: A owes B 30€, B owes A 20€, A has paid B 5€.
     * It will return that A owes B 5€.
     * IMPORTANT: it calculates only direct debts.
     * So you should not use the data directly to calculate settlements.
     * NOTE: negative values means that they are owed that amount.
     *
     * @param personId the id of the Person who owes others
     * @return a map where the key is a Person and the value is how much 'person' owes that person.
     */
    private Map<Person, BigDecimal> calculateDebtSimple(String personId) {
        HashMap<Person, BigDecimal> map = new HashMap<>();

        // Calculate debts for expenses
        for (Expense expense : expenses) {
            // Ignore expenses which don't have a receiver or participants
            if (expense.receiver == null || expense.participants == null
                || expense.participants.isEmpty()) {
                continue;
            }
            if (expense.receiver.getId().equals(personId)) {
                for (Person participant : expense.participants) {
                    map.merge(participant, expense.calculateShare().negate(), BigDecimal::add);
                }
            } else if (expense.participantsContainId(personId)) {
                map.merge(expense.receiver, expense.calculateShare(), BigDecimal::add);
            }
        }

        // Update debts with payments
        for (Payment payment : payments) {
            // Ignore payments which don't have a payer or receiver
            if (payment.payer == null || payment.receiver == null) {
                continue;
            }
            if (payment.receiver.getId().equals(personId)) {
                map.merge(payment.payer, payment.amount, BigDecimal::add);
            } else if (payment.payer.getId().equals(personId)) {
                map.merge(payment.receiver, payment.amount.negate(), BigDecimal::add);
            }
        }

        return map;
    }

    /**
     * Calculates how much 'person' owes others in total.
     * If it's negative, it means 'person' is owed that amount.
     *
     * @param personId the personId for who owes others
     * @return the amount
     */
    public BigDecimal calculateDebtSum(String personId) {
        Map<Person, BigDecimal> debts = calculateDebtSimple(personId);
        return debts.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calculates how much 'person' owes others in total.
     * If it's negative, it means 'person' is owed that amount.
     *
     * @param person the person who owes others
     * @return the amount
     */
    public BigDecimal calculateDebtSum(Person person) {
        return calculateDebtSum(person.getId());
    }

    /**
     * Calculates what payments are needed to be made in order to settle everyone's debts.
     *
     * @return a list of payments
     */
    public List<Payment> calculateSettlements() {
        ArrayList<Payment> settlements = new ArrayList<>();

        // the people who owe, sorted
        ArrayList<Pair<Person, BigDecimal>> positive = new ArrayList<>();

        // the people who are owed, sorted
        ArrayList<Pair<Person, BigDecimal>> negative = new ArrayList<>();

        List<Person> sorted =
            people.stream().sorted(Comparator.comparing(Person::getId)).toList();
        for (Person person : sorted) {
            BigDecimal debt = calculateDebtSum(person.getId());
            Pair<Person, BigDecimal> pair = new Pair<>(person, debt);
            if (debt.signum() == 1) {
                positive.add(pair);
            } else if (debt.signum() == -1) {
                negative.add(pair);
            }
        }

        while (!positive.isEmpty() && !negative.isEmpty()) {
            var p = positive.getFirst();
            var n = negative.getFirst();

            BigDecimal amount = p.getRight().min(n.getRight().negate());
            settlements.add(new Payment(p.getLeft(), n.getLeft(), amount));
            p.setRight(p.getRight().subtract(amount));
            n.setRight(n.getRight().add(amount));

            if (p.getRight().signum() == 0) {
                positive.removeFirst();
            }
            if (n.getRight().signum() == 0) {
                negative.removeFirst();
            }
        }

        if (!positive.isEmpty() || !negative.isEmpty()) {
            throw new RuntimeException("Could not settle everyone's debts");
        }

        return settlements;
    }

    /**
     * Calculates how much 'person' needs to pay others to settle the debts.
     * Takes all debts (and payments) into account so that
     * there only n-1 payments are needed to be made to settle everything
     * (where n is the amount of people).
     * NOTE: does not return negative values.
     *
     * @param personId the id of the person who owes others
     * @return a map where the key is a person and the value is how much 'person' owes that person.
     */
    public Map<Person, BigDecimal> calculateDebt(String personId) {
        HashMap<Person, BigDecimal> map = new HashMap<>();

        for (Payment settlement : calculateSettlements()) {
            if (settlement.payer.getId().equals(personId)) {
                if (map.containsKey(settlement.receiver)) {
                    throw new RuntimeException("Map should not already contain a receiver");
                }

                map.put(settlement.receiver, settlement.amount);
            }
        }

        return map;
    }

    /**
     * Calculates how much money was spent in total for an event.
     *
     * @return the total amount spent
     */
    public BigDecimal totalAmountSpent() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Expense expense : expenses) {
            sum = sum.add(expense.paid);
        }
        return sum;
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
        return Objects.equals(id, event.id) && Objects.equals(code, event.code)
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    /**
     * TODO Causes problems for testing.
     *
     * @param code code to be set
     */
    public void setCode(String code) {
        // updateLastModifiedDateTime();
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        updateLastModifiedDateTime();
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        updateLastModifiedDateTime();
        this.description = description;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        updateLastModifiedDateTime();
        this.people = people;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        updateLastModifiedDateTime();
        this.tags = tags;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        updateLastModifiedDateTime();
        this.expenses = expenses;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        updateLastModifiedDateTime();
        this.payments = payments;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        updateLastModifiedDateTime();
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        updateLastModifiedDateTime();
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

    private static class Pair<A, B> {
        private A left;
        private B right;

        public Pair(A left, B right) {
            this.left = left;
            this.right = right;
        }

        public A getLeft() {
            return left;
        }

        public B getRight() {
            return right;
        }

        public void setLeft(A left) {
            this.left = left;
        }

        public void setRight(B right) {
            this.right = right;
        }
    }
}