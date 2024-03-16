package server.runner;

import commons.Currency;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import server.database.CurrencyRepository;

/**
 * This a seeder class for initializing default values.
 * It is run everytime the server is started up.
 */
@Component
public class Seeder implements CommandLineRunner {
    private final CurrencyRepository currencyRepository;

    public Seeder(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void run(String... args) {
        loadCurrencies();
    }

    /**
     * Creates the default currencies.
     */
    private void loadCurrencies() {
        // Check if there are no currencies
        if (currencyRepository.count() == 0) {
            System.out.println("Adding default currencies...");

            Currency euro = new Currency("Euro", "EUR", '€');
            Currency dollar = new Currency("Dollar", "USD", '$');
            Currency swiss = new Currency("Swiss franc", "CHF", 'F');

            currencyRepository.save(euro);
            currencyRepository.save(dollar);
            currencyRepository.save(swiss);
        }
    }

    // TODO: create loader for tags
}
