package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventTest {

    private Event test1;
    private Event test2;
    private ArrayList<Tag> tags1;
    private ArrayList<Tag> tags2;
    private Tag tag1;
    private Tag tag2;
    private LocalDate startNow;
    private LocalDate endNow;
    private Instant now;

    @BeforeEach
    void setUp() {
        tags1 = new ArrayList<Tag>();
        tags2 = new ArrayList<Tag>();
        tag1 = new Tag("Food", new Colour("#0000FF"));
        tag2 = new Tag("Drinks", new Colour("#FFC0CB"));
        tags1.add(tag1);
        tags1.add(tag2);
        tags2.add(tag1);
        tags2.add(tag2);

        now = Instant.now();
        startNow = LocalDate.now();
        endNow = LocalDate.now();

        test1 = new Event("Dinner and Drinks", "Dinner and drinks with the group",
            new ArrayList<Person>(), tags1, new ArrayList<Expense>(),
            new ArrayList<Payment>(), startNow, endNow, now);
        test2 = new Event("Dinner and Drinks", "Dinner and drinks with the group",
            new ArrayList<Person>(), tags2, new ArrayList<Expense>(),
            new ArrayList<Payment>(), startNow, endNow, now);
        test1.setCode("123");
        test2.setCode("123");
    }

    @Test
    void getTitle() {
        assertEquals("Dinner and Drinks", test1.getTitle(), "Incorrect title");
    }

    @Test
    void setTitle() {
        String newTitle = "NewTitle";
        test1.setTitle(newTitle);
        assertEquals(newTitle, test1.getTitle(), "Setting title failed");
    }

    @Test
    void getDescription() {
        assertEquals("Dinner and drinks with the group", test1.getDescription(),
            "Incorrect description");
    }

    @Test
    void setDescription() {
        String newDescription = "NewDescription";
        test1.setDescription(newDescription);
        assertEquals(newDescription, test1.getDescription(), "Setting description failed");
    }

    @Test
    void getTags() {
        assertEquals(tags1, test1.getTags(), "Incorrect tags");
    }

    @Test
    void setTags() {
        ArrayList<Tag> newTags = new ArrayList<>();
        newTags.add(new Tag("Travel", new Colour("#008000")));
        test1.setTags(newTags);
        assertEquals(newTags, test1.getTags(), "Setting tags failed");
    }

    @Test
    void getExpenses() {
        assertEquals(new ArrayList<Expense>(), test1.getExpenses(), "Incorrect expenses");
    }

    @Test
    void setExpenses() {
        // Create some expenses
        Person receiver1 = new Person("Alice", "needs a surname", "Alice@domain.com",
            "AL35202111090000000001234567", "ZUOBJEO6XXX");
        Person receiver2 =
            new Person("Alice", "needs a surname", "Alice@domain.com",
                "AL35202111090000000001234567", "ZUOBJEO6XXX"); //changed last digit IBAN
        BigDecimal amount1 = new BigDecimal("50");
        BigDecimal amount2 = new BigDecimal("30");
        Expense expense1 = new Expense(receiver1, amount1);
        Expense expense2 = new Expense(receiver2, amount2);

        // Set expenses for test1
        ArrayList<Expense> newExpenses = new ArrayList<>();
        newExpenses.add(expense1);
        newExpenses.add(expense2);
        test1.setExpenses(newExpenses);

        assertEquals(2, test1.getExpenses().size(), "Setting expenses failed");
        assertTrue(test1.getExpenses().contains(expense1), "Expense 1 not found in the list");
        assertTrue(test1.getExpenses().contains(expense2), "Expense 2 not found in the list");
    }

    @Test
    void setCreationDate() {
        Instant newDate = Instant.now().minusSeconds(3600);
        test1.setLastModifiedDateTime(newDate);
        assertEquals(newDate, test1.getLastModifiedDateTime(), "Setting creation date failed");
    }

    @Test
    void equalsSame() {
        assertEquals(test1, test1);
    }

    @Test
    void equalsSameButDifferent() {
        assertEquals(test2, test1);
    }

    @Test
    void testHashCode() {
        assertEquals(test1.hashCode(), test1.hashCode(), "Hash codes should be equal");
    }

    @Test
    void debtTest() {
        Person a =
            new Person("A", "", "email@email.com", "AD1400080001001234567890", "ZUOBJEO6XXX");
        a.setId("personIdA");
        Person b =
            new Person("B", "", "email@email.com", "AD1400080001001234567890", "ZUOBJEO6XXX");
        b.setId("personIdB");
        Person c =
            new Person("C", "", "email@email.com", "AD1400080001001234567890", "ZUOBJEO6XXX");
        c.setId("personIdC");
        Person d =
            new Person("D", "", "email@email.com", "AD1400080001001234567890", "ZUOBJEO6XXX");
        d.setId("personIdD");
        Person e =
            new Person("E", "", "email@email.com", "AD1400080001001234567890", "ZUOBJEO6XXX");
        e.setId("personIdE");

        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense("", Arrays.asList(a, b, c), d, new BigDecimal("40"), null, null));
        expenses.add(new Expense("", Arrays.asList(a, c), b, new BigDecimal("21"), null, null));
        expenses.add(new Expense("", Arrays.asList(d, e), a, new BigDecimal("15"), null, null));
        expenses.add(
            new Expense("", Arrays.asList(a, b, c, d), e, new BigDecimal("15"), null, null));

        List<Payment> payments = new ArrayList<>();

        payments.add(new Payment(a, b, new BigDecimal("3")));
        payments.add(new Payment(b, d, new BigDecimal("1")));

        Event event = new Event("", "",
            Arrays.asList(a, b, c, d, e), new ArrayList<>(), expenses,
            payments, LocalDate.now(), LocalDate.now(), Instant.now());

        assertEquals(0, event.calculateDebtSum(a).compareTo(new BigDecimal("7")));
        assertEquals(0, event.calculateDebtSum(b).compareTo(new BigDecimal("1")));
        assertEquals(0, event.calculateDebtSum(c).compareTo(new BigDecimal("20")));
        assertEquals(0, event.calculateDebtSum(d).compareTo(new BigDecimal("-21")));
        assertEquals(0, event.calculateDebtSum(e).compareTo(new BigDecimal("-7")));
        assertEquals(0, event.totalAmountSpent().compareTo(new BigDecimal("91")));
    }

    @Test
    void debtSettleTest() {
        Person a =
            new Person("A", "", "email@email.com", "AD1400080001001234567890", "ZUOBJEO6XXX");
        a.setId("personIdA");
        Person b =
            new Person("B", "", "email@email.com", "AD1400080001001234567890", "ZUOBJEO6XXX");
        b.setId("personIdB");
        Person c =
            new Person("C", "", "email@email.com", "AD1400080001001234567890", "ZUOBJEO6XXX");
        c.setId("personIdC");
        Person d =
            new Person("D", "", "email@email.com", "AD1400080001001234567890", "ZUOBJEO6XXX");
        d.setId("personIdD");
        Person e =
            new Person("E", "", "email@email.com", "AD1400080001001234567890", "ZUOBJEO6XXX");
        e.setId("personIdE");

        List<Expense> expenses = new ArrayList<>();
        expenses.add(
            new Expense("", Arrays.asList(a, b, c), d, new BigDecimal("40"), null, null));
        expenses.add(
            new Expense("", Arrays.asList(a, c), b, new BigDecimal("21"), null, null));
        expenses.add(
            new Expense("", Arrays.asList(d, e), a, new BigDecimal("15"), null, null));
        expenses.add(
            new Expense("", Arrays.asList(a, b, c, d), e, new BigDecimal("15"), null, null));

        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment(a, b, new BigDecimal("3")));
        payments.add(new Payment(b, d, new BigDecimal("1")));

        Event event = new Event("", "",
            Arrays.asList(a, b, c, d, e), new ArrayList<>(), expenses,
            payments, LocalDate.now(), LocalDate.now(), Instant.now());

        List<Payment> settlementsCheck = new ArrayList<>();
        settlementsCheck.add(new Payment(a, d, new BigDecimal("7.00")));
        settlementsCheck.add(new Payment(b, d, new BigDecimal("1.00")));
        settlementsCheck.add(new Payment(c, d, new BigDecimal("13.00")));
        settlementsCheck.add(new Payment(c, e, new BigDecimal("7.00")));

        List<Payment> settlements = event.calculateSettlements();

        assertEquals(settlementsCheck, settlements);
    }

    @Test
    void hashTest() {
        assertEquals(test1.hashCode(), test2.hashCode());
    }

    @Test
    void settersGetters() {
        assertEquals("123", test1.getCode());

        test1.setId("500");
        assertEquals("500", test1.getId());

        List<Person> people = new ArrayList<>();
        assertEquals(people, test1.getPeople());

        Person a =
            new Person("A", "", "email@email.com", "AD1400080001001234567890", "ZUOBJEO6XXX");
        a.setId("personIdA");
        Person b =
            new Person("B", "", "email@email.com", "AD1400080001001234567890", "ZUOBJEO6XXX");
        people.add(a);
        people.add(b);
        test1.setPeople(people);

        assertEquals(people, test1.getPeople());
    }

    @Test
    void toStringTest() {
        String json = test1.toString();
        String jsonCheck = "Event{"
            + "title='" + test1.title + '\''
            + ", description='" + test1.getDescription() + '\''
            + ", tags=" + test1.getTags()
            + ", lastModifiedDateTime=" + test1.getLastModifiedDateTime()
            + '}';

        assertEquals(jsonCheck, json);
    }
}