package server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import commons.Event;
import commons.Expense;
import commons.Payment;
import commons.Person;
import commons.Tag;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.database.EventRepository;

class EventServiceTest {
    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEvent() {
        List<Event> events = new ArrayList<>();
        when(eventRepository.findAll()).thenReturn(events);
        assertEquals(events, eventService.getAllEvent());
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void getEventById() {
        String id = "1";
        Event event = new Event("Title", "Description");
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));
        assertEquals(event, eventService.getEventById(id));
        verify(eventRepository, times(1)).findById(id);
    }

    @Test
    void getEventByCode() {
        String code = "EVT123";
        Event event = new Event("Title", "Description");
        when(eventRepository.findByCode(code)).thenReturn(Optional.of(event));
        assertEquals(event, eventService.getEventByCode(code));
        verify(eventRepository, times(1)).findByCode(code);
    }

    @Test
    void deleteEvent() {
        String id = "1";
        when(eventRepository.existsById(id)).thenReturn(true); // Simulate that the event exists
        doNothing().when(eventRepository).deleteById(id);

        eventService.deleteEvent(id);

        verify(eventRepository, times(1)).existsById(id); // Verify existsById was called
        verify(eventRepository, times(1)).deleteById(id); // Verify deleteById was called
    }


    @Test
    void createEvent_Event() {
        Event event = new Event("Title", "Description");
        when(eventRepository.save(event)).thenReturn(event);
        assertEquals(event, eventService.createEvent(event));
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void createEvent_Details() {
        Event event = new Event("Title", "Description");
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        assertEquals(event.getId(), eventService.createEvent("Title", "Description"));
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void createEventWithDates() {
        Event event =
            new Event("Title", "Description", LocalDate.now(), LocalDate.now().plusDays(1));
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        assertEquals(event.getId(),
            eventService.createEvent("Title", "Description", LocalDate.now(),
                LocalDate.now().plusDays(1)));
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void createEventWithTags() {
        ArrayList<Tag> tags = new ArrayList<>();
        Event event = new Event("Title", "Description", tags);
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        assertEquals(event.getId(), eventService.createEventWithTags("Title", "Description", tags));
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void createEventWithTagsAndDates() {
        ArrayList<Tag> tags = new ArrayList<>();
        Event event =
            new Event("Title", "Description", tags, LocalDate.now(), LocalDate.now().plusDays(1));
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        assertEquals(event.getId(),
            eventService.createEventWithTagsAndDates("Title", "Description", tags, LocalDate.now(),
                LocalDate.now().plusDays(1)));
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void createEventFromImport() {
        List<Person> people = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        List<Expense> expenses = new ArrayList<>();
        List<Payment> payments = new ArrayList<>();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        Instant lastModifiedDateTime = Instant.now();
        Event event =
            new Event("Title", "Description", people, tags, expenses, payments, startDate, endDate,
                lastModifiedDateTime);
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        assertEquals(event.getId(),
            eventService.createEventFromImport("Title", "Description", people, tags, expenses,
                payments, startDate, endDate, lastModifiedDateTime));
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void updateEvent() {
        Event event = new Event("Title", "Description");
        when(eventRepository.existsById(event.getId())).thenReturn(true);
        when(eventRepository.save(event)).thenReturn(event);
        eventService.updateEvent(event);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void updateEventLastModified() {
        String id = "1";
        Event event = new Event("Title", "Description");
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        eventService.updateEventLastModified(id);
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void calculateDebtSumForPerson() {
        String eventId = "event1";
        String personId = "person1";
        BigDecimal expectedDebtSum = BigDecimal.valueOf(100);

        Event event = mock(Event.class);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(event.calculateDebtSum(personId)).thenReturn(expectedDebtSum);

        BigDecimal result = eventService.calculateDebtSumForPerson(eventId, personId);

        assertEquals(expectedDebtSum, result);
        verify(event).calculateDebtSum(personId);
    }


    @Test
    void calculateSettlementsForEvent() {
        Person person1 = new Person(
            "Receiver",
            "Last",
            "receiver@example.com",
            "NL78ABNA2490067532",
            "DNBANOKK");
        person1.setId("person1");
        Person person2 = new Person(
            "Receiver2",
            "Last2",
            "receiver2@example.com",
            "NL78ABNA2490067532",
            "DNBANOKK");
        person2.setId("person2");

        String eventId = "event1";
        List<Payment> expectedSettlements = new ArrayList<>();
        expectedSettlements.add(new Payment(person1, person2, BigDecimal.valueOf(50)));

        Event event = mock(Event.class);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(event.calculateSettlements()).thenReturn(expectedSettlements);

        List<Payment> settlements = eventService.calculateSettlementsForEvent(eventId);

        assertEquals(expectedSettlements, settlements);
        verify(event).calculateSettlements();
    }

    @Test
    void calculateDebtForPerson() {
        Person person1 = new Person(
            "Receiver",
            "Last",
            "receiver@example.com",
            "NL78ABNA2490067532",
            "DNBANOKK");
        person1.setId("person1");

        String eventId = "event1";
        String personId = "person1";
        Map<Person, BigDecimal> expectedDebt = new HashMap<>();
        expectedDebt.put(person1, BigDecimal.valueOf(50));

        Event event = mock(Event.class);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(event.calculateDebt(personId)).thenReturn(expectedDebt);

        Map<Person, BigDecimal> debt = eventService.calculateDebtForPerson(eventId, personId);
        Map<Person, BigDecimal> debt2 = eventService.calculateDebtForPerson(eventId, person1);

        assertEquals(debt, debt2);
        assertEquals(expectedDebt, debt);
        verify(event, times(2)).calculateDebt(personId);
    }


    @Test
    void calculateTotalAmountSpentForEvent() {

        Person receiver = new Person(
            "Receiver",
            "Last",
            "receiver@example.com",
            "NL78ABNA2490067532",
            "DNBANOKK");
        receiver.setId("receiverId");
        Person receiver2 = new Person(
            "Receiver2",
            "Last2",
            "receiver2@example.com",
            "NL78ABNA2490067532",
            "DNBANOKK");
        receiver2.setId("receiverId2");
        Expense testExpense =
            new Expense(receiver, BigDecimal.valueOf(100), Instant.now(), new Tag());
        testExpense.setId("testId");
        Expense testExpense2 =
            new Expense(receiver, BigDecimal.valueOf(50), Instant.now(), new Tag());
        testExpense2.setId("testId2");

        String id = "1";
        Event event = new Event("Title", "Description");
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));
        event.setExpenses(List.of(testExpense, testExpense2)); // Simplified example
        // Assuming each Expense has a predefined amount
        eventService.calculateTotalAmountSpentForEvent(id);
        // Verification can involve asserting the total amount calculated
    }

    // Tests for getters

    @Test
    void getEventCode() {
        String eventId = "1";
        Event event = new Event("Title", "Description");
        event.setCode("CODE123");
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        assertEquals("CODE123", eventService.getEventCode(eventId));
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    void getEventTitle() {
        String eventId = "1";
        Event event = new Event("Title", "Description");
        event.setTitle("Event Title");
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        assertEquals("Event Title", eventService.getEventTitle(eventId));
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    void getEventDescription() {
        String eventId = "1";
        Event event = new Event("Title", "Description");
        event.setDescription("Event Description");
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        assertEquals("Event Description", eventService.getEventDescription(eventId));
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    void getEventPeople() {
        String eventId = "1";
        List<Person> people = new ArrayList<>();
        Event event = new Event("Title", "Description");
        event.setPeople(people);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        assertEquals(people, eventService.getEventPeople(eventId));
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    void getEventTags() {
        String eventId = "1";
        List<Tag> tags = new ArrayList<>();
        Event event = new Event("Title", "Description");
        event.setTags(tags);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        assertEquals(tags, eventService.getEventTags(eventId));
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    void getEventExpenses() {
        String eventId = "1";
        Event event = new Event("Title", "Description");
        event.setExpenses(new ArrayList<>());
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        assertEquals(new ArrayList<>(), eventService.getEventExpenses(eventId));
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    void getEventPayments() {
        String eventId = "1";
        Event event = new Event("Title", "Description");
        event.setPayments(new ArrayList<>());
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        assertEquals(new ArrayList<>(), eventService.getEventPayments(eventId));
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    void getEventStartDate() {
        String eventId = "1";
        LocalDate startDate = LocalDate.now();
        Event event = new Event("Title", "Description");
        event.setStartDate(startDate);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        assertEquals(startDate, eventService.getEventStartDate(eventId));
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    void getEventEndDate() {
        String eventId = "1";
        LocalDate endDate = LocalDate.now().plusDays(1);
        Event event = new Event("Title", "Description");
        event.setEndDate(endDate);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        assertEquals(endDate, eventService.getEventEndDate(eventId));
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    void getEventLastModifiedDateTime() {
        String eventId = "1";
        Instant lastModified = Instant.now();
        Event event = new Event("Title", "Description");
        event.setLastModifiedDateTime(lastModified);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        assertEquals(lastModified, eventService.getEventLastModifiedDateTime(eventId));
        verify(eventRepository, times(1)).findById(eventId);
    }

    // Tests for setters

    @Test
    void setEventCode() {
        String eventId = "1";
        String code = "NEWCODE123";
        Event event = new Event("Title", "Description");
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        doAnswer(invocation -> {
            event.setCode(code);
            return null;
        }).when(eventRepository).save(any(Event.class));
        eventService.setEventCode(eventId, code);
        assertEquals(code, event.getCode());
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void setEventTitle() {
        String eventId = "1";
        String title = "New Title";
        Event event = new Event("Title", "Description");
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        doAnswer(invocation -> {
            event.setTitle(title);
            return null;
        }).when(eventRepository).save(any(Event.class));
        eventService.setEventTitle(eventId, title);
        assertEquals(title, event.getTitle());
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void setEventDescription() {
        String eventId = "1";
        String description = "New Description";
        Event event = new Event("Title", "Description");
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        doAnswer(invocation -> {
            event.setDescription(description);
            return null;
        }).when(eventRepository).save(any(Event.class));
        eventService.setEventDescription(eventId, description);
        assertEquals(description, event.getDescription());
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void setEventPeople() {
        String eventId = "1";
        List<Person> people = new ArrayList<>();
        Event event = new Event("Title", "Description");
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        doAnswer(invocation -> {
            event.setPeople(people);
            return null;
        }).when(eventRepository).save(any(Event.class));
        eventService.setEventPeople(eventId, people);
        assertEquals(people, event.getPeople());
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void setEventTags() {
        String eventId = "1";
        List<Tag> tags = new ArrayList<>();
        Event event = new Event("Title", "Description");
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        doAnswer(invocation -> {
            event.setTags(tags);
            return null;
        }).when(eventRepository).save(any(Event.class));
        eventService.setEventTags(eventId, tags);
        assertEquals(tags, event.getTags());
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void setEventExpenses() {
        String eventId = "1";
        List<Expense> expenses = new ArrayList<>();
        Event event = new Event("Title", "Description");
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        doAnswer(invocation -> {
            event.setExpenses(expenses);
            return null;
        }).when(eventRepository).save(any(Event.class));
        eventService.setEventExpenses(eventId, expenses);
        assertEquals(expenses, event.getExpenses());
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void setEventPayments() {
        String eventId = "1";
        List<Payment> payments = new ArrayList<>();
        Event event = new Event("Title", "Description");
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        doAnswer(invocation -> {
            event.setPayments(payments);
            return null;
        }).when(eventRepository).save(any(Event.class));
        eventService.setEventPayments(eventId, payments);
        assertEquals(payments, event.getPayments());
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void setEventStartDate() {
        String eventId = "1";
        LocalDate startDate = LocalDate.now();
        Event event = new Event("Title", "Description");
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        doAnswer(invocation -> {
            event.setStartDate(startDate);
            return null;
        }).when(eventRepository).save(any(Event.class));
        eventService.setEventStartDate(eventId, startDate);
        assertEquals(startDate, event.getStartDate());
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void setEventEndDate() {
        String eventId = "1";
        LocalDate endDate = LocalDate.now().plusDays(1);
        Event event = new Event("Title", "Description");
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        doAnswer(invocation -> {
            event.setEndDate(endDate);
            return null;
        }).when(eventRepository).save(any(Event.class));
        eventService.setEventEndDate(eventId, endDate);
        assertEquals(endDate, event.getEndDate());
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void setEventLastModifiedDateTime() {
        String eventId = "1";
        Instant lastModifiedDateTime = Instant.now();
        Event event = new Event("Title", "Description");
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        doAnswer(invocation -> {
            event.setLastModifiedDateTime(lastModifiedDateTime);
            return null;
        }).when(eventRepository).save(any(Event.class));
        eventService.setEventLastModifiedDateTime(eventId, lastModifiedDateTime);
        assertEquals(lastModifiedDateTime, event.getLastModifiedDateTime());
        verify(eventRepository).save(any(Event.class));
    }

    // Failure tests

    @Test
    void getEventByIdNotFound() {
        String id = "nonExistingId";
        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> eventService.getEventById(id));
        verify(eventRepository, times(1)).findById(id);
    }

    @Test
    void getEventByCodeNotFound() {
        String code = "nonExistingCode";
        when(eventRepository.findByCode(code)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> eventService.getEventByCode(code));
        verify(eventRepository, times(1)).findByCode(code);
    }

    @Test
    void deleteEventNotFound() {
        String id = "nonExistingId-asdhnteoi";
        doThrow(new IllegalStateException("There isn't an Event with this id"))
            .when(eventRepository).deleteById(id);

        assertThrows(IllegalStateException.class, () -> eventService.deleteEvent(id));
    }

    @Test
    void createEventWithExistingId() throws Exception {
        Event event = new Event("Title", "Description");
        Field field = Event.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(event, "existingId");

        when(eventRepository.existsById("existingId")).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> eventService.createEvent(event));

        verify(eventRepository, times(1)).existsById("existingId");
        verify(eventRepository, never()).save(event);
    }

    @Test
    void updateEventNotFound() {
        Event event = new Event("Title", "Description");
        when(eventRepository.existsById(event.getId())).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> eventService.updateEvent(event));
        verify(eventRepository, times(1)).existsById(event.getId());
    }
}