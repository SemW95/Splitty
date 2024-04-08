package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

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
    public DeferredResult<ResponseEntity<Object>> getStatus() {
        var ok = ResponseEntity.ok().build();
        long timeoutMillis = 5_000L;
        return new DeferredResult<>(timeoutMillis, ok);
    }
}
