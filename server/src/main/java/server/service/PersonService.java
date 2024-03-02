package server.service;

import commons.Person;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.PersonRepository;

/**
 * thing.
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
     *
     * @param id
     */
    public void deletePerson(Long id){
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
