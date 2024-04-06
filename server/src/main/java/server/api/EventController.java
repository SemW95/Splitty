package server.api;

import commons.Event;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.service.EventService;

/**
 * Controller for Event [CONT -> SERV -> REPO].
 */
@RestController
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Returns all persons in the database,
     * if no people returns empty list.
     *
     * @return list of persons
     */
    // TODO move this to admin
    @GetMapping(path = "/event")
    public List<Event> getAllEvents() {
        return eventService.getAllEvent();
    }

    /**
     * Returns an event based on its invite code.
     *
     * @param code the invite code
     * @return the searched event
     */
    @GetMapping(path = "/event/code/{code}")
    public Event getEventByCode(@PathVariable String code) {
        return eventService.getEventByCode(code);
    }

    @GetMapping(path = "/event/id/{id}")
    public Event getEventById(@PathVariable String id) {
        return eventService.getEventById(id);
    }

    @PostMapping(path = "/event")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @PutMapping(path = "/event")
    public void updateEvent(@RequestBody Event event) {
        eventService.updateEvent(event);
    }
}
