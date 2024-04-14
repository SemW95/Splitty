package server.api;

import commons.Currency;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.service.CurrencyService;

/**
 * Controller for Currency [CONT -> SERV -> REPO].
 */
@RestController
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(path = "/currency")
    public List<Currency> getAllCurrency() {
        return currencyService.getAllCurrency();
    }

    /**
     * Adds a Currency object to the database,
     * throws exception if person already exists.
     *
     * @param currency that is to be added
     */
    @PostMapping(path = "/currency")
    public Currency createCurrency(@RequestBody Currency currency) {
        return currencyService.createCurrency(currency);
    }

    /**
     * Gets the latest conversion rate between two currencies.
     * Uses the <a href="https://www.frankfurter.app/docs/">Frankfurter</a> api.
     *
     * @param otherCurrency The Currency method that it should be converted to.
     * @return conversion rate
     */
    @GetMapping("/currency/{id}/rate/{otherCurrency}")
    public BigDecimal getConversionRate(
        @PathVariable String id,
        @PathVariable Currency otherCurrency) throws IOException, URISyntaxException {
        return currencyService.getConversionRate(id, otherCurrency);
    }

    /**
     * Gets the conversion rate between two currencies on some specific date.
     * Uses the <a href="https://www.frankfurter.app/docs/">Frankfurter</a> api.
     *
     * @param otherCurrency The Currency method that it should be converted to.
     * @param date          A date which is of format "YYYY-MM-DD".
     * @return conversion rate
     */
    @GetMapping("/currency/{id}/rate/{otherCurrency}/date/{date}")
    public BigDecimal getConversionRate(
        @PathVariable String id,
        @PathVariable Currency otherCurrency,
        @PathVariable String date)
        throws IOException, URISyntaxException {
        return currencyService.getConversionRate(id, otherCurrency, date);
    }

    @GetMapping(path = "/currency/{id}/name")
    public String getName(@PathVariable String id) {
        return currencyService.getName(id);
    }

    @GetMapping(path = "/currency/{id}/code")
    public String getCode(@PathVariable String id) {
        return currencyService.getCode(id);
    }

    @GetMapping(path = "/currency/{id}/symbol")
    public char getSymbol(@PathVariable String id) {
        return currencyService.getSymbol(id);

    }

    /** Sets the name for a Currency.
     *
     * @param id The id of the Currency
     * @param name The new name for the Currency
     */
    @PutMapping(path = "/currency/{id}/name/{name}")
    public void setName(
        @PathVariable String id,
        @PathVariable String name) {
        currencyService.setName(id, name);
    }

    /** Sets the code for a Currency.
     *
     * @param id The id of the Currency
     * @param code The new code for the Currency
     */
    @PutMapping(path = "/currency/{id}/code/{code}")
    public void setCode(
        @PathVariable String id,
        @PathVariable String code) {
        currencyService.setCode(id, code);

    }

    /** Sets the symbol for a Currency.
     *
     * @param id The id of the Currency
     * @param symbol The new symbol for the Currency
     */
    @PutMapping(path = "/currency/{id}/symbol/{symbol}")
    public void setSymbol(
        @PathVariable String id,
        @PathVariable char symbol) {
        currencyService.setSymbol(id, symbol);
    }

    /**
     * Deletes person with certain id,
     * throws exception if person does not exist.
     *
     * @param id that is to be deleted
     * @throws IllegalStateException When the person with this id doesn't exist
     */
    @DeleteMapping(path = "/currency/{id}")
    public void deleteCurrency(String id) throws IllegalStateException {
        currencyService.deleteCurrency(id);
    }

    /**
     * Updates a Person in the database.
     *
     * @param person The Person Object with the updated data
     * @throws IllegalStateException When there isn't a Person with this id in the database
     */
    @PutMapping(path = "/currency")
    public void updateCurrency(Currency person) throws IllegalStateException {
        currencyService.updateCurrency(person);
    }


}
