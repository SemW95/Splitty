package server.database;

import commons.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository of the currency entity.
 */
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
