package server.service;

import commons.Event;
import commons.Expense;
import commons.Payment;
import commons.Person;
import commons.Tag;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
        if (eventRepository.existsById(id)) {
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
    public Event createEvent(Event event) throws IllegalStateException {
        if (event.getId() == null || !eventRepository.existsById(event.getId())) {
            return eventRepository.save(event);
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


}
