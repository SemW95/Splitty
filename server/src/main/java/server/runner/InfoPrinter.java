package server.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import server.service.AdminService;

/**
 * This a class for printing information once when the server is started.
 */
@Component
public class InfoPrinter implements CommandLineRunner {
    private final AdminService adminService;

    public InfoPrinter(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void run(String... args) {
        printAdminPassword();
    }

    /**
     * Prints a randomly generated admin password to standard output.
     */
    private void printAdminPassword() {
        String password = adminService.getAdminPassword();

        System.out.println();
        System.out.println("*******************************");
        System.out.println("* Admin password = '" + password + "' *");
        System.out.println("*******************************");
        System.out.println();
    }
}
