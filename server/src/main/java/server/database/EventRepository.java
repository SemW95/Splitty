package server.database;

import commons.Event;
import commons.Person;
import commons.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * The repository interface of the Event class.
 */
public interface EventRepository extends JpaRepository<Event, Long> {

    Event findById(long id);

    List<Event> findByTitle(String title);

    List<Event> findByPeople(ArrayList<Person> people);

    List<Event> findByTags(ArrayList<Tag> tags);

    List<Event> findByCreationDate(Instant creationDate);
}
