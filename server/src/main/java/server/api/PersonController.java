package server.api;

import commons.Person;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
     * @throws IllegalStateException when a Person with that id doesn't exists
     */
    @GetMapping(path = "/person/{id}")
    @ResponseBody
    public Person getPersonById(@PathVariable(name = "id") String id) throws IllegalStateException {
        return personService.getPersonById(id);
    }

    /**
     * Searches Person on specified id and returns the firstName,
     * throws exception if id doesn't exist.
     *
     * @param id that is searched
     * @return The firstName of the Person with specified id
     */
    @GetMapping(path = "/person/{id}/first-name")
    @ResponseBody
    public String getFirstNameByPersonId(@PathVariable(name = "id") Long id) {
        return personService.getFirstName(id);
    }

    /**
     * Searches Person on specified id and returns the lastName,
     * throws exception if id doesn't exist.
     *
     * @param id that is searched
     * @return The lastName of the Person with specified id
     */
    @GetMapping(path = "/person/{id}/last-name")
    @ResponseBody
    public String getLastNameByPersonId(@PathVariable(name = "id") Long id) {
        return personService.getLastName(id);
    }

    /**
     * Searches Person on specified id and returns the email,
     * throws exception if id doesn't exist.
     *
     * @param id that is searched
     * @return The email of the Person with specified id
     */
    @GetMapping(path = "/person/{id}/email")
    @ResponseBody
    public String getEmailByPersonId(@PathVariable(name = "id") Long id) {
        return personService.getEmail(id);
    }

    /**
     * Searches Person on specified id and returns the lastName,
     * throws exception if id doesn't exist.
     *
     * @param id that is searched
     * @return The lastName of the Person with specified id
     */
    @GetMapping(path = "/person/{id}/iban")
    @ResponseBody
    public String getIbanByPersonId(@PathVariable(name = "id") Long id) {
        return personService.getIban(id);
    }

    /**
     * Searches Person on specified id and returns the lastName,
     * throws exception if id doesn't exist.
     *
     * @param id that is searched
     * @return The lastName of the Person with specified id
     */
    @GetMapping(path = "/person/{id}/bic")
    @ResponseBody
    public String getBicByPersonId(@PathVariable(name = "id") Long id) {
        return personService.getBic(id);
    }

    /**
     * Searches Person on specified id and sets the firstName,
     * throws exception if id doesn't exist.
     *
     * @param id that is searched
     * @param firstName The firstName that will be set
     */
    @PutMapping(path = "/person/{id}/first-name/{first-name}")
    @ResponseBody
    public void setFirstNameByPersonId(
        @PathVariable(name = "id") Long id,
        @PathVariable(name = "first-name") String firstName
    ) {
        personService.setFirstName(id, firstName);
    }

    /**
     * Searches Person on specified id and sets the lastName,
     * throws exception if id doesn't exist.
     *
     * @param id that is searched
     * @param lastName The lastName that will be set
     */
    @PutMapping(path = "/person/{id}/last-name/{last-name}")
    @ResponseBody
    public void setLastNameByPersonId(
        @PathVariable(name = "id") Long id,
        @PathVariable(name = "last-name") String lastName
    ) {
        personService.setLastName(id, lastName);
    }

    /**
     * Searches Person on specified id and sets the email,
     * throws exception if id doesn't exist.
     *
     * @param id that is searched
     * @param email The email that will be set
     */
    @PutMapping(path = "/person/{id}/email/{email}")
    @ResponseBody
    public void setEmailByPersonId(
        @PathVariable(name = "id") Long id,
        @PathVariable(name = "email") String email
    ) {
        personService.setEmail(id, email);
    }

    /**
     * Searches Person on specified id and sets the iban,
     * throws exception if id doesn't exist.
     *
     * @param id that is searched
     * @param iban The iban that will be set
     */
    @PutMapping(path = "/person/{id}/iban/{iban}")
    @ResponseBody
    public void setIbanByPersonId(
        @PathVariable(name = "id") Long id,
        @PathVariable(name = "iban") String iban
    ) {
        personService.setIban(id, iban);
    }

    /**
     * Searches Person on specified id and sets the bic,
     * throws exception if id doesn't exist.
     *
     * @param id that is searched
     * @param bic The bic that will be set
     */
    @PutMapping(path = "/person/{id}/bic/{bic}")
    @ResponseBody
    public void setBicByPersonId(
        @PathVariable(name = "id") Long id,
        @PathVariable(name = "bic") String bic
    ) {
        personService.setBic(id, bic);
    }

    /**
     * Adds a person object to the database,
     * throws exception if person already exists.
     *
     * @param person that is to be added
     */
    @PostMapping(path = "/person")
    public void addPerson(@RequestBody Person person) {
        personService.addPerson(person);
    }

    /**
     * Adds a person object to the database with person-details,
     * throws exception if person already exists.
     *
     */
    @PostMapping(path = "/person/{first-name}/{last-name}/{email}/{iban}/{bic}")
    public void addPerson(
        @PathVariable(name = "first-name") String firstName,
        @PathVariable(name = "last-name") String lastName,
        @PathVariable(name = "email") String email,
        @PathVariable(name = "iban") String iban,
        @PathVariable(name = "bic") String bic
    ) {
        personService.addPerson(firstName, lastName, email, iban, bic);
    }

    /**
     * Deletes person with certain id,
     * throws exception if person does not exist.
     *
     * @param id that is to be deleted
     */
    @DeleteMapping(path = "/person/{id}")
    public void deletePerson(@PathVariable(name = 'id') String id) {
        personService.deletePerson(id);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex) {
        // Return a ResponseEntity with the NOT_FOUND status
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "/person")
    public void updatePerson(@RequestBody Person person) {
        personService.updatePerson(person);
    }
}
