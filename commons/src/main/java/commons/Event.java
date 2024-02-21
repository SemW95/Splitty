package commons;

import java.util.ArrayList;
import java.util.Objects;


/** This is the data Object for an Event
 *
 */
public class Event {
    String id;
    String title;
    String description;
    ArrayList<Tag> tags;
    ArrayList<Expense> expenses;

    /** Create an Event without any Tag's
     * @param title The title of the Event
     * @param description The description of the Event
     */
    public Event(String title, String description) {
        // TODO: implement Event creation without tags
    }

    /** Create an Event with Tag's
     * @param title The title of the Event
     * @param description The description of the Event
     * @param tags The ArrayList of Tag
     */
    public Event(String title, String description, ArrayList<Tag> tags) {
        // TODO: implement Event creation with tags
    }

    /** Checks if the Object that is provided is equal to this Event object
     * @param o The Object that has to be compared to this Event Object
     * @return true if they are equal, false when they are not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(title, event.title) && Objects.equals(description, event.description) && Objects.equals(tags, event.tags) && Objects.equals(expenses, event.expenses);
    }

    /** Provides a hash for the current Object
     * @return the hash of this Object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, tags, expenses);
    }
}
