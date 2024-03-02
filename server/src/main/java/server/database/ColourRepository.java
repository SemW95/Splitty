package server.database;

import commons.Colour;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository of the currency entity
 */
public interface ColourRepository extends JpaRepository<Colour, Long> {
}
