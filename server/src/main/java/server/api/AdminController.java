package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import server.service.AdminService;

/**
 * Controller for Admin.
 */
@RestController
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Returns whether the password matches the current admin password.
     */
    @GetMapping(path = "/admin/validate/{password}")
    public boolean validatePassword(@PathVariable String password) {
        return adminService.validatePassword(password);
    }

    // TODO: all other routes for admin operations should also ask for the password
    // and check if it's correct.
}
