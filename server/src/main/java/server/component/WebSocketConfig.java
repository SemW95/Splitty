package server.component;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * A websocket config component which handles all websocket servers.
 */
@EnableWebSocket
@Component
public class WebSocketConfig implements WebSocketConfigurer {
    private final MyWebSocketHandler webSocketHandler;

    public WebSocketConfig(MyWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/websocket");
    }

    public MyWebSocketHandler getWebSocketHandler() {
        return webSocketHandler;
    }
}
