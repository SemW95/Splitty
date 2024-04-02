package server.api;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    /**
     * Deletes an Event based on its id.
     *
     * @param id The id of the Event that should be deleted
     * @throws IllegalStateException When there isn't an Event with this id
     */
    @DeleteMapping(path = "/event/{id}")
    public void deleteEvent(@PathVariable(name = "id") String id) {
        eventService.deleteEvent(id);
    }

    /**
     * Updates the last modified date time of an event.
     *
     * @param eventId The id of the event to be updated.
     */
    @PutMapping(path = "/event/{id}/updateLastModified")
    public void updateEventLastModified(@PathVariable(name = "id") String eventId) {
        eventService.updateEventLastModified(eventId);
    }

    /**
     * Calculates and returns the total debt sum for a person within an event.
     *
     * @param eventId The id of the event.
     * @param personId The person whose debt sum is to be calculated.
     * @return The total debt sum.
     */
    @GetMapping(path = "/event/{id}/debt/{personId}")
    public BigDecimal calculateDebtSumForPerson(
        @PathVariable(name = "id") String eventId,
        @PathVariable(name = "personId") String personId) {
        return eventService.calculateDebtSumForPerson(eventId, personId);
    }

    /**
     * Calculates what payments are needed to settle everyone's debts in an event.
     *
     * @param eventId The id of the event.
     * @return A list of payments required to settle debts.
     */
    @GetMapping(path = "/event/{id}/calculateSettlements")
    @ResponseBody
    public List<Payment> calculateSettlementsForEvent(@PathVariable(name = "id") String eventId) {
        return eventService.calculateSettlementsForEvent(eventId);
    }

    /**
     * Calculates the detailed debt information for a person within an event.
     *
     * @param eventId The id of the event.
     * @param personId The id of the person for whom the debt information is calculated.
     * @return A map of people to the amount of debt owed.
     */
    @GetMapping(path = "/event/{id}/calculatePersonDebt/{personId}")
    public Map<Person, BigDecimal> calculateDebtForPerson(
        @PathVariable(name = "id") String eventId,
        @PathVariable(name = "personId") String personId) {
        return eventService.calculateDebtForPerson(eventId, personId);
    }

    /**
     * Calculates the total amount spent for an event.
     *
     * @param eventId The id of the event.
     * @return The total amount spent.
     */
    @GetMapping(path = "/event/{id}/calculateTotalAmountSpent")
    @ResponseBody
    public BigDecimal calculateTotalAmountSpentForEvent(@PathVariable(name = "id") String eventId) {
        return eventService.calculateTotalAmountSpentForEvent(eventId);
    }

    @GetMapping(path = "/event/{id}/code")
    @ResponseBody
    public String getEventCode(@PathVariable(name = "id") String eventId) {
        return eventService.getEventCode(eventId);
    }

    @GetMapping(path = "/event/{id}/title")
    @ResponseBody
    public String getEventTitle(@PathVariable(name = "id") String eventId) {
        return eventService.getEventTitle(eventId);
    }

    @GetMapping(path = "/event/{id}/description")
    @ResponseBody
    public String getEventDescription(@PathVariable(name = "id") String eventId) {
        return eventService.getEventDescription(eventId);
    }

    @GetMapping(path = "/event/{id}/people")
    @ResponseBody
    public List<Person> getEventPeople(@PathVariable(name = "id") String eventId) {
        return eventService.getEventPeople(eventId);
    }

    @GetMapping(path = "/event/{id}/tags")
    @ResponseBody
    public List<Tag> getEventTags(@PathVariable(name = "id") String eventId) {
        return eventService.getEventTags(eventId);
    }

    @GetMapping(path = "/event/{id}/expenses")
    @ResponseBody
    public List<Expense> getEventExpenses(@PathVariable(name = "id") String eventId) {
        return eventService.getEventExpenses(eventId);
    }

    @GetMapping(path = "/event/{id}/payments")
    @ResponseBody
    public List<Payment> getEventPayments(@PathVariable(name = "id") String eventId) {
        return eventService.getEventPayments(eventId);
    }

    @GetMapping(path = "/event/{id}/start-date")
    @ResponseBody
    public LocalDate getEventStartDate(@PathVariable(name = "id") String eventId) {
        return eventService.getEventStartDate(eventId);
    }

    @GetMapping(path = "/event/{id}/end-date")
    @ResponseBody
    public LocalDate getEventEndDate(@PathVariable(name = "id") String eventId) {
        return eventService.getEventEndDate(eventId);
    }

    @GetMapping(path = "/event/{id}/last-modified")
    @ResponseBody
    public Instant getEventLastModifiedDateTime(@PathVariable(name = "id") String eventId) {
        return eventService.getEventLastModifiedDateTime(eventId);
    }

    /**
     * Sets the code of an event and saves the update.
     *
     * @param eventId The ID of the event to update.
     * @param code    The new code to set for the event.
     */
    @PutMapping(path = "/event/{id}/code/{set}")
    public void setEventCode(
        @PathVariable(name = "id") String eventId,
        @PathVariable(name = "set") String code) {
        eventService.setEventCode(eventId, code);
    }

    /**
     * Sets the title of an event and saves the update.
     *
     * @param eventId The ID of the event to update.
     * @param title   The new title to set for the event.
     */
    @PutMapping(path = "/event/{id}/title/{set}")
    public void setEventTitle(
        @PathVariable(name = "id") String eventId,
        @PathVariable(name = "set") String title) {
        eventService.setEventTitle(eventId, title);
    }

    /**
     * Sets the description of an event and saves the update.
     *
     * @param eventId     The ID of the event to update.
     * @param description The new description to set for the event.
     */
    @PutMapping(path = "/event/{id}/description/{set}")
    public void setEventDescription(
        @PathVariable(name = "id") String eventId,
        @PathVariable(name = "set") String description) {
        eventService.setEventDescription(eventId, description);
    }

    /**
     * Sets the list of people associated with an event and saves the update.
     *
     * @param eventId The ID of the event to update.
     * @param people  The new list of people to associate with the event.
     */
    @PutMapping(path = "/event/{id}/people")
    public void setEventPeople(
        @PathVariable(name = "id") String eventId,
        @RequestBody List<Person> people) {
        eventService.setEventPeople(eventId, people);
    }

    /**
     * Sets the tags of an event and saves the update.
     *
     * @param eventId The ID of the event to update.
     * @param tags    The new tags to set for the event.
     */
    @PutMapping(path = "/event/{id}/tags")
    public void setEventTags(
        @PathVariable(name = "id") String eventId,
        @RequestBody List<Tag> tags) {
        eventService.setEventTags(eventId, tags);
    }

    /**
     * Sets the expenses of an event and saves the update.
     *
     * @param eventId  The ID of the event to update.
     * @param expenses The new list of expenses to set for the event.
     */
    @PutMapping(path = "/event/{id}/expenses")
    public void setEventExpenses(
        @PathVariable(name = "id") String eventId,
        @RequestBody List<Expense> expenses) {
        eventService.setEventExpenses(eventId, expenses);
    }

    /**
     * Sets the payments of an event and saves the update.
     *
     * @param eventId  The ID of the event to update.
     * @param payments The new list of payments to set for the event.
     */
    @PutMapping(path = "/event/{id}/payments")
    public void setEventPayments(
        @PathVariable(name = "id") String eventId,
        @RequestBody List<Payment> payments) {
        eventService.setEventPayments(eventId, payments);
    }

    /**
     * Sets the start date of an event and saves the update.
     *
     * @param eventId   The ID of the event to update.
     * @param startDate The new start date to set for the event.
     */
    @PutMapping(path = "/event/{id}/start-date")
    public void setEventStartDate(
        @PathVariable(name = "id") String eventId,
        @RequestBody LocalDate startDate) {
        eventService.setEventStartDate(eventId, startDate);
    }

    /**
     * Sets the end date of an event and saves the update.
     *
     * @param eventId The ID of the event to update.
     * @param endDate The new end date to set for the event.
     */
    @PutMapping(path = "/event/{id}/end-date")
    public void setEventEndDate(
        @PathVariable(name = "id") String eventId,
        @RequestBody LocalDate endDate) {
        eventService.setEventEndDate(eventId, endDate);
    }

    /**
     * Sets the last modified date and time of an event and saves the update.
     *
     * @param eventId              The ID of the event to update.
     * @param lastModifiedDateTime The new last modified date and time to set for the event.
     */
    @PutMapping(path = "/event/{id}/last-modified")
    public void setEventLastModifiedDateTime(
        @PathVariable(name = "id") String eventId,
        @RequestBody Instant lastModifiedDateTime) {
        eventService.setEventLastModifiedDateTime(eventId, lastModifiedDateTime);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException exception) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
