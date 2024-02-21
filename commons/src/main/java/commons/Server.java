package commons;

import java.util.ArrayList;
import java.util.Objects;


/**
 * The server class
 * A server is a list containing Event instances. It is defined by a name and description.
 */
public class Server {
    String name;
    String description;
    ArrayList<Event> events;

    /** Creates a Server instance
     * @param name The name of the server
     * @param description A description of the server
     */
    public Server(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /** Adds an event to the server
     * @param event The event that should be added to the server
     */
    public void addEvent(Event event) {
        // TODO: add event
    }

    /** Checks if the Object that is provided is equal to this Server object
     * @param o The Object that has to be compared to this Server Object
     * @return true if they are equal, false when they are not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return Objects.equals(name, server.name) && Objects.equals(description, server.description) && Objects.equals(events, server.events);
    }

    /** Provides a hash for the current Object
     * @return the hash of this Object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, description, events);
    }
}
