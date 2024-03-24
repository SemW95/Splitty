package server.service;

import commons.Event;
import java.util.List;
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

    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }

    public void createEvent(Event event) {
        if (eventRepository.findById(event.getId()).isEmpty()) {
            eventRepository.save(event);
        }
    }
}
