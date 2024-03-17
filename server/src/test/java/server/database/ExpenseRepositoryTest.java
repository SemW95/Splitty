package server.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import commons.Colour;
import commons.Expense;
import commons.Person;
import commons.Tag;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ExpenseRepositoryTest {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ColourRepository colourRepository;

    private Expense testExpense1;


    @BeforeEach
    void setUp() {
        Person testPerson1 = new Person("Alice", "Hennessy", "aliceh@domain.name", "NL80RABO3330533676", "ABNANL2A");
        Person testPerson2 = new Person("Bobertus", "Fireball", "bobf@domain.name", "NL92RABO4452759149", "AGBLLT2X");
        Person testPerson3 = new Person("Charlie", "Smith", "charlies@domain.name", "GB94BARC10201530093459", "BOFAUS3N");
        Person testPerson4 = new Person("David", "Brown", "davidb@domain.name", "NL18ABNA2262368678", "DEUTDEFF");
        Person testPerson5 = new Person("Eve", "Johnson", "evej@domain.name", "NL24RABO8487376045", "BNPAFRPP");

        List<Person> participants1 = new ArrayList<>();
        participants1.add(testPerson1);
        participants1.add(testPerson3);
        participants1.add(testPerson4);
        participants1.add(testPerson5);
        personRepository.saveAll(List.of(testPerson1, testPerson2, testPerson3, testPerson4, testPerson5));

        BigDecimal paid1 = BigDecimal.valueOf(250);

        Colour faintSunshine = new Colour("#FDFD96");
        colourRepository.saveAll(List.of(faintSunshine));


        Tag tag1 = new Tag("Faint Sunshine", faintSunshine );
        tagRepository.saveAll(List.of(tag1));


        Instant paymentDateTime1 = Instant.ofEpochMilli(712000712);

        testExpense1 = new Expense("pay for drinks", participants1, testPerson2, paid1, tag1, paymentDateTime1);

        expenseRepository.save(testExpense1);
    }


    @AfterEach
    void tearDown() {
        expenseRepository.delete(testExpense1);
    }

    @Test
    void testFindById() {
        Expense e1 = expenseRepository.findById(testExpense1.getId()).orElse(null);
        assertNotNull(e1);

        assertEquals(testExpense1.getDescription(), e1.getDescription());
        assertEquals(testExpense1.getPaid(), e1.getPaid());
        assertEquals(testExpense1.getReceiver(), e1.getReceiver());
        assertEquals(testExpense1.getParticipants(), e1.getParticipants());
        assertEquals(testExpense1.getDescription(), e1.getDescription());
        assertEquals(testExpense1.getPaymentDateTime(), e1.getPaymentDateTime());
    }

    @Test
    void testFindByDescription() {
        List<Expense> elist1 = expenseRepository.findByDescriptionContainingIgnoreCase(testExpense1.getDescription());

        Expense e1 = elist1.get(0);
        assertNotNull(e1);

        assertEquals(testExpense1.getDescription(), e1.getDescription());
        assertEquals(testExpense1.getPaid(), e1.getPaid());
        assertEquals(testExpense1.getReceiver(), e1.getReceiver());
        assertEquals(testExpense1.getParticipants(), e1.getParticipants());
        assertEquals(testExpense1.getDescription(), e1.getDescription());
        assertEquals(testExpense1.getPaymentDateTime(), e1.getPaymentDateTime());
    }
}