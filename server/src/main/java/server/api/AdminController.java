package server.api;

import commons.Event;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import server.service.AdminService;
import server.service.EventService;

/**
 * Controller for Admin.
 */
@RestController
public class AdminController {
    private final AdminService adminService;
    private final EventService eventService;

    @Autowired
    public AdminController(AdminService adminService, EventService eventService) {
        this.adminService = adminService;
        this.eventService = eventService;
    }

    /**
     * Returns whether the password matches the current admin password.
     */
    @GetMapping(path = "/admin/validate/{password}")
    public boolean validatePassword(@PathVariable String password) {
        return adminService.validatePassword(password);
    }

    // All routes for admin operations should ask for the password

    /**
     * Returns all events in the database.
     *
     * @return list of all events
     */
    @GetMapping(path = "/admin/event")
    public List<Event> getAllEvents(@RequestParam String password) {
        if (!adminService.validatePassword(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return eventService.getAllEvent();
    }

    /**
     * Deletes an event with the provided id. Requires the admin password
     *
     * @param id       the id of the event to be deleted
     * @param password the admin password
     */
    @DeleteMapping(path = "/admin/event/{id}")
    public void deleteEvent(@PathVariable String id, @RequestParam String password) {
        if (!adminService.validatePassword(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        eventService.deleteEvent(id);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex) {
        // Return a ResponseEntity with the NOT_FOUND status
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
