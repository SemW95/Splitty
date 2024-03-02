package commons;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// TODO: https://gitlab.ewi.tudelft.nl/cse1105/2023-2024/teams/oopp-team-25/-/issues/15
class CurrencyTest {
    @Test
    void testGetConversionRate() {
        Currency euro = new Currency("Euro", "EUR", '€');
        Currency dollar = new Currency("Dollar", "USD", '$');
        Currency swiss = new Currency("Swiss franc", "CHF", 'F');

        double eurToUsd = euro.getConversionRate(dollar).doubleValue();
        // TODO: maybe think of a better way to test :)
        assertTrue(1.0 < eurToUsd && eurToUsd < 1.16);
    }
}