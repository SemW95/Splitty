package server.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

/**
 * Service for Admin.
 */
@Service
public class AdminService {
    /**
     * Randomly generated password, 8 characters long.
     * It should never be sent to the client.
     */
    private final String adminPassword;

    public AdminService() {
        adminPassword = RandomStringUtils.randomAlphanumeric(8);
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    /**
     * Checks if the password matches the admin password.
     */
    public boolean validatePassword(String password) {
        return adminPassword.equals(password);
    }
}
