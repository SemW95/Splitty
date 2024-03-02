package server.api;

import commons.Person;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import server.service.PersonService;

@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(path="/person")
    @ResponseBody
    public List<Person> getAllPerson() {
        return personService.getAllPerson();
    }

    @GetMapping(path="/person/{id}")
    @ResponseBody
    public Person getPersonById(@PathVariable(name="id") Long id) {
        return personService.getPersonById(id);
    }


    @PostMapping(path="/person")
    public void addPerson(@RequestBody Person person) {

        personService.addPerson(person);
//        try {
//            personService.addPerson(person);
//        } catch (IllegalStateException ignored) {
//            throw new IllegalStateException(
//                "There is already a person with this id"
//            );
//        }
    }

    @DeleteMapping(path="/person/{id}")
    public void deletePerson(@PathVariable(name="id") Long id) {
        personService.deletePerson(id);
    }


}
