package server.api;

import commons.Event;
import commons.Expense;
import commons.Payment;
import commons.Person;
import commons.Tag;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @ResponseBody
    public String createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    /**
     * Creates a new Event without dates.
     *
     * @param title       The title of the Event.
     * @param description The description of the Event.
     * @return The saved Event's id
     */
    @PostMapping(path = "/event/{title}/{desc}")
    @ResponseBody
    public String createEvent(
        @PathVariable(name = "title") String title,
        @PathVariable(name = "desc") String description) {
        return eventService.createEvent(title, description);
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
    @PostMapping(path = "/event/{title}/{desc}/{startDate}/{endDate}")
    @ResponseBody
    public String createEvent(
        @PathVariable(name = "title") String title,
        @PathVariable(name = "desc") String description,
        @PathVariable(name = "startDate") LocalDate startDate,
        @PathVariable(name = "endDate") LocalDate endDate) {
        return eventService.createEvent(title, description, startDate, endDate);
    }

    /**
     * Creates a new Event with predefined Tags and without dates.
     *
     * @param title       The title of the Event.
     * @param description The description of the Event.
     * @param tags        The Tags of the Event.
     * @return The saved Event's id
     */
    @PostMapping(path = "/event/{title}/{desc}/{tags}")
    @ResponseBody
    public String createEventWithTags(
        @PathVariable(name = "title") String title,
        @PathVariable(name = "desc") String description,
        @PathVariable(name = "tags") ArrayList<Tag> tags) {
        return eventService.createEventWithTags(title, description, tags);
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
    @PostMapping(path = "/event/{title}/{desc}/{tags}/{startDate}/{endDate}")
    @ResponseBody
    public String createEventWithTagsAndDates(
        @PathVariable(name = "title") String title,
        @PathVariable(name = "desc") String description,
        @PathVariable(name = "tags") ArrayList<Tag> tags,
        @PathVariable(name = "startDate") LocalDate startDate,
        @PathVariable(name = "endDate") LocalDate endDate) {
        return eventService.createEventWithTagsAndDates(
            title, description, tags, startDate, endDate);
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
    @PostMapping(path = "/event"
            + "/{title}"
            + "/{desc}"
            + "/{people}"
            + "/{tags}"
            + "/{expenses}"
            + "/{payments}"
            + "/{startDate}"
            + "/{endDate}"
            + "/{lastModified}")
    @ResponseBody
    public String createEventFromImport(
        @PathVariable(name = "title") String title,
        @PathVariable(name = "desc") String description,
        @PathVariable(name = "people") List<Person> people,
        @PathVariable(name = "tags") List<Tag> tags,
        @PathVariable(name = "expenses") List<Expense> expenses,
        @PathVariable(name = "payments") List<Payment> payments,
        @PathVariable(name = "startDate") LocalDate startDate,
        @PathVariable(name = "endDate") LocalDate endDate,
        @PathVariable(name = "lastModified") Instant lastModifiedDateTime
    ) {
        return eventService.createEventFromImport(
            title,
            description,
            people,
            tags,
            expenses,
            payments,
            startDate,
            endDate,
            lastModifiedDateTime);
    }

    @PutMapping(path = "/event")
    public void updateEvent(@RequestBody Event event) {
        eventService.updateEvent(event);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException exception) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
