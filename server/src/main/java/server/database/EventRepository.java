package server.database;

import commons.Event;
import commons.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository interface of the Event class.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByTitleContainingIgnoreCase(String title);

    List<Event> findByTagsIn(List<Tag> tags);

    Optional<Event> findByCode(String code);

    // @Query("SELECT e FROM Event e WHERE e.creationDate = :creationDate")
    // List<Event> findByCreationDate(@Param("creationDate") Instant creationDate);
    // List<Event> findByCreationDate(Instant creationDate);

}
