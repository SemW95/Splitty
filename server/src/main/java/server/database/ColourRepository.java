package server.database;

import commons.Colour;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository of the colour entity.
 */
public interface ColourRepository extends JpaRepository<Colour, Long> {
    Optional<Colour> findColourById(long id);
}
