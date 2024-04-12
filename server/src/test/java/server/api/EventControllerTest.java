package server.api;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Event;
import commons.Expense;
import commons.Payment;
import commons.Person;
import commons.Tag;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import server.service.EventService;

/**
 * The tests for the ExpenseController.
 */
@WebMvcTest(EventController.class)
@ActiveProfiles("test")
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new EventController(eventService)).build();
    }

    // @Test
    // public void getAllEventsTest() throws Exception {
    //     List<Event> mockEvents = Arrays.asList(
    //         new Event("Event Title 1", "Description 1"),
    //         new Event("Event Title 2", "Description 2")
    //     );
    //     when(eventService.getAllEvent()).thenReturn(mockEvents);
    //
    //     mockMvc.perform(get("/event"))
    //         .andExpect(status().isOk())
    //         .andExpect(jsonPath("$", hasSize(2))) // Correctly uses ResultMatcher
    //         .andExpect(jsonPath("$[0].title", is("Event Title 1")))
    //         .andExpect(jsonPath("$[1].title", is("Event Title 2")));
    //
    //     verify(eventService, times(1)).getAllEvent();
    // }

    @Test
    public void getEventByCodeTest() throws Exception {
        Event mockEvent = new Event("Event by Code", "This event is fetched by its code");
        mockEvent.setCode("CODE123");
        when(eventService.getEventByCode("CODE123")).thenReturn(mockEvent);

        mockMvc.perform(get("/event/code/{code}", "CODE123"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title", is("Event by Code")))
            .andExpect(jsonPath("$.code", is("CODE123")));

        verify(eventService).getEventByCode("CODE123");
    }

    @Test
    void getEventById() throws Exception {
        Event mockEvent = new Event("Event Title", "Event Description");
        String eventId = "1";
        when(eventService.getEventById(eventId)).thenReturn(mockEvent);

        mockMvc.perform(get("/event/id/{id}", eventId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title", is("Event Title")))
            .andExpect(jsonPath("$.description", is("Event Description")));

        verify(eventService).getEventById(eventId);
    }

    @Test
    void createEvent() throws Exception {
        Event inputEvent = new Event("Event Title", "Event Description");
        String eventId = "1";
        when(eventService.createEvent(any(Event.class))).thenReturn(eventId);

        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputEvent)))
            .andExpect(status().isOk())
            .andExpect(content().string(eventId));

        verify(eventService).createEvent(any(Event.class));
    }

    @Test
    void testCreateEvent() throws Exception {
        String title = "Event Title";
        String description = "Event Description";
        String eventId = "1";
        when(eventService.createEvent(title, description)).thenReturn(eventId);

        mockMvc.perform(post("/event/{title}/{desc}", title, description))
            .andExpect(status().isOk())
            .andExpect(content().string(eventId));

        verify(eventService).createEvent(title, description);
    }

    @Test
    void testCreateEvent1() throws Exception {
        String title = "Event Title";
        String description = "Event Description";
        LocalDate startDate = LocalDate.of(2023, 4, 1);
        LocalDate endDate = LocalDate.of(2023, 4, 15);
        String eventId = "1";
        when(eventService.createEvent(title, description, startDate, endDate)).thenReturn(eventId);

        mockMvc.perform(
                post("/event/{title}/{desc}/{startDate}/{endDate}", title, description, startDate,
                    endDate))
            .andExpect(status().isOk())
            .andExpect(content().string(eventId));

        verify(eventService).createEvent(title, description, startDate, endDate);
    }

    @Test
    void createEventWithTags() throws Exception {
        String title = "Event Title";
        String description = "Event Description";
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Tag1", 50, 100, 150));
        String eventId = "1";

        when(eventService.createEventWithTags(eq(title), eq(description),
            any(ArrayList.class))).thenReturn(eventId);

        mockMvc.perform(post("/event/{title}/{desc}/tags", title, description)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tags)))
            .andExpect(status().isOk())
            .andExpect(content().string(eventId));

        verify(eventService).createEventWithTags(eq(title), eq(description), any(ArrayList.class));
    }

    @Test
    void createEventWithTagsAndDates() throws Exception {
        String title = "Event Title";
        String description = "Event Description";
        LocalDate startDate = LocalDate.of(2023, 4, 1);
        LocalDate endDate = LocalDate.of(2023, 4, 15);
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Tag1", 50, 100, 150));
        String eventId = "1";

        when(eventService.createEventWithTagsAndDates(
            eq(title),
            eq(description),
            any(ArrayList.class),
            eq(startDate),
            eq(endDate))
        ).thenReturn(eventId);

        mockMvc.perform(post(
                    "/event/{title}/{desc}/tags/{startDate}/{endDate}",
                    title,
                    description,
                    startDate,
                    endDate
                )
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(tags))
            )
            .andExpect(status().isOk())
            .andExpect(content().string(eventId));

        verify(eventService).createEventWithTagsAndDates(eq(title), eq(description),
            any(ArrayList.class), eq(startDate), eq(endDate));
    }

    @Test
    void updateEvent() throws Exception {
        Event eventToUpdate =
            new Event("Event Title", "Event Description", new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), LocalDate.now(), LocalDate.now().plusDays(1),
                Instant.now());
        // Assuming this is a valid representation of your Event object for an update operation

        mockMvc.perform(put("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventToUpdate)))
            .andExpect(status().isOk());

        verify(eventService).updateEvent(any(Event.class));
    }

    @Test
    void deleteEvent() throws Exception {
        String eventId = "1";

        mockMvc.perform(delete("/event/{id}", eventId))
            .andExpect(status().isOk());

        verify(eventService, times(1)).deleteEvent(eventId);
    }

    @Test
    void updateEventLastModified() throws Exception {
        String eventId = "1";
        Instant now = Instant.now();

        mockMvc.perform(put("/event/{id}/updateLastModified", eventId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(now)))
            .andExpect(status().isOk());

        verify(eventService).updateEventLastModified(anyString());
    }

    @Test
    void calculateDebtSumForPerson() throws Exception {
        String eventId = "1";
        String personId = "person1";
        BigDecimal expectedDebtSum = new BigDecimal("100.00");

        when(eventService.calculateDebtSumForPerson(eventId, personId)).thenReturn(expectedDebtSum);

        mockMvc.perform(get("/event/{id}/debt/{personId}", eventId, personId))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedDebtSum.toString()));

        verify(eventService).calculateDebtSumForPerson(eventId, personId);
    }

    @Test
    void calculateSettlementsForEvent() throws Exception {
        String eventId = "1";
        List<Payment> expectedPayments = new ArrayList<>();

        when(eventService.calculateSettlementsForEvent(eventId)).thenReturn(expectedPayments);

        mockMvc.perform(get("/event/{id}/calculateSettlements", eventId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));

        verify(eventService).calculateSettlementsForEvent(eventId);
    }

    @Test
    void calculateDebtForPerson() throws Exception {
        String eventId = "1";
        String personId = "person1";
        Map<Person, BigDecimal> expectedDebts = new HashMap<>();

        when(eventService.calculateDebtForPerson(eventId, personId)).thenReturn(expectedDebts);

        mockMvc.perform(get("/event/{id}/calculatePersonDebt/{personId}", eventId, personId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", isA(Map.class))); // Validate it's a map

        verify(eventService).calculateDebtForPerson(eventId, personId);
    }

    @Test
    void calculateTotalAmountSpentForEvent() throws Exception {
        String eventId = "1";
        BigDecimal expectedTotal = new BigDecimal("200.00");

        when(eventService.calculateTotalAmountSpentForEvent(eventId)).thenReturn(expectedTotal);

        mockMvc.perform(get("/event/{id}/calculateTotalAmountSpent", eventId))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedTotal.toString()));

        verify(eventService).calculateTotalAmountSpentForEvent(eventId);
    }

    @Test
    void getEventCode() throws Exception {
        String eventId = "1";
        String expectedCode = "CODE123";

        when(eventService.getEventCode(eventId)).thenReturn(expectedCode);

        mockMvc.perform(get("/event/{id}/code", eventId))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedCode));

        verify(eventService).getEventCode(eventId);
    }

    @Test
    void getEventTitle() throws Exception {
        String eventId = "1";
        String expectedTitle = "Event Title";

        when(eventService.getEventTitle(eventId)).thenReturn(expectedTitle);

        mockMvc.perform(get("/event/{id}/title", eventId))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedTitle));

        verify(eventService).getEventTitle(eventId);
    }

    @Test
    void getEventDescription() throws Exception {
        String eventId = "1";
        String expectedDescription = "Event Description";

        when(eventService.getEventDescription(eventId)).thenReturn(expectedDescription);

        mockMvc.perform(get("/event/{id}/description", eventId))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedDescription));

        verify(eventService).getEventDescription(eventId);
    }

    @Test
    void getEventPeople() throws Exception {
        String eventId = "1";
        List<Person> expectedPeople = new ArrayList<>();

        when(eventService.getEventPeople(eventId)).thenReturn(expectedPeople);

        mockMvc.perform(get("/event/{id}/people", eventId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));

        verify(eventService).getEventPeople(eventId);
    }

    @Test
    void getEventTags() throws Exception {
        String eventId = "1";
        List<Tag> expectedTags = new ArrayList<>();

        when(eventService.getEventTags(eventId)).thenReturn(expectedTags);

        mockMvc.perform(get("/event/{id}/tags", eventId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));

        verify(eventService).getEventTags(eventId);
    }

    @Test
    void getEventExpenses() throws Exception {
        String eventId = "1";
        List<Expense> expectedExpenses = new ArrayList<>();

        when(eventService.getEventExpenses(eventId)).thenReturn(expectedExpenses);

        mockMvc.perform(get("/event/{id}/expenses", eventId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));

        verify(eventService).getEventExpenses(eventId);
    }

    @Test
    void getEventPayments() throws Exception {
        String eventId = "1";
        List<Payment> expectedPayments = new ArrayList<>();

        when(eventService.getEventPayments(eventId)).thenReturn(expectedPayments);

        mockMvc.perform(get("/event/{id}/payments", eventId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));

        verify(eventService).getEventPayments(eventId);
    }

    @Test
    void getEventStartDate() throws Exception {
        String eventId = "1";
        LocalDate expectedStartDate = LocalDate.of(2024, 4, 5);

        when(eventService.getEventStartDate(eventId)).thenReturn(expectedStartDate);

        mockMvc.perform(get("/event/{id}/start-date", eventId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$",
                is(Arrays.asList(expectedStartDate.getYear(), expectedStartDate.getMonthValue(),
                    expectedStartDate.getDayOfMonth()))));

        verify(eventService).getEventStartDate(eventId);
    }

    @Test
    void getEventEndDate() throws Exception {
        String eventId = "1";
        LocalDate expectedEndDate = LocalDate.of(2024, 4, 15);

        when(eventService.getEventEndDate(eventId)).thenReturn(expectedEndDate);

        mockMvc.perform(get("/event/{id}/end-date", eventId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$",
                is(Arrays.asList(expectedEndDate.getYear(), expectedEndDate.getMonthValue(),
                    expectedEndDate.getDayOfMonth()))));

        verify(eventService).getEventEndDate(eventId);
    }

    @Test
    void getEventLastModifiedDateTime() throws Exception {
        String eventId = "1";
        Instant expectedLastModifiedDateTime = Instant.parse("2024-04-04T21:48:57Z");

        when(eventService.getEventLastModifiedDateTime(eventId)).thenReturn(
            expectedLastModifiedDateTime);

        mockMvc.perform(get("/event/{id}/last-modified", eventId))
            .andExpect(status().isOk())
            .andExpect(content().string(
                expectedLastModifiedDateTime.toEpochMilli() / 1000 + ".000000000"
            ));

        verify(eventService).getEventLastModifiedDateTime(eventId);
    }


    @Test
    void setEventCode() throws Exception {
        String eventId = "1";
        String newCode = "NEWCODE123";

        mockMvc.perform(put("/event/{id}/code/{set}", eventId, newCode))
            .andExpect(status().isOk());

        verify(eventService).setEventCode(eventId, newCode);
    }

    @Test
    void setEventTitle() throws Exception {
        String eventId = "1";
        String newTitle = "New Event Title";

        mockMvc.perform(put("/event/{id}/title/{set}", eventId, newTitle))
            .andExpect(status().isOk());

        verify(eventService).setEventTitle(eventId, newTitle);
    }

    @Test
    void setEventDescription() throws Exception {
        String eventId = "1";
        String newDescription = "New Event Description";

        mockMvc.perform(put("/event/{id}/description/{set}", eventId, newDescription))
            .andExpect(status().isOk());

        verify(eventService).setEventDescription(eventId, newDescription);
    }

    @Test
    void setEventPeople() throws Exception {
        String eventId = "1";
        List<Person> newPeople = new ArrayList<>();

        mockMvc.perform(put("/event/{id}/people", eventId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPeople)))
            .andExpect(status().isOk());

        verify(eventService).setEventPeople(eq(eventId), anyList());
    }

    @Test
    void setEventTags() throws Exception {
        String eventId = "1";
        List<Tag> newTags = new ArrayList<>();

        mockMvc.perform(put("/event/{id}/tags", eventId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTags)))
            .andExpect(status().isOk());

        verify(eventService).setEventTags(eq(eventId), anyList());
    }

    @Test
    void setEventExpenses() throws Exception {
        String eventId = "1";
        List<Expense> newExpenses = new ArrayList<>();

        mockMvc.perform(put("/event/{id}/expenses", eventId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newExpenses)))
            .andExpect(status().isOk());

        verify(eventService).setEventExpenses(eq(eventId), anyList());
    }

    @Test
    void setEventPayments() throws Exception {
        String eventId = "1";
        List<Payment> newPayments = new ArrayList<>();

        mockMvc.perform(put("/event/{id}/payments", eventId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPayments)))
            .andExpect(status().isOk());

        verify(eventService).setEventPayments(eq(eventId), anyList());
    }

    @Test
    void setEventStartDate() throws Exception {
        String eventId = "1";
        LocalDate newStartDate = LocalDate.now().plusDays(2);

        mockMvc.perform(put("/event/{id}/start-date", eventId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newStartDate)))
            .andExpect(status().isOk());

        verify(eventService).setEventStartDate(eventId, newStartDate);
    }

    @Test
    void setEventEndDate() throws Exception {
        String eventId = "1";
        LocalDate newEndDate = LocalDate.now().plusDays(10);

        mockMvc.perform(put("/event/{id}/end-date", eventId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newEndDate)))
            .andExpect(status().isOk());

        verify(eventService).setEventEndDate(eventId, newEndDate);
    }

    @Test
    void setEventLastModifiedDateTime() throws Exception {
        String eventId = "1";
        Instant newLastModifiedDateTime = Instant.now().plusSeconds(1000);

        mockMvc.perform(put("/event/{id}/last-modified", eventId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newLastModifiedDateTime)))
            .andExpect(status().isOk());

        verify(eventService).setEventLastModifiedDateTime(eventId, newLastModifiedDateTime);
    }


    @Test
    public void handleIllegalStateExceptionTest() throws Exception {
        String invalidExpenseId = "nonExistingId";
        given(eventService.getEventById(invalidExpenseId)).willThrow(
            new IllegalStateException("There is no Expense with this id")
        );

        mockMvc.perform(get("/event/id/{id}", invalidExpenseId))
            .andExpect(status().isNotFound());
    }
}