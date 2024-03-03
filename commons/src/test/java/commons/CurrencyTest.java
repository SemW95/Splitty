package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

// TODO: https://gitlab.ewi.tudelft.nl/cse1105/2023-2024/teams/oopp-team-25/-/issues/15
class CurrencyTest {
    @Test
    void testGetConversionRate() {
        Currency euro = new Currency("Euro", "EUR", '€');
        Currency dollar = new Currency("Dollar", "USD", '$');
        Currency swiss = new Currency("Swiss franc", "CHF", 'F');

        BigDecimal eurToUsd = euro.getConversionRate(dollar, "2024-01-01");
        assertEquals(new BigDecimal("1.105"), eurToUsd);

        BigDecimal usdToSwiss = dollar.getConversionRate(swiss, "2024-01-01");
        assertEquals(new BigDecimal("0.83801"), usdToSwiss);
    }
}