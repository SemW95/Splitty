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
     * returns null if id doesn't exist.
     *
     * @param id that is searched
     * @return Person with specified id
     */
    public Person getPersonById(String id) {
        return personRepository.findById(id).orElse(null);
    }

    /**
     * Adds a person object to the database,
     * throws exception if person already exists.
     *
     * @param person that is to be added
     */
    public Person addPerson(Person person) {
        if (person.getId() != null && personRepository.existsById(person.getId())) {
            throw new IllegalStateException(
                "There already is a person with this id"
            );
        }

        return personRepository.save(person);
    }

    /**
     * Deletes person with certain id,
     * throws exception if person does not exist.
     *
     * @param id that is to be deleted
     */
    public void deletePerson(String id) {
        System.out.println(id);
        Optional<Person> optionalPerson = personRepository
            .findById(id);
        if (optionalPerson.isEmpty()) {
            throw new IllegalStateException(
                "There is no person with this id"
            );
        }

        personRepository.deleteById(id);
    }

    public void updatePerson(Person person) {
        personRepository.save(person);
    }
}
