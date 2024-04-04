package server.service;

import commons.Event;
import commons.Expense;
import commons.Payment;
import commons.Person;
import commons.Tag;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.EventRepository;

/**
 * Service for Person. [CONT -> SERV -> REPO]
 */
@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /** Checks if an Event with the id of the given Event is already in the database.
     *
     * @param event The Event for which the id that should be checked
     * @return true if an Event with the same id is present
     */
    private boolean eventExists(Event event) {
        return eventRepository.existsById(event.getId());
    }

    public List<Event> getAllEvent() {
        return eventRepository.findAll();
    }

    /** Finds an event by its code.
     *
     * @param id of the event
     * @return Event that was searched
     * @throws IllegalStateException When no Event with the given id is present
     */
    public Event getEventById(String id) throws IllegalStateException {
        Optional<Event> optionalEvent = eventRepository
            .findById(id);

        if (optionalEvent.isEmpty()) {
            throw new IllegalStateException("There is no Event with this id");
        }

        return optionalEvent.get();
    }

    /** Finds an event by its code.
     *
     * @param code of the event
     * @return Event that was searched
     * @throws IllegalStateException When no Event with the given code is present
     */
    public Event getEventByCode(String code) throws IllegalStateException {
        Optional<Event> optionalEvent = eventRepository
            .findByCode(code);

        if (optionalEvent.isEmpty()) {
            throw new IllegalStateException("There is no Event with this code");
        }

        return optionalEvent.get();
    }

    /** Deletes an Event based on its id.
     *
     * @param id The id of the Event that should be deleted
     * @throws IllegalStateException When there isn't an Event with this id
     */
    public void deleteEvent(String id) throws IllegalStateException {
        if (!eventRepository.existsById(id)) {
            throw new IllegalStateException("There isn't an Event with this id");
        }

        eventRepository.deleteById(id);
    }

    /**
     * Creates an event. Fails if an event exists with the same id or invite code.
     *
     * @param event the event to be created.
     * @throws IllegalStateException When there already is an Event with this id
     */
    public String createEvent(Event event) throws IllegalStateException {
        if (event.getId() == null || !eventRepository.existsById(event.getId())) {
            return eventRepository.save(event).getId();
        }
        throw new IllegalStateException("There already is an Event with this id");
    }

    /**
     * Creates a new Event without dates.
     *
     * @param title       The title of the Event.
     * @param description The description of the Event.
     * @return The saved Event's id
     */
    public String createEvent(String title, String description) {
        Event event = new Event(title, description);
        return eventRepository.save(event).getId();
    }

    /**
     * Creates a new Event with specific start and end dates.
     *
     * @param title       The title of the Event.
     * @param description The description of the Event.
     * @param startDate   The start date of the Event.
     * @param endDate     The end date of the Event.
     * @return The saved Event's id
     */
    public String createEvent(
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate) {
        Event event = new Event(title, description, startDate, endDate);
        return eventRepository.save(event).getId();
    }

    /**
     * Creates a new Event with predefined Tags and without dates.
     *
     * @param title       The title of the Event.
     * @param description The description of the Event.
     * @param tags        The Tags of the Event.
     * @return The saved Event's id
     */
    public String createEventWithTags(String title, String description, ArrayList<Tag> tags) {
        Event event = new Event(title, description, tags);
        return eventRepository.save(event).getId();
    }

    /**
     * Creates a new Event with predefined Tags and specific start and end dates.
     *
     * @param title       The title of the Event.
     * @param description The description of the Event.
     * @param tags        The Tags of the Event.
     * @param startDate   The start date of the Event.
     * @param endDate     The end date of the Event.
     * @return The saved Event's id
     */
    public String createEventWithTagsAndDates(
        String title,
        String description,
        ArrayList<Tag> tags,
        LocalDate startDate,
        LocalDate endDate) {
        Event event = new Event(title, description, tags, startDate, endDate);
        return eventRepository.save(event).getId();
    }

    /**
     * The Event constructor used for imports.
     *
     * @param title                The Event title.
     * @param description          The Event description.
     * @param people               The ArrayList with all Persons in the Event.
     * @param tags                 The ArrayList with all the Tags in the Event.
     * @param expenses             The ArrayList with all the Expenses in the Event.
     * @param payments             The ArrayList with all the Payments in the Event.
     * @param startDate            The date that this Event started.
     * @param endDate              The date that this Event ended.
     * @param lastModifiedDateTime The date of when this Event was last modified.
     * @return The id of the created Event
     */
    public String createEventFromImport(
        String title,
        String description,
        List<Person> people,
        List<Tag> tags,
        List<Expense> expenses,
        List<Payment> payments,
        LocalDate startDate,
        LocalDate endDate,
        Instant lastModifiedDateTime
    ) {
        Event event = new Event(
            title,
            description,
            people,
            tags,
            expenses,
            payments,
            startDate,
            endDate,
            lastModifiedDateTime);
        return eventRepository.save(event).getId();
    }


    /** Updates an already existing Event.
     *
     * @param event The Event that should be updated
     * @throws IllegalStateException When there isn't an Event with this id
     */
    public void updateEvent(Event event) throws IllegalStateException {
        if (!eventExists(event)) {
            throw new IllegalStateException("There isn't an Event with this code");
        }

        eventRepository.save(event);
    }

    /**
     * Updates the last modified date time of an event.
     *
     * @param eventId The id of the event to be updated.
     */
    public void updateEventLastModified(String eventId) {
        Event event = getEventById(eventId);
        event.updateLastModifiedDateTime();
        eventRepository.save(event);
    }

    /**
     * Calculates and returns the total debt sum for a person within an event.
     *
     * @param eventId The id of the event.
     * @param personId The person whose debt sum is to be calculated.
     * @return The total debt sum.
     */
    public BigDecimal calculateDebtSumForPerson(String eventId, String personId) {
        Event event = getEventById(eventId);
        return event.calculateDebtSum(personId);
    }

    /**
     * Calculates what payments are needed to settle everyone's debts in an event.
     *
     * @param eventId The id of the event.
     * @return A list of payments required to settle debts.
     */
    public List<Payment> calculateSettlementsForEvent(String eventId) {
        Event event = getEventById(eventId);
        return event.calculateSettlements();
    }

    /**
     * Calculates the detailed debt information for a person within an event.
     *
     * @param eventId The id of the event.
     * @param personId The person for whom the debt information is calculated.
     * @return A map of people to the amount of debt owed.
     */
    public Map<Person, BigDecimal> calculateDebtForPerson(String eventId, String personId) {
        Event event = getEventById(eventId);
        return event.calculateDebt(personId);
    }

    /**
     * Calculates the detailed debt information for a person within an event.
     *
     * @param eventId The id of the event.
     * @param person The person for whom the debt information is calculated.
     * @return A map of people to the amount of debt owed.
     */
    public Map<Person, BigDecimal> calculateDebtForPerson(String eventId, Person person) {
        return calculateDebtForPerson(eventId, person.getId());
    }

    /**
     * Calculates the total amount spent for an event.
     *
     * @param eventId The id of the event.
     * @return The total amount spent.
     */
    public BigDecimal calculateTotalAmountSpentForEvent(String eventId) {
        Event event = getEventById(eventId);
        return event.totalAmountSpent();
    }

    public String getEventCode(String eventId) {
        return getEventById(eventId).getCode();
    }

    public String getEventTitle(String eventId) {
        return getEventById(eventId).getTitle();
    }

    public String getEventDescription(String eventId) {
        return getEventById(eventId).getDescription();
    }

    public List<Person> getEventPeople(String eventId) {
        return getEventById(eventId).getPeople();
    }

    public List<Tag> getEventTags(String eventId) {
        return getEventById(eventId).getTags();
    }

    public List<Expense> getEventExpenses(String eventId) {
        return getEventById(eventId).getExpenses();
    }

    public List<Payment> getEventPayments(String eventId) {
        return getEventById(eventId).getPayments();
    }

    public LocalDate getEventStartDate(String eventId) {
        return getEventById(eventId).getStartDate();
    }

    public LocalDate getEventEndDate(String eventId) {
        return getEventById(eventId).getEndDate();
    }

    public Instant getEventLastModifiedDateTime(String eventId) {
        return getEventById(eventId).getLastModifiedDateTime();
    }

    // Setter methods for Event fields
    /**
     * Sets the code of an event and saves the update.
     *
     * @param eventId The ID of the event to update.
     * @param code The new code to set for the event.
     */
    public void setEventCode(String eventId, String code) {
        Event event = getEventById(eventId);
        event.setCode(code);
        eventRepository.save(event);
    }

    /**
     * Sets the title of an event and saves the update.
     *
     * @param eventId The ID of the event to update.
     * @param title The new title to set for the event.
     */
    public void setEventTitle(String eventId, String title) {
        Event event = getEventById(eventId);
        event.setTitle(title);
        eventRepository.save(event);
    }

    /**
     * Sets the description of an event and saves the update.
     *
     * @param eventId The ID of the event to update.
     * @param description The new description to set for the event.
     */
    public void setEventDescription(String eventId, String description) {
        Event event = getEventById(eventId);
        event.setDescription(description);
        eventRepository.save(event);
    }

    /**
     * Sets the list of people associated with an event and saves the update.
     *
     * @param eventId The ID of the event to update.
     * @param people The new list of people to associate with the event.
     */
    public void setEventPeople(String eventId, List<Person> people) {
        Event event = getEventById(eventId);
        event.setPeople(people);
        eventRepository.save(event);
    }

    /**
     * Sets the tags of an event and saves the update.
     *
     * @param eventId The ID of the event to update.
     * @param tags The new tags to set for the event.
     */
    public void setEventTags(String eventId, List<Tag> tags) {
        Event event = getEventById(eventId);
        event.setTags(tags);
        eventRepository.save(event);
    }

    /**
     * Sets the expenses of an event and saves the update.
     *
     * @param eventId The ID of the event to update.
     * @param expenses The new list of expenses to set for the event.
     */
    public void setEventExpenses(String eventId, List<Expense> expenses) {
        Event event = getEventById(eventId);
        event.setExpenses(expenses);
        eventRepository.save(event);
    }

    /**
     * Sets the payments of an event and saves the update.
     *
     * @param eventId The ID of the event to update.
     * @param payments The new list of payments to set for the event.
     */
    public void setEventPayments(String eventId, List<Payment> payments) {
        Event event = getEventById(eventId);
        event.setPayments(payments);
        eventRepository.save(event);
    }

    /**
     * Sets the start date of an event and saves the update.
     *
     * @param eventId The ID of the event to update.
     * @param startDate The new start date to set for the event.
     */
    public void setEventStartDate(String eventId, LocalDate startDate) {
        Event event = getEventById(eventId);
        event.setStartDate(startDate);
        eventRepository.save(event);
    }

    /**
     * Sets the end date of an event and saves the update.
     *
     * @param eventId The ID of the event to update.
     * @param endDate The new end date to set for the event.
     */
    public void setEventEndDate(String eventId, LocalDate endDate) {
        Event event = getEventById(eventId);
        event.setEndDate(endDate);
        eventRepository.save(event);
    }

    /**
     * Sets the last modified date and time of an event and saves the update.
     *
     * @param eventId The ID of the event to update.
     * @param lastModifiedDateTime The new last modified date and time to set for the event.
     */
    public void setEventLastModifiedDateTime(String eventId, Instant lastModifiedDateTime) {
        Event event = getEventById(eventId);
        event.setLastModifiedDateTime(lastModifiedDateTime);
        eventRepository.save(event);
    }
}
