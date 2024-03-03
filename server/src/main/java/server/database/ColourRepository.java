package server.database;

import commons.Colour;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository of the colour entity.
 */
public interface ColourRepository extends JpaRepository<Colour, Long> {
    Optional<Colour> findColourById(long id);
    // Do we need to add findColourByIndex and findColourByHexString?
    // I tried to add them, but they cannot be added just like id because the union of three index
    // and the HexString are not variables in Colour.

}
