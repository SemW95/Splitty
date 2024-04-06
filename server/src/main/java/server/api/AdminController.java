package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.WebSocketConfig;
import server.service.AdminService;
import server.service.EventService;

/**
 * Controller for Admin.
 */
@RestController
public class AdminController {
    private final AdminService adminService;
    private final EventService eventService;
    private final WebSocketConfig webSocketConfig;

    @Autowired
    public AdminController(AdminService adminService, EventService eventService,
                           WebSocketConfig webSocketConfig) {
        this.adminService = adminService;
        this.eventService = eventService;
        this.webSocketConfig = webSocketConfig;
    }

    /**
     * Returns whether the password matches the current admin password.
     */
    @GetMapping(path = "/admin/validate/{password}")
    public boolean validatePassword(@PathVariable String password) {
        webSocketConfig.getWebSocketController().sendUpdateMessage();
        return adminService.validatePassword(password);
    }

    // All routes for admin operations should ask for the password

    /**
     * Deletes an event with the provided id. Requires the admin password
     *
     * @param id       the id of the event to be deleted
     * @param password the admin password
     */
    @DeleteMapping(path = "/admin/event/{id}")
    public void deleteEvent(@PathVariable String id, @RequestParam String password) {
        if (!adminService.validatePassword(password)) {
            return;
        }
        eventService.deleteEvent(id);
    }
}
