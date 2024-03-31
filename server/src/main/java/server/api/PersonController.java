package server.api;

import commons.Person;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import server.service.PersonService;

/**
 * Controller for Person. [CONT -> SERV -> REPO]
 */
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Returns all persons in the database,
     * if no people returns empty list.
     *
     * @return list of persons
     */
    @GetMapping(path = "/person")
    @ResponseBody
    public List<Person> getAllPerson() {
        return personService.getAllPerson();
    }

    /**
     * Searches Person on specified id,
     * throws exception if id doesn't exist.
     *
     * @param id that is searched
     * @return Person with specified id
     */
    @GetMapping(path = "/person/{id}")
    @ResponseBody
    public Person getPersonById(@PathVariable String id) {
        return personService.getPersonById(id);
    }

    /**
     * Adds a person object to the database,
     * throws exception if person already exists.
     *
     * @param person that is to be added
     */
    @PostMapping(path = "/person")
    public Person addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    /**
     * Deletes person with certain id,
     * throws exception if person does not exist.
     *
     * @param id that is to be deleted
     */
    @DeleteMapping(path = "/person/{id}")
    public void deletePerson(@PathVariable String id) {
        personService.deletePerson(id);
    }

    @PutMapping(path = "/person")
    public void updatePerson(@RequestBody Person person) {
        personService.updatePerson(person);
    }
}
