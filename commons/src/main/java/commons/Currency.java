package commons;

import java.util.Objects;

/**
 * Currency class.
 */
public class Currency {
    String name;
    String code;

    public Currency(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**
     * Gets the conversion rate between two currencies.
     *
     * @param otherCurrency The Currency method that it should be converted to.
     * @return conversion rate
     */
    public double getConversionRate(Currency otherCurrency) {
        // TODO: check if currency.equals(otherCurrency)
        // TODO: create GET request to API from code to otherCurrency.getCode()
        // TODO: should return the conversion rate between currencies.
        // Use api.frankfurter.app (https://www.frankfurter.app/docs/)
        // example: https://api.frankfurter.app/latest?from=GBP&to=USD
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Currency currency = (Currency) o;
        return Objects.equals(name, currency.name) && Objects.equals(code, currency.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
