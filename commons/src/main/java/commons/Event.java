package commons;

import java.util.ArrayList;
import java.util.Objects;


/**
 * This is the data Object for an Event.
 */
public class Event {
    String id;
    String title;
    String description;
    ArrayList<Tag> tags;
    ArrayList<Expense> expenses;

    /**
     * Create an Event without any Tags or Expenses.
     *
     * @param title       The title of the Event
     * @param description The description of the Event
     */
    public Event(String title, String description) {
        // TODO: create id
        this.title = title;
        this.description = description;
        this.tags = new ArrayList<Tag>();
        this.expenses = new ArrayList<Expense>();
    }

    /**
     * Create an Event with Tags, but without Expenses.
     *
     * @param title       The title of the Event
     * @param description The description of the Event
     * @param tags        The ArrayList of Tag
     */
    public Event(String title, String description, ArrayList<Tag> tags) {
        // TODO: create id
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.expenses = new ArrayList<Expense>();
    }

    /**
     * Create an Event with Tags and Expenses.
     *
     * @param title       The title of the Event
     * @param description The description of the Event
     * @param tags        The ArrayList of Tag
     * @param expenses    The ArrayList of Expense
     */
    public Event(String title, String description, ArrayList<Tag> tags,
                 ArrayList<Expense> expenses) {
        // TODO: create id
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.expenses = expenses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    /**
     * Checks if the Object that is provided is equal to this Event object.
     *
     * @param o The Object that has to be compared to this Event Object
     * @return true if they are equal, false when they are not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(title, event.title)
            && Objects.equals(description, event.description) && Objects.equals(tags, event.tags)
            && Objects.equals(expenses, event.expenses);
    }

    /**
     * Provides a hash for the current Object.
     *
     * @return the hash of this Object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, tags, expenses);
    }
}
