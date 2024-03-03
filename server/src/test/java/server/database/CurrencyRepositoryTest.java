package server.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import commons.Currency;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CurrencyRepositoryTest {
    @Autowired
    private CurrencyRepository currencyRepository;

    private Currency testCurrency;


    @BeforeEach
    void setUp() {
        // Initialize test data before each test method
        testCurrency = new Currency("SpecialMoney", "SUS", '$');
        currencyRepository.save(testCurrency);
    }

    @AfterEach
    void tearDown() {
        // Release test data after each test method
        currencyRepository.delete(testCurrency);
    }

    @Test
    void testFindById() {
        Currency c = currencyRepository.findById(testCurrency.getId()).orElse(null);
        assertNotNull(c);

        assertEquals(testCurrency.getName(), c.getName());
        assertEquals(testCurrency.getCode(), c.getCode());
        assertEquals(testCurrency.getSymbol(), c.getSymbol());
    }

    @Test
    void testFindByName() {
        Currency c = currencyRepository.findByName(testCurrency.getName()).orElse(null);
        assertNotNull(c);

        assertEquals(testCurrency.getName(), c.getName());
        assertEquals(testCurrency.getCode(), c.getCode());
        assertEquals(testCurrency.getSymbol(), c.getSymbol());
    }

    @Test
    void testFindByCode() {
        Currency c = currencyRepository.findByCode(testCurrency.getCode()).orElse(null);
        assertNotNull(c);

        assertEquals(testCurrency.getName(), c.getName());
        assertEquals(testCurrency.getCode(), c.getCode());
        assertEquals(testCurrency.getSymbol(), c.getSymbol());
    }
}
