package server.service;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import commons.Person;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.database.PersonRepository;
import server.service.PersonService;

/**
 * Tests the PersonService.
 */
public class PersonServiceTest {

    private PersonService personService;
    @Mock
    private PersonRepository personRepository;
    private Person returnPerson;
    private Person insertPerson;

    @BeforeEach
    void setup() {
        // Mock the @Mock annotated fields.
        MockitoAnnotations.openMocks(this);

        // Initialize the DOC and return values for the Mocked item
        personService = new PersonService(personRepository);
        insertPerson = new Person("Bobertus", "Fireball", "aliceh@domain.name",
            "LT601010012345678901",
            "BOOKTP99A3E");
        returnPerson = new Person("Bobertus", "Fireball", "aliceh@domain.name",
            "LT601010012345678901",
            "BOOKTP99A3E");
    }

    @Test
    void getPersonByIdTest() {
        try {
            //Mock the findById method of personRepository and returns a Person.
            when(personRepository.findById(12345L)).thenReturn(Optional.of(returnPerson));

            // Checks if no exception is thrown since the id exists
            assertDoesNotThrow(() -> personService.getPersonById(12345L));

            // Checks if the personRepository is actually called (one time)
            verify(personRepository).findById(12345L);

        } catch (Exception e) {
            fail("The test itself broke");
        }
    }

    @Test
    void getPersonByIdTestX() {
        try {
            when(personRepository.findById(12345L)).thenReturn(Optional.empty());
            assertThrows(IllegalStateException.class, () -> personService.getPersonById(12345L));
            verify(personRepository).findById(12345L);
        } catch (Exception e) {
            fail("The test itself broke");
        }
    }

    @Test
    void addPersonByIdTest() {
        try {
            when(personRepository.findById(any())).thenReturn(Optional.empty());
            assertDoesNotThrow(() -> personService.addPerson(insertPerson));
            verify(personRepository).save(any());
        } catch (Exception e) {
            fail("The test itself broke");
        }
    }

    @Test
    void addPersonByIdTestX() {
        try {
            when(personRepository.findById(any())).thenReturn(Optional.of(returnPerson));
            assertThrows(IllegalStateException.class, () -> personService.addPerson(insertPerson));
            verify(personRepository).findById(any());
        } catch (Exception e) {
            fail("The test itself broke");
        }
    }

    @Test
    void deletePersonByIdTest() {
        try {
            when(personRepository.findById(12345L)).thenReturn(Optional.of(returnPerson));
            assertDoesNotThrow(() -> personService.deletePerson(12345L));
            verify(personRepository).deleteById(12345L);
        } catch (Exception e) {
            fail("The test itself broke");
        }
    }

    @Test
    void deletePersonByIdTestX() {
        try {
            when(personRepository.findById(12345L)).thenReturn(Optional.empty());
            assertThrows(IllegalStateException.class, () -> personService.deletePerson(12345L));
            verify(personRepository).findById(12345L);
        } catch (Exception e) {
            fail("The test itself broke");
        }
    }

}
