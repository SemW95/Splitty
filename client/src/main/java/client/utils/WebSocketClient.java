package client.utils;

import java.io.IOException;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * A websocket client.
 */
public class WebSocketClient {
    private WebSocketSession session;
    private String url;
    private Runnable onUpdate;

    /**
     * Creates a websocket client.
     *
     * @param url      the websocket server address
     * @param onUpdate this is run when an 'update' message is received
     */
    public WebSocketClient(String url, Runnable onUpdate) {
        this.url = url;
        this.onUpdate = onUpdate;
        connect();
    }

    /**
     * Tries to [re]connect to a websocket server.
     *
     * @return whether connection was successful
     */
    public boolean connect() {
        if (session != null) {
            // shouldn't be able to connect to a server if there already is a session
            return false;
        }
        var handler = new MyWebSocketHandler();
        try {
            session = new StandardWebSocketClient().execute(handler, url).get();
            return session != null;
        } catch (Exception e) {
            System.err.println("Could not connect to websocket server: " + e);
            return false;
        }
    }

    /**
     * Tries to disconnect.
     *
     * @return whether the connection was closed successfully
     */
    public boolean disconnnect() {
        if (session == null) {
            return false;
        }
        try {
            session.close();
            return true;
        } catch (IOException e) {
            System.err.println("Could not close the websocket connection: " + e);
            return false;
        }
    }

    private class MyWebSocketHandler extends AbstractWebSocketHandler {
        @Override
        public void afterConnectionEstablished(WebSocketSession newSession) throws Exception {
            System.out.println("Websocket connection established");
            // Try to run the onUpdate because something could have changed while the socket
            // was offline
            if (onUpdate != null) {
                onUpdate.run();
            }
        }

        @Override
        public void afterConnectionClosed(WebSocketSession closeSession, CloseStatus status) {
            System.out.println("Websocket connection was closed. Status: " + status);
            session = null;
        }

        @Override
        public void handleTextMessage(WebSocketSession currentSession, TextMessage message) {
            String payload = message.getPayload();
            if ("update".equals(payload)) {
                if (onUpdate != null) {
                    onUpdate.run();
                }
            }
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setOnUpdate(Runnable onUpdate) {
        this.onUpdate = onUpdate;
    }
}
