package server.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * A websocket handler which keeps tracks of websocket sessions and is able to send text messages.
 */
@Component
public class MyWebSocketHandler extends AbstractWebSocketHandler {
    private final List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    // @Override
    // public void handleTextMessage(WebSocketSession session,
    //                               TextMessage message) {
    //     // receive any text messages
    //     System.out.println(message.toString());
    // }

    /**
     * Send the update message to all websocket clients.
     */
    public void sendUpdateMessage() {
        for (var session : sessions) {
            try {
                session.sendMessage(new TextMessage("update"));
            } catch (IOException e) {
                System.err.println("Could not send a message to a websocket client: " + e);
            }
        }
    }
}
