package server.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Person;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import server.api.PersonController;
import server.service.PersonService;

/** The class for testing the PersonController.
 */
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Person> personList;

    /** The preparation that will run before every test.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new PersonController(personService)).build();
        // Initialize the test Person's here
        Person person1 = new Person(
            "John",
            "Doe",
            "john.doe@example.com",
            "NL39RABO8247360527",
            "DEUTDEDBFRA");
        Person person2 = new Person(
            "Jane",
            "Doe",
            "jane.doe@example.com",
            "DE95500105175827669536",
            "NWBKGB22");
        personList = Arrays.asList(person1, person2);
    }

    @Test
    public void getAllPersonTest() throws Exception {
        given(personService.getAllPerson()).willReturn(personList);
        mockMvc.perform(get("/person"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].firstName", is("John")))
            .andExpect(jsonPath("$[1].firstName", is("Jane")));
    }

    @Test
    public void getPersonByIdTest() throws Exception {
        final Long personId = 1L;
        final Person person = new Person(
            "John",
            "Doe",
            "john.doe@example.com",
            "NL39RABO8247360527",
            "DEUTDEDBFRA");

        given(personService.getPersonById(personId)).willReturn(person);

        mockMvc.perform(get("/person/{id}", personId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    public void updateFirstNameByPersonIdTest() throws Exception {
        final Long personId = 1L;
        final String newFirstName = "UpdatedName";
        doNothing().when(personService).setFirstName(personId, newFirstName);

        mockMvc.perform(put("/person/{id}/first-name/{first-name}", personId, newFirstName))
            .andExpect(status().isOk());
    }

    @Test
    public void addPersonTest() throws Exception {
        Person newPerson = new Person(
            "Alice",
            "Smith",
            "alice.smith@example.com",
            "FR9312739000703455722884M28",
            "AGRIFRPP");

        doNothing().when(personService).addPerson(newPerson);

        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPerson)))
            .andExpect(status().isOk());
    }

    @Test
    public void getFirstNameByPersonIdTest() throws Exception {
        final Long personId = 1L;
        final String expectedFirstName = "John";

        given(personService.getFirstName(personId)).willReturn(expectedFirstName);

        mockMvc.perform(get("/person/{id}/first-name", personId))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedFirstName));
    }

    @Test
    public void getLastNameByPersonIdTest() throws Exception {
        final Long personId = 1L;
        final String expectedLastName = "Doe";

        given(personService.getLastName(personId)).willReturn(expectedLastName);

        mockMvc.perform(get("/person/{id}/last-name", personId))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedLastName));
    }

    @Test
    public void updateLastNameByPersonIdTest() throws Exception {
        final Long personId = 1L;
        final String newLastName = "UpdatedLastName";

        doNothing().when(personService).setLastName(personId, newLastName);

        mockMvc.perform(put("/person/{id}/last-name/{last-name}", personId, newLastName))
            .andExpect(status().isOk());
    }

    @Test
    public void addPersonWithDetailsTest() throws Exception {
        String firstName = "Charlie";
        String lastName = "Brown";
        String email = "charlie.brown@example.com";
        String iban = "ES79 2100 0813 6101 2345 6789";
        String bic = "CAIXESBBXXX";

        doNothing().when(personService).addPerson(firstName, lastName, email, iban, bic);

        mockMvc.perform(post("/person/{first-name}/{last-name}/{email}/{iban}/{bic}",
                firstName, lastName, email, iban, bic))
            .andExpect(status().isOk());
    }

    @Test
    public void deletePersonTest() throws Exception {
        final Long personId = 1L;
        doNothing().when(personService).deletePerson(personId);

        mockMvc.perform(delete("/person/{id}", personId))
            .andExpect(status().isOk());
    }

    // Existing setup and tests...

    /**
     * Tests retrieving a person's email by their ID.
     */
    @Test
    public void getEmailByPersonIdTest() throws Exception {
        final Long personId = 1L;
        final String expectedEmail = "john.doe@example.com";

        given(personService.getEmail(personId)).willReturn(expectedEmail);

        mockMvc.perform(get("/person/{id}/email", personId))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedEmail));
    }

    /**
     * Tests updating a person's email by their ID.
     */
    @Test
    public void updateEmailByPersonIdTest() throws Exception {
        final Long personId = 1L;
        final String newEmail = "new.john.doe@example.com";

        doNothing().when(personService).setEmail(personId, newEmail);
        mockMvc.perform(put("/person/{id}/email/{email}", personId, newEmail))
            .andExpect(status().isOk());
    }

    /**
     * Tests retrieving a person's IBAN by their ID.
     */
    @Test
    public void getIbanByPersonIdTest() throws Exception {
        final Long personId = 1L;
        final String expectedIban = "NL39RABO8247360527";

        given(personService.getIban(personId)).willReturn(expectedIban);

        mockMvc.perform(get("/person/{id}/iban", personId))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedIban));
    }

    /**
     * Tests updating a person's IBAN by their ID.
     */
    @Test
    public void updateIbanByPersonIdTest() throws Exception {
        final Long personId = 1L;
        final String newIban = "DE89 3704 0044 0532 0130 00";

        doNothing().when(personService).setIban(personId, newIban);

        mockMvc.perform(put("/person/{id}/iban/{iban}", personId, newIban))
            .andExpect(status().isOk());
    }

    /**
     * Tests retrieving a person's BIC by their ID.
     */
    @Test
    public void getBicByPersonIdTest() throws Exception {
        final Long personId = 1L;
        final String expectedBic = "DEUTDEDBFRA";

        given(personService.getBic(personId)).willReturn(expectedBic);

        mockMvc.perform(get("/person/{id}/bic", personId))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedBic));
    }

    /**
     * Tests updating a person's BIC by their ID.
     */
    @Test
    public void updateBicByPersonIdTest() throws Exception {
        final Long personId = 1L;
        final String newBic = "NWBKGB22";

        doNothing().when(personService).setBic(personId, newBic);

        mockMvc.perform(put("/person/{id}/bic/{bic}", personId, newBic))
            .andExpect(status().isOk());
    }

    /**
     * Tests the handling of {@link IllegalStateException} within the controller,
     * expecting to receive a NOT_FOUND (404) response status.
     */
    @Test
    public void handleIllegalStateExceptionTest() throws Exception {
        final Long personId = 1L;

        // Setup the condition to throw an IllegalStateException
        given(personService.getPersonById(personId)).willThrow(new IllegalStateException());

        // Perform a request that would trigger the IllegalStateException
        mockMvc.perform(get("/person/{id}", personId))
            .andExpect(status().isNotFound());
    }
}
