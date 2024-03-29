package server.database;

import commons.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository of the tag entity.
 */
public interface TagRepository extends JpaRepository<Tag, String> {
    Optional<Tag> findTagByName(String name);
}
