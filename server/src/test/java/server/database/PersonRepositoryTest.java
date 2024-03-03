package server.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import commons.Person;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    private Person testPerson1;
    private Person testPerson2;


    @BeforeEach
    void setUp() {
        // Initialize test data before each test method
        testPerson1 = new Person("Alice", "Hennessy", "aliceh@domain.name", "NL91ABNA0417164300",
            "ZUOBJEO6XXX");
        testPerson2 = new Person("Bobertus", "Fireball", "aliceh@domain.name", "NL22INGB0443244300",
            "BOOKTP99A3E");
        personRepository.save(testPerson1);
        personRepository.save(testPerson2);
    }

    @AfterEach
    void tearDown() {
        // Release test data after each test method
        personRepository.delete(testPerson1);
        personRepository.delete(testPerson2);
    }

    @Test
    void testFindById() {
        Person p1 = personRepository.findById(testPerson1.getId()).orElse(null);
        assertNotNull(p1);

        assertEquals(testPerson1.getBic(), p1.getBic());
        assertEquals(testPerson1.getIban(), p1.getIban());
        assertEquals(testPerson1.getId(), p1.getId());
        assertEquals(testPerson1.getLastName(), p1.getLastName());
        assertEquals(testPerson1.getEmail(), p1.getEmail());
        assertEquals(testPerson1.getFirstName(), p1.getFirstName());
        assertEquals(testPerson1.getClass(), p1.getClass());


        Person p2 = personRepository.findById(testPerson2.getId()).orElse(null);
        assertNotNull(p2);

        assertEquals(testPerson2.getBic(), p2.getBic());
        assertEquals(testPerson2.getIban(), p2.getIban());
        assertEquals(testPerson2.getId(), p2.getId());
        assertEquals(testPerson2.getEmail(), p2.getEmail());
        assertEquals(testPerson2.getClass(), p2.getClass());
        assertEquals(testPerson2.getFirstName(), p2.getFirstName());
        assertEquals(testPerson2.getLastName(), p2.getLastName());
    }

    @Test
    void testFindByFirstName() {
        List<Person> p1 = personRepository.findByFirstName(testPerson1.getFirstName());
        assertFalse(p1.isEmpty());
        Person firstInList = p1.get(0);

        assertEquals(testPerson1.getId(), firstInList.getId());
        assertEquals(testPerson1.getFirstName(), firstInList.getFirstName());
        assertEquals(testPerson1.getLastName(), firstInList.getLastName());
        assertEquals(testPerson1.getEmail(), firstInList.getEmail());
        assertEquals(testPerson1.getBic(), firstInList.getBic());
        assertEquals(testPerson1.getIban(), firstInList.getIban());
        assertEquals(testPerson1.getClass(), firstInList.getClass());


        List<Person> p2 = personRepository.findByFirstName(testPerson2.getFirstName());
        assertFalse(p2.isEmpty());
        Person firstInList2 = p2.get(0);

        assertEquals(testPerson2.getId(), firstInList2.getId());
        assertEquals(testPerson2.getFirstName(), firstInList2.getFirstName());
        assertEquals(testPerson2.getLastName(), firstInList2.getLastName());
        assertEquals(testPerson2.getEmail(), firstInList2.getEmail());
        assertEquals(testPerson2.getBic(), firstInList2.getBic());
        assertEquals(testPerson2.getIban(), firstInList2.getIban());
        assertEquals(testPerson2.getClass(), firstInList2.getClass());
    }

    @Test
    void testFindPeopleByFirstNameContainingIgnoreCase() {
        List<Person> p1 = personRepository.findPeopleByFirstNameContainingIgnoreCase("lic");
        assertTrue(!p1.isEmpty());


        Person firstInList1 = p1.get(0);
        assertEquals(testPerson1.getBic(), firstInList1.getBic());
        assertEquals(testPerson1.getFirstName(), firstInList1.getFirstName());
        assertEquals(testPerson1.getClass(), firstInList1.getClass());
        assertEquals(testPerson1.getId(), firstInList1.getId());
        assertEquals(testPerson1.getIban(), firstInList1.getIban());
        assertEquals(testPerson1.getEmail(), firstInList1.getEmail());
        assertEquals(testPerson1.getLastName(), firstInList1.getLastName());


        //Bobertus
        String realFirstName = testPerson2.getFirstName();
        //bertu
        String realisticSearchString = realFirstName.substring(2, 7);

        List<Person> p2 =
            personRepository.findPeopleByFirstNameContainingIgnoreCase(realisticSearchString);
        Person firstInList2 = p2.get(0);
        assertNotNull(p2);

        assertEquals(testPerson2.getClass(), firstInList2.getClass());
        assertEquals(testPerson2.getFirstName(), firstInList2.getFirstName());
        assertEquals(testPerson2.getBic(), firstInList2.getBic());
        assertEquals(testPerson2.getEmail(), firstInList2.getEmail());
        assertEquals(testPerson2.getLastName(), firstInList2.getLastName());
        assertEquals(testPerson2.getIban(), firstInList2.getIban());
        assertEquals(testPerson2.getId(), firstInList2.getId());


    }
}