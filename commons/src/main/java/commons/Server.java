package commons;

import java.util.ArrayList;
import java.util.Objects;

public class Server {
    String name;
    String description;
    ArrayList<Event> events;

    public Server(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addEvent(Event event) {
        // TODO: add event
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return Objects.equals(name, server.name) && Objects.equals(description, server.description) && Objects.equals(events, server.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, events);
    }
}
