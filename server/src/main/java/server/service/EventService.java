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

    /**
     * Finds an event by its code. May return null
     *
     * @param code of the event
     * @return Event that was searched
     */
    public Event getEventByCode(String code) {
        Optional<Event> optionalEvent = eventRepository
            .findByCode(code);

        return optionalEvent.orElse(null);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    /**
     * Creates an event. Fails if an event exists with the same id or invite code.
     *
     * @param event the event to be created.
     */
    public void createEvent(Event event) {
        if (eventRepository.findById(event.getId()).isEmpty()) {
            eventRepository.save(event);
        }
    }
}
