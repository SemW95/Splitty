package server.service;

import commons.Person;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.PersonRepository;

/**
 * Service for Person. [CONT -> SERV -> REPO]
 */
@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPerson() {
        return personRepository.findAll();
    }

    /**
     * Searches Person on specified id,
     * throws exception if id doesn't exist.
     *
     * @param id that is searched
     * @return Person with specified id
     * @throws IllegalStateException When the person with this id doesn't exist
     */
    public Person getPersonById(String id) throws IllegalStateException {
        Optional<Person> optionalPerson = personRepository
            .findById(id);

        if (optionalPerson.isEmpty()) {
            throw new IllegalStateException(
                "There is no person with this id"
            );
        }

        return optionalPerson.get();
    }

    /**
     * Adds a person object to the database,
     * throws exception if person already exists.
     *
     * @param person that is to be added
     */
    public Person createPerson(Person person) {
        if (person.getId() == null || !personRepository.existsById(person.getId())) {
            return personRepository.save(person);
        }

        throw new IllegalStateException(
            "There already is a person with this id"
        );
    }

    /**
     * Adds a person object to the database,
     * throws exception if person already exists.
     *
     * @param person that is to be added
     */
    @Deprecated
    public void addPerson(Person person) {
        Optional<Person> optionalPerson = personRepository
            .findById(person.getId());

        if (optionalPerson.isPresent()) {
            throw new IllegalStateException(
                "There already is a person with this id"
            );
        }

        personRepository.save(person);
    }

    public void addPerson(
        String firstName, String lastName, String email, String iban, String bic
    ) {
        personRepository.save(new Person(firstName, lastName, email, iban, bic));
    }

    /**
     * Gets the email of a person with the given id.
     *
     * @param id The id of the person whose email is retrieved.
     */
    public String getFirstName(String id) {
        // Use existing method to get the person by ID
        Person person = getPersonById(id);

        return person.getFirstName();
    }

    /**
     * Gets the email of a person with the given id.
     *
     * @param id The id of the person whose email is retrieved.
     */
    public String getLastName(String id) {
        // Use existing method to get the person by ID
        Person person = getPersonById(id);

        return person.getLastName();
    }

    /**
     * Gets the email of a person with the given id.
     *
     * @param id The id of the person whose email is to be retrieved.
     */
    public String getEmail(String id) {
        // Use existing method to get the person by ID
        Person person = getPersonById(id);

        return person.getEmail();
    }

    /**
     * Gets the email of a person with the given id.
     *
     * @param id The id of the person whose email is retrieved.
     */
    public String getIban(String id) {
        // Use existing method to get the person by ID
        Person person = getPersonById(id);

        return person.getIban();
    }

    /**
     * Gets the email of a person with the given id.
     *
     * @param id The id of the person whose email is retrieved.
     */
    public String getBic(String id) {
        // Use existing method to get the person by ID
        Person person = getPersonById(id);

        return person.getBic();
    }

    /**
     * Sets the email of a person with the given id.
     *
     * @param id        The id of the person whose email is to be updated.
     * @param firstName The new firstName to be set.
     */
    public void setFirstName(String id, String firstName) {
        // Use existing method to get the person by ID
        Person person = getPersonById(id);

        person.setFirstName(firstName);
        personRepository.save(person);
    }

    /**
     * Sets the email of a person with the given id.
     *
     * @param id       The id of the person whose email is to be updated.
     * @param lastName The new lastName to be set.
     */
    public void setLastName(String id, String lastName) {
        // Use existing method to get the person by ID
        Person person = getPersonById(id);

        person.setLastName(lastName);
        personRepository.save(person);
    }

    /**
     * Sets the email of a person with the given id.
     *
     * @param id    The id of the person whose email is to be updated.
     * @param email The new email to be set.
     */
    public void setEmail(String id, String email) {
        // Use existing method to get the person by ID
        Person person = getPersonById(id);

        // Set the new email
        try {
            person.setEmail(email);
        } catch (IllegalArgumentException e) {
            // Handle the case where email validation fails
            throw new IllegalArgumentException(
                "The provided email is not valid: "
                    + e.getMessage()
            );
        }

        // Save the updated person object to the database
        personRepository.save(person);
    }

    /**
     * Sets the email of a person with the given id.
     *
     * @param id   The id of the person whose email is to be updated.
     * @param iban The new iban to be set.
     */
    public void setIban(String id, String iban) {
        // Use existing method to get the person by ID
        Person person = getPersonById(id);

        try {
            person.setIban(iban);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The provided IBAN is not valid: " + e.getMessage());
        }
        personRepository.save(person);
    }

    /**
     * Sets the email of a person with the given id.
     *
     * @param id  The id of the person whose email is to be updated.
     * @param bic The new bic to be set.
     */
    public void setBic(String id, String bic) {
        // Use existing method to get the person by ID
        Person person = getPersonById(id);

        try {
            person.setBic(bic);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The provided BIC is not valid: " + e.getMessage());
        }
        personRepository.save(person);
    }

    /**
     * Deletes person with certain id,
     * throws exception if person does not exist.
     *
     * @param id that is to be deleted
     * @throws IllegalStateException When the person with this id doesn't exist
     */
    public void deletePerson(String id) throws IllegalStateException {
        Optional<Person> optionalPerson = personRepository
            .findById(id);

        if (optionalPerson.isEmpty()) {
            throw new IllegalStateException(
                "There is no person with this id"
            );
        }

        personRepository.deleteById(id);
    }

    /**
     * Updates a Person in the database.
     *
     * @param person The Person Object with the updated data
     * @throws IllegalStateException When there isn't a Person with this id in the database
     */
    public void updatePerson(Person person) throws IllegalStateException {
        Optional<Person> optionalPerson = personRepository
            .findById(person.getId());

        if (optionalPerson.isEmpty()) {
            throw new IllegalStateException(
                "There is no person with this id"
            );
        }
        personRepository.save(person);

    }
}
