package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// TODO: https://gitlab.ewi.tudelft.nl/cse1105/2023-2024/teams/oopp-team-25/-/issues/15
class CurrencyTest {

    private Currency euro;
    private Currency euro2;
    private Currency dollar;
    private Currency swiss;

    @BeforeEach
    void setUp() {
        euro = new Currency("Euro", "EUR", '€');
        euro2 = new Currency("Euro", "EUR", '€');
        dollar = new Currency("Dollar", "USD", '$');
        swiss = new Currency("Swiss franc", "CHF", 'F');
    }

    @Test
    void testGetConversionRate() {

        BigDecimal eurToUsd = euro.getConversionRate(dollar, "2024-01-01");
        assertEquals(new BigDecimal("1.105"), eurToUsd);

        BigDecimal usdToSwiss = dollar.getConversionRate(swiss, "2024-01-01");
        assertEquals(new BigDecimal("0.83801"), usdToSwiss);
    }

    @Test
    void testEquals() {
        assertEquals(euro, euro2);
    }

    @Test
    void testHashCode() {
        assertEquals(euro.hashCode(), euro2.hashCode(), "Hash codes should be equal");
    }

    @Test
    void getName() {
        assertEquals("Euro", euro.getName(), "Incorrect name");
    }

    @Test
    void setName() {
        String newName = "NotEuroAnymore";
        euro.setName(newName);
        assertEquals(newName, euro.getName(), "Setting name failed");
    }

    @Test
    void getCode() {
        assertEquals("EUR", euro.getCode(), "Incorrect code");
    }

    @Test
    void setCode() {
        String newCode = "NotEURAnymore";
        euro.setCode(newCode);
        assertEquals(newCode, euro.getCode(), "Setting code failed");
    }

    @Test
    void getSymbol() {
        assertEquals('€', euro.getSymbol(), "Incorrect symbol");
    }

    @Test
    void setSymbol() {
        char newSymbol = '$';
        euro.setSymbol(newSymbol);
        assertEquals('$', euro.getSymbol());
    }

}