package server.database;

import commons.Event;
import commons.Tag;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository interface of the Event class.
 */
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByTitleContainingIgnoreCase(String title);

    List<Event> findByTagsIn(List<Tag> tags);

    List<Event> findByCreationDate(Instant creationDate);
}
