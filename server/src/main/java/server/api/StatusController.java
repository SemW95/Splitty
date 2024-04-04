package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller for checking server status.
 */
@RestController
public class StatusController {

    /**
     * Simple request to server to see if it is working.
     *
     * @return status of server
     */
    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Good");
    }
}
