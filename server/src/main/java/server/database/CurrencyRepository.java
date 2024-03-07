package server.database;

import commons.Currency;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository of the currency entity.
 */
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByName(String name);

    Optional<Currency> findByCode(String code);

    // There shouldn't be a `findBySymbol` because symbols aren't unique
}
