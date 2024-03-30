package server.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import commons.Tag;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import server.service.ExpenseService;

/** The tests for the ExpenseController.
 */
@WebMvcTest(ExpenseController.class)
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @Autowired
    private ObjectMapper objectMapper;

    /** The preparation for all the ExpenseControllerTest.java
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(
            new ExpenseController(expenseService)).build();
    }

    @Test
    public void setParticipantsTest() throws Exception {
        String expenseId = "1";
        List<Person> participants = Arrays.asList(
            new Person(
                "John",
                "Doe",
                "john.doe@example.com",
                "NL11RABO1729344860",
                "BANKNL2A"),
            new Person(
                "Jane",
                "Doe",
                "jane.doe@example.com",
                "NL90INGB1957558601",
                "BANKNL2B"));

        doNothing().when(expenseService).setParticipants(
            eq(expenseId),
            ArgumentMatchers.<List<Person>>any());

        mockMvc.perform(put("/expense/{id}/participants", expenseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(participants)))
            .andExpect(status().isOk());
    }

    @Test
    public void setDescriptionTest() throws Exception {
        String expenseId = "1";
        String description = "Team Dinner";

        doNothing().when(expenseService).setDescription(expenseId, description);

        mockMvc.perform(put("/expense/{id}/description", expenseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(description)))
            .andExpect(status().isOk());
    }

    @Test
    public void setReceiverTest() throws Exception {
        String expenseId = "1";
        Person receiver = new Person(
            "John",
            "Doe",
            "john.doe@example.com",
            "NL64ABNA1208552090",
            "BANKNL2A");

        doNothing().when(expenseService).setReceiver(eq(expenseId), any(Person.class));

        mockMvc.perform(put("/expense/{id}/receiver", expenseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(receiver)))
            .andExpect(status().isOk());
    }

    @Test
    public void setPaidTest() throws Exception {
        String expenseId = "1";
        BigDecimal paid = new BigDecimal("150.00");

        doNothing().when(expenseService).setPaid(expenseId, paid);

        mockMvc.perform(put("/expense/{id}/paid", expenseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paid)))
            .andExpect(status().isOk());
    }

    @Test
    public void setTagTest() throws Exception {
        String expenseId = "1";
        Tag tag = new Tag("Meal", 50, 100, 150);

        doNothing().when(expenseService).setTag(eq(expenseId), any(Tag.class));

        mockMvc.perform(put("/expense/{id}/tag", expenseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tag)))
            .andExpect(status().isOk());
    }

    @Test
    public void setPaymentDateTimeTest() throws Exception {
        String expenseId = "1";
        Instant paymentDateTime = Instant.parse("2020-12-03T10:15:30.00Z");

        doNothing().when(expenseService).setPaymentDateTime(expenseId, paymentDateTime);

        mockMvc.perform(put("/expense/{id}/paymentDateTime", expenseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentDateTime)))
            .andExpect(status().isOk());
    }

    @Test
    public void addParticipantTest() throws Exception {
        String expenseId = "1";
        Person participant = new Person("Jake",
            "Long",
            "jake.long@example.com",
            "NL91ABNA0417164300",
            "ABNANL2A");

        doNothing().when(expenseService).addParticipant(eq(expenseId), any(Person.class));

        mockMvc.perform(post("/expense/{id}/participant", expenseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(participant)))
            .andExpect(status().isOk());
    }

    @Test
    public void addParticipantsTest() throws Exception {
        String expenseId = "1";
        List<Person> participants = Arrays.asList(
            new Person("Jake",
                "Long",
                "jake.long@example.com",
                "NL23RABO2863434403",
                "ABNANL2A"),
            new Person("Sam",
                "Short",
                "sam.short@example.com",
                "NL50ABNA9096356438",
                "ABNANL2B"));

        doNothing().when(expenseService).addParticipants(
            eq(expenseId),
            ArgumentMatchers.<List<Person>>any());

        mockMvc.perform(post("/expense/{id}/addParticipants", expenseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(participants)))
            .andExpect(status().isOk());
    }

    @Test
    public void removeParticipantTest() throws Exception {
        String expenseId = "1";
        String participantId = "2";

        doNothing().when(expenseService).removeParticipant(expenseId, participantId);

        mockMvc.perform(delete(
            "/expense/{expenseId}/participants/{participantId}",
                expenseId,
                participantId))
            .andExpect(status().isOk());
    }

    @Test
    public void getShareTest() throws Exception {
        String expenseId = "1";
        BigDecimal share = new BigDecimal("75.00");

        given(expenseService.getShare(expenseId)).willReturn(share);

        mockMvc.perform(get("/expense/{id}/share", expenseId))
            .andExpect(status().isOk())
            .andExpect(content().string(share.toString()));
    }

    @Test
    public void getParticipantsTest() throws Exception {
        String expenseId = "1";
        List<Person> participants = Arrays.asList(
            new Person("Alice",
                "Wonderland",
                "alice@example.com",
                "NL38INGB3367466468",
                "ABNANL2A"),
            new Person("Bob",
                "Builder",
                "bob@example.com",
                "NL05ABNA4734538751",
                "ABNANL2B")
        );

        given(expenseService.getParticipants(expenseId)).willReturn(participants);

        mockMvc.perform(get("/expense/{id}/participants", expenseId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].firstName", is("Alice")))
            .andExpect(jsonPath("$[1].firstName", is("Bob")));
    }

    @Test
    public void getDescriptionTest() throws Exception {
        String expenseId = "1";
        String description = "Office Supplies";

        given(expenseService.getDescription(expenseId)).willReturn(description);

        mockMvc.perform(get("/expense/{id}/description", expenseId))
            .andExpect(status().isOk())
            .andExpect(content().string(description));
    }

    @Test
    public void getReceiverTest() throws Exception {
        String expenseId = "1";
        Person receiver = new Person(
            "Charlie",
            "Chaplin",
            "charlie@example.com",
            "GB29NWBK60161331926819",
            "NWBKGB2L");

        given(expenseService.getReceiver(expenseId)).willReturn(receiver);

        mockMvc.perform(get("/expense/{id}/receiver", expenseId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstName", is("Charlie")))
            .andExpect(jsonPath("$.lastName", is("Chaplin")));
    }

    @Test
    public void getPaidTest() throws Exception {
        String expenseId = "1";
        BigDecimal paid = new BigDecimal("100.00");

        given(expenseService.getPaid(expenseId)).willReturn(paid);

        mockMvc.perform(get("/expense/{id}/paid", expenseId))
            .andExpect(status().isOk())
            .andExpect(content().string(paid.toString()));
    }

    @Test
    public void getTagTest() throws Exception {
        String expenseId = "1";
        Tag tag = new Tag("Travel", 150, 100, 50);

        given(expenseService.getTag(expenseId)).willReturn(tag);

        mockMvc.perform(get("/expense/{id}/tag", expenseId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", is("Travel")));
    }

    @Test
    public void getPaymentDateTimeTest() throws Exception {
        String expenseId = "1";
        Instant paymentDateTime = Instant.parse("2021-07-20T10:42:00Z");

        given(expenseService.getPaymentDateTime(expenseId)).willReturn(paymentDateTime);

        mockMvc.perform(get("/expense/{id}/paymentDateTime", expenseId))
            .andExpect(status().isOk())
            .andExpect(content().string(
                Long.toString(
                paymentDateTime.toEpochMilli() / 1000
                ) + ".000000000"
            ));
    }

    @Test
    public void deleteExpenseTest() throws Exception {
        String expenseId = "2";

        doNothing().when(expenseService).deleteExpense(expenseId);

        mockMvc.perform(delete("/expense/{id}", expenseId))
            .andExpect(status().isOk());
    }

}
