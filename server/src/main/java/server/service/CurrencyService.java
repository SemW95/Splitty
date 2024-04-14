package server.service;

import commons.Currency;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.CurrencyRepository;

/**
 * Service for Currency. [CONT -> SERV -> REPO]
 */
@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> getAllCurrency() {
        return currencyRepository.findAll();
    }

    /**
     * Adds a Currency object to the database,
     * throws exception if person already exists.
     *
     * @param currency that is to be added
     */
    public void createCurrency(Currency currency) {
        if (currency.getId() == null || !currencyRepository.existsById(currency.getId())) {
            currencyRepository.save(currency);
            return;
        }

        throw new IllegalStateException(
            "There already is a Currency with this id"
        );
    }

    public boolean currencyExist(String id) {
        return currencyRepository.existsById(id);
    }

    /**
     * Gets a Currency by its id.
     *
     * @param id The id of the Currency
     * @return The requested Currency
     * @throws IllegalStateException When there doesn't exist a Currency with that id
     */
    public Currency getCurrencyById(String id) throws IllegalStateException {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);

        if (optionalCurrency.isEmpty()) {
            throw new IllegalStateException("Currency not found");
        }

        return optionalCurrency.get();
    }

    /**
     * Gets the latest conversion rate between two currencies.
     * Uses the <a href="https://www.frankfurter.app/docs/">Frankfurter</a> api.
     *
     * @param otherCurrency The Currency method that it should be converted to.
     * @return conversion rate
     */
    public BigDecimal getConversionRate(String id, String otherCurrency)
        throws IOException, URISyntaxException {
        return getCurrencyById(id).getConversionRate(getCurrencyById(otherCurrency));
    }

    /**
     * Gets the conversion rate between two currencies on some specific date.
     * Uses the <a href="https://www.frankfurter.app/docs/">Frankfurter</a> api.
     *
     * @param otherCurrency The Currency method that it should be converted to.
     * @param date          A date which is of format "YYYY-MM-DD".
     * @return conversion rate
     */
    public BigDecimal getConversionRate(String id, String otherCurrency, String date)
        throws IOException, URISyntaxException {
        return getCurrencyById(id).getConversionRate(getCurrencyById(otherCurrency), date);
    }

    public String getName(String id) {
        return getCurrencyById(id).getName();
    }

    public String getCode(String id) {
        return getCurrencyById(id).getCode();
    }

    public char getSymbol(String id) {
        return getCurrencyById(id).getSymbol();
    }

    /**
     * Sets the name for a Currency.
     *
     * @param id   The id of the Currency
     * @param name The new name for the Currency
     */
    public void setName(String id, String name) {
        Currency currency = getCurrencyById(id);

        currency.setName(name);
        currencyRepository.save(currency);
    }

    /**
     * Sets the code for a Currency.
     *
     * @param id   The id of the Currency
     * @param code The new code for the Currency
     */
    public void setCode(String id, String code) {
        Currency currency = getCurrencyById(id);

        currency.setCode(code);
        currencyRepository.save(currency);
    }

    /**
     * Sets the symbol for a Currency.
     *
     * @param id     The id of the Currency
     * @param symbol The new symbol for the Currency
     */
    public void setSymbol(String id, char symbol) {
        Currency currency = getCurrencyById(id);

        currency.setSymbol(symbol);
        currencyRepository.save(currency);
    }

    /**
     * Deletes person with certain id,
     * throws exception if person does not exist.
     *
     * @param id that is to be deleted
     * @throws IllegalStateException When the person with this id doesn't exist
     */
    public void deleteCurrency(String id) throws IllegalStateException {
        Optional<Currency> optionalCurrency = currencyRepository
            .findById(id);

        if (optionalCurrency.isEmpty()) {
            throw new IllegalStateException(
                "There is no Currency with this id"
            );
        }

        currencyRepository.deleteById(id);
    }

    /**
     * Updates a Currency in the database.
     *
     * @param currency The Currency Object with the updated data
     * @throws IllegalStateException When there isn't a Currency with this id in the database
     */
    public void updateCurrency(Currency currency) throws IllegalStateException {
        if (!currencyExist(currency.getId())) {
            throw new IllegalStateException(
                "There is no Currency with this id"
            );
        }
        currencyRepository.save(currency);

    }
}
