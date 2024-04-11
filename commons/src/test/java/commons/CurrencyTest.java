package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CurrencyTest {

    private Currency euro;
    private Currency euro2;
    private Currency dollar;
    private Currency swiss;

    @BeforeEach
    void setUp() {
        euro = new Currency("Euro", "EUR", (char) 8364);
        euro2 = new Currency("Euro", "EUR", (char) 8364);
        dollar = new Currency("Dollar", "USD", '$');
        swiss = new Currency("Swiss franc", "CHF", 'F');
    }

    @Test
    void emptyConstructor() {
        Currency currency = new Currency();
        assertNotNull(currency);
        assertNull(currency.getId());
    }

    @Test
    void testGetConversionRate() {
        try {
            BigDecimal eurToUsd = euro.getConversionRate(dollar, "2024-01-01");
            assertEquals(new BigDecimal("1.105"), eurToUsd);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        try {
            BigDecimal usdToSwiss = dollar.getConversionRate(swiss, "2024-01-01");
            assertEquals(new BigDecimal("0.83801"), usdToSwiss);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetConversionLatest() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayFormatted = today.format(formatter);

        //gets conversion of today implicit
        BigDecimal eurToUsd;
        try {
            eurToUsd = dollar.getConversionRate(swiss);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        //gets conversion of today explicit
        BigDecimal eurToUsdAgain;
        try {
            eurToUsdAgain = dollar.getConversionRate(swiss, todayFormatted);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        assertEquals(eurToUsd, eurToUsdAgain);
    }

    @Test
    void getEqualConversions() throws URISyntaxException {
        try {
            BigDecimal eurToEur = euro.getConversionRate(euro, "2024-01-01");

            BigDecimal eurToEur2 = euro.getConversionRate(euro);

            BigDecimal one = new BigDecimal(1);
            assertEquals(one, eurToEur);
            assertEquals(one, eurToEur2);

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void breakUrl() {
        assertThrows(IOException.class, () -> {
            BigDecimal eurToEur = euro.getConversionRate(swiss, "kill-url-please");
        });
    }

    @Test
    void testEquals() {
        assertEquals(euro, euro2);
    }

    @Test
    void testActualEquals() {
        assertEquals(euro, euro);
    }

    @Test
    void testNullEqual() {
        assertNotEquals(euro, null);
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
        assertEquals((char) 8364, euro.getSymbol(), "Incorrect symbol");
    }

    @Test
    void setAndGetId() {
        euro.setId("1234");
        assertEquals("1234", euro.getId());
    }

    @Test
    void setSymbol() {
        char newSymbol = '$';
        euro.setSymbol(newSymbol);
        assertEquals('$', euro.getSymbol(), "Setting symbol failed");
    }

}