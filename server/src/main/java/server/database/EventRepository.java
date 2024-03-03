package server.database;

import commons.Event;
import commons.Person;
import commons.Tag;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository interface of the Event class.
 */
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByTitleContaining(String title);

    List<Event> findByPeopleContaining(ArrayList<Person> people);

    List<Event> findByTags(ArrayList<Tag> tags);

    List<Event> findByCreationDate(Instant creationDate);
}
