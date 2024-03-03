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
     */
    public Person getPersonById(Long id) {
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

    /**
     * Deletes person with certain id,
     * throws exception if person does not exist.
     *
     * @param id that is to be deleted
     */
    public void deletePerson(Long id) {
        Optional<Person> optionalPerson = personRepository
            .findById(id);

        if (optionalPerson.isEmpty()) {
            throw new IllegalStateException(
                "There is no person with this id"
            );
        }

        personRepository.deleteById(id);
    }

}
