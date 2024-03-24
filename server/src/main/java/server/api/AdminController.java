package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
     * TODO.
     *
     * @param id
     * @param password
     */
    @DeleteMapping(path = "/admin/event/{id}")
    public void deleteEvent(@PathVariable long id, @RequestParam String password) {
        if (!adminService.validatePassword(password)) {
            return;
        }
        eventService.deleteEvent(id);
    }
}
