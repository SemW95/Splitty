package server.service;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import commons.Person;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import server.database.PersonRepository;

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
            when(personRepository.findById("12345")).thenReturn(Optional.of(returnPerson));

            // Checks if no exception is thrown since the id exists
            assertDoesNotThrow(() -> personService.getPersonById("12345"));

            // Checks if the personRepository is actually called (one time)
            verify(personRepository).findById("12345");

        } catch (Exception e) {
            fail("The test itself broke");
        }
    }

    @Test
    void getPersonByIdTestX() {
        try {
            when(personRepository.findById("12345")).thenReturn(Optional.empty());
            assertThrows(IllegalStateException.class, () -> personService.getPersonById("12345"));
            verify(personRepository).findById("12345");
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
    void addPersonSuccessTest() {
        String firstName = "Jane";
        String lastName = "Doe";
        String email = "janedoe@example.com";
        String iban = "DE02500105170137075030";
        String bic = "DEUTDEDBBER";

        // Act
        personService.addPerson(firstName, lastName, email, iban, bic);

        // Assert
        verify(personRepository).save(argThat(person ->
            firstName.equals(person.getFirstName())
                && lastName.equals(person.getLastName())
                && email.equals(person.getEmail())
                && iban.equals(person.getIban())
                && bic.equals(person.getBic())
        ));
    }

    @Test
    void deletePersonByIdTest() {
        try {
            when(personRepository.findById("12345")).thenReturn(Optional.of(returnPerson));
            assertDoesNotThrow(() -> personService.deletePerson("12345"));
            verify(personRepository).deleteById("12345");
        } catch (Exception e) {
            fail("The test itself broke");
        }
    }

    @Test
    void deletePersonByIdTestX() {
        try {
            when(personRepository.findById("12345")).thenReturn(Optional.empty());
            assertThrows(IllegalStateException.class, () -> personService.deletePerson("12345"));
            verify(personRepository).findById("12345");
        } catch (Exception e) {
            fail("The test itself broke");
        }
    }


    @Test
    void getAllPersonTest() {
        when(personRepository.findAll()).thenReturn(Collections.singletonList(returnPerson));
        List<Person> persons = personService.getAllPerson();
        assertFalse(persons.isEmpty());
        assertEquals(1, persons.size());
        verify(personRepository).findAll();
    }

    @Test
    void getFirstNameTest() {
        when(personRepository.findById(any())).thenReturn(Optional.of(returnPerson));
        String firstName = personService.getFirstName("12345");
        assertEquals("Bobertus", firstName);
        verify(personRepository).findById("12345");
    }

    @Test
    void setFirstNameTest() {
        when(personRepository.findById(any())).thenReturn(Optional.of(returnPerson));
        assertDoesNotThrow(() -> personService.setFirstName("12345", "UpdatedName"));
        verify(personRepository).save(returnPerson);
        assertEquals("UpdatedName", returnPerson.getFirstName());
    }

    @Test
    void setEmailInvalidTest() {
        when(personRepository.findById(any())).thenReturn(Optional.of(returnPerson));
        assertThrows(
            IllegalArgumentException.class,
            () -> personService.setEmail("12345", "invalidEmail")
        );
    }

    @Test
    void setIbanInvalidTest() {
        when(personRepository.findById(any())).thenReturn(Optional.of(returnPerson));
        assertThrows(
            IllegalArgumentException.class,
            () -> personService.setIban("12345", "invalidIBAN")
        );
    }

    @Test
    void getLastNameTest() {
        when(personRepository.findById(any())).thenReturn(Optional.of(returnPerson));
        String lastName = personService.getLastName("12345");
        assertEquals("Fireball", lastName);
        verify(personRepository).findById("12345");
    }

    @Test
    void getEmailTest() {
        when(personRepository.findById(any())).thenReturn(Optional.of(returnPerson));
        String email = personService.getEmail("12345");
        assertEquals("aliceh@domain.name", email);
        verify(personRepository).findById("12345");
    }

    @Test
    void getIbanTest() {
        when(personRepository.findById(any())).thenReturn(Optional.of(returnPerson));
        String iban = personService.getIban("12345");
        assertEquals("LT601010012345678901", iban);
        verify(personRepository).findById("12345");
    }

    @Test
    void getBicTest() {
        when(personRepository.findById(any())).thenReturn(Optional.of(returnPerson));
        String bic = personService.getBic("12345");
        assertEquals("BOOKTP99A3E", bic);
        verify(personRepository).findById("12345");
    }

    @Test
    void setLastNameTest() {
        when(personRepository.findById(any())).thenReturn(Optional.of(returnPerson));
        assertDoesNotThrow(() -> personService.setLastName("12345", "UpdatedLastName"));
        verify(personRepository).save(returnPerson);
        assertEquals("UpdatedLastName", returnPerson.getLastName());
    }

    @Test
    void setEmailTest() {
        when(personRepository.findById(any())).thenReturn(Optional.of(returnPerson));
        assertDoesNotThrow(
            () -> personService.setEmail("12345", "updatedemail@domain.com")
        );
        verify(personRepository).save(returnPerson);
        assertEquals("updatedemail@domain.com", returnPerson.getEmail());
    }

    @Test
    void setIbanTest() {
        when(personRepository.findById(any())).thenReturn(Optional.of(returnPerson));
        assertDoesNotThrow(() -> personService.setIban("12345", "DE89370400440532013000"));
        verify(personRepository).save(returnPerson);
        assertEquals("DE89370400440532013000", returnPerson.getIban());
    }

    @Test
    void setBicTest() {
        when(personRepository.findById(any())).thenReturn(Optional.of(returnPerson));
        assertDoesNotThrow(() -> personService.setBic("12345", "DEUTDEFF"));
        verify(personRepository).save(returnPerson);
        assertEquals("DEUTDEFF", returnPerson.getBic());
    }

    @Test
    void setBicInvalidTest() {
        when(personRepository.findById(any())).thenReturn(Optional.of(returnPerson));
        assertThrows(
            IllegalArgumentException.class,
            () -> personService.setBic("12345", "invalid")
        );
    }

    @Test
    void setEmailInvalidFormatTest() {
        when(personRepository.findById(any())).thenReturn(Optional.of(returnPerson));
        // This test assumes your service has logic to validate the email format which isn't shown
        assertThrows(
            IllegalArgumentException.class,
            () -> personService.setEmail("12345", "bademailformat")
        );
    }

    @Test
    void updatePersonSuccessTest() {
        // Arrange
        String id = UUID.randomUUID().toString(); // Simulate an existing person ID
        String firstName = "Jane";
        String lastName = "Doe";
        String email = "janedoe@example.com";
        String newEmail = "newjanedoe@example.com"; // Simulate an update
        String iban = "DE02500105170137075030";
        String bic = "DEUTDEDBBER";

        // Create a person object that simulates an existing person in the database
        Person existingPerson = new Person(firstName, lastName, email, iban, bic);
        ReflectionTestUtils.setField(existingPerson, "id", id); // Set the ID field, as it's generated by the DB

        // Mock the findById to simulate that the person exists
        when(personRepository.findById(id)).thenReturn(Optional.of(existingPerson));

        // Update person details
        Person updatedPerson = new Person(firstName, lastName, newEmail, iban, bic);
        ReflectionTestUtils.setField(updatedPerson, "id", id); // Ensure the ID matches for update

        // Act
        personService.updatePerson(updatedPerson);

        // Assert
        verify(personRepository).save(argThat(person ->
            id.equals(person.getId())
                && firstName.equals(person.getFirstName())
                && lastName.equals(person.getLastName())
                && newEmail.equals(person.getEmail()) // Assert the email was updated
                && iban.equals(person.getIban())
                && bic.equals(person.getBic())
        ));
    }

    @Test
    void updatePersonFailureTest() {
        // Arrange
        String nonExistentId = UUID.randomUUID().toString(); // Simulate a non-existent person ID
        String firstName = "Jane";
        String lastName = "Doe";
        String email = "janedoe@example.com";
        String iban = "DE02500105170137075030";
        String bic = "DEUTDEDBBER";

        // Create a person object with the non-existent ID
        Person nonExistentPerson = new Person(firstName, lastName, email, iban, bic);
        ReflectionTestUtils.setField(nonExistentPerson, "id", nonExistentId); // Set the simulated ID

        // Mock the findById to simulate that the person does not exist
        when(personRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            personService.updatePerson(nonExistentPerson);
        }, "There is no person with this id");
    }
}
