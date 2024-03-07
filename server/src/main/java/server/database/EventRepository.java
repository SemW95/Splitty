package server.database;

import commons.Event;
import commons.Tag;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * The repository interface of the Event class.
 */
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByTitleContainingIgnoreCase(String title);

    List<Event> findByTagsIn(List<Tag> tags);

    @Query("SELECT e FROM Event e WHERE e.creationDate = :creationDate")
    List<Event> findByCreationDate(@Param("creationDate") Instant creationDate);
//    List<Event> findByCreationDate(Instant creationDate);
}
