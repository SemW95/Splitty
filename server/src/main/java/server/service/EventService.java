package server.service;

import commons.Event;
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

    public List<Event> getAllEvent() {
        return eventRepository.findAll();
    }

    /** Finds an event by its code.
     *
     * @param id of the event
     * @return Event that was searched
     * @throws IllegalStateException When no Event with the given id is present
     */
    public Event getEventById(String id) {
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
     * @throws IllegalStateException When no Event with the given id is present
     */
    public Event getEventByCode(String code) throws IllegalStateException {
        Optional<Event> optionalEvent = eventRepository
            .findByCode(code);

        if (optionalEvent.isEmpty()) {
            throw new IllegalStateException("There is no Event with this id");
        }

        return optionalEvent.get();
    }

    /** Deletes an Event based on its id.
     *
     * @param id The id of the Event that should be deleted
     */
    public void deleteEvent(String id) {
        if (eventRepository.existsById(id)) {
            throw new IllegalStateException("There isn't an Event with this code");
        }

        eventRepository.deleteById(id);
    }

    /**
     * Creates an event. Fails if an event exists with the same id or invite code.
     *
     * @param event the event to be created.
     */
    public Event createEvent(Event event) {
        if (event.getId() == null || !eventRepository.existsById(event.getId())) {
            return eventRepository.save(event);
        }
        return null;
    }

    public void updateEvent(Event event) {
        eventRepository.save(event);
    }
}
