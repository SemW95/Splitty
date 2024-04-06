package server.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
public class WebSocketController extends AbstractWebSocketHandler {
    private List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session,
                                  TextMessage message) {
        System.out.println(message.toString());
    }

    public void sendUpdateMessage() {
        for (var session : sessions) {
            try {
                session.sendMessage(new TextMessage("update"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
