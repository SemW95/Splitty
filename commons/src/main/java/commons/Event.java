package commons;

import java.util.ArrayList;
import java.util.Objects;

public class Event {
    String id;
    String title;
    String description;
    ArrayList<Tag> tags;
    ArrayList<Expense> expenses;

    public Event(String title, String description) {
        // TODO: implement Event creation without tags
    }

    public Event(String title, String description, ArrayList<Tag> tags) {
        // TODO: implement Event creation with tags
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(title, event.title) && Objects.equals(description, event.description) && Objects.equals(tags, event.tags) && Objects.equals(expenses, event.expenses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, tags, expenses);
    }
}
