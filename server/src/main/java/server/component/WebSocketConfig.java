package server.component;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * A websocket config component which handles all websocket servers.
 */
@Component
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final MyWebSocketHandler webSocketHandler;

    public WebSocketConfig(MyWebSocketHandler webSocketController) {
        this.webSocketHandler = webSocketController;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/websocket");
    }

    public MyWebSocketHandler getWebSocketHandler() {
        return webSocketHandler;
    }
}
