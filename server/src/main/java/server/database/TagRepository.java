package server.database;

import commons.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository of the tag entity.
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
}
