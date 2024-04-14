package server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import commons.Currency;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.database.CurrencyRepository;

class CurrencyServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCurrency() {
        List<Currency> currencies = new ArrayList<>();
        when(currencyRepository.findAll()).thenReturn(currencies);
        assertEquals(currencies, currencyService.getAllCurrency());
        verify(currencyRepository, times(1)).findAll();
    }



    @Test
    void createCurrency() {
        Currency currency = new Currency("Dollar", "USD", '$');
        when(currencyRepository.save(any(Currency.class))).thenReturn(currency);
        currencyService.createCurrency(currency);
        verify(currencyRepository, times(1)).save(currency);
    }

    @Test
    void createCurrencyExistingId() {
        Currency currency = new Currency("Dollar", "USD", '$');
        currency.setId("1");
        when(currencyRepository.existsById("1")).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> currencyService.createCurrency(currency));
        verify(currencyRepository, never()).save(currency);
    }

    @Test
    void getCurrencyByIdFound() {
        Currency currency = new Currency("Dollar", "USD", '$');
        when(currencyRepository.findById("1")).thenReturn(Optional.of(currency));
        assertEquals(currency, currencyService.getCurrencyById("1"));
        verify(currencyRepository, times(1)).findById("1");
    }

    @Test
    void getCurrencyByIdNotFound() {
        when(currencyRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> currencyService.getCurrencyById("1"));
        verify(currencyRepository, times(1)).findById("1");
    }

    @Test
    void getConversionRate() throws IOException, URISyntaxException {
        Currency currency1 = new Currency("Dollar", "USD", '$');
        Currency currency2 = new Currency("Euro", "EUR", '€');
        when(currencyRepository.findById("1")).thenReturn(Optional.of(currency1));
        assertEquals(
            currency1.getConversionRate(currency2),
            currencyService.getConversionRate("1", currency2));
        verify(currencyRepository, times(1)).findById("1"); // Verify findById was called
    }

    @Test
    void getConversionRateOnSpecificDate() throws IOException, URISyntaxException {
        Currency currency1 = new Currency("Dollar", "USD", '$');
        Currency currency2 = new Currency("Euro", "EUR", '€');
        when(currencyRepository.findById("1")).thenReturn(Optional.of(currency1));
        assertEquals(
            currency1.getConversionRate(currency2, "2024-04-14"),
            currencyService.getConversionRate("1", currency2, "2024-04-14"));
        verify(currencyRepository, times(1)).findById("1"); // Verify findById was called
    }

    @Test
    void getName() {
        Currency currency = new Currency("Dollar", "USD", '$');
        when(currencyRepository.findById("1")).thenReturn(Optional.of(currency));
        assertEquals("Dollar", currencyService.getName("1"));
        verify(currencyRepository, times(1)).findById("1");
    }

    @Test
    void getCode() {
        Currency currency = new Currency("Dollar", "USD", '$');
        when(currencyRepository.findById("1")).thenReturn(Optional.of(currency));
        assertEquals("USD", currencyService.getCode("1"));
        verify(currencyRepository, times(1)).findById("1");
    }

    @Test
    void getSymbol() {
        Currency currency = new Currency("Dollar", "USD", '$');
        when(currencyRepository.findById("1")).thenReturn(Optional.of(currency));
        assertEquals('$', currencyService.getSymbol("1"));
        verify(currencyRepository, times(1)).findById("1");
    }

    @Test
    void setName() {
        Currency currency = new Currency("Dollar", "USD", '$');
        when(currencyRepository.findById("1")).thenReturn(Optional.of(currency));
        when(currencyRepository.save(currency)).thenReturn(currency);
        currencyService.setName("1", "Euro");
        assertEquals("Euro", currency.getName());
        verify(currencyRepository, times(1)).save(currency);
    }

    @Test
    void setCode() {
        Currency currency = new Currency("Dollar", "USD", '$');
        when(currencyRepository.findById("1")).thenReturn(Optional.of(currency));
        when(currencyRepository.save(currency)).thenReturn(currency);
        currencyService.setCode("1", "EUR");
        assertEquals("EUR", currency.getCode());
        verify(currencyRepository, times(1)).save(currency);
    }

    @Test
    void setSymbol() {
        Currency currency = new Currency("Dollar", "USD", '$');
        when(currencyRepository.findById("1")).thenReturn(Optional.of(currency));
        when(currencyRepository.save(currency)).thenReturn(currency);
        currencyService.setSymbol("1", '€');
        assertEquals('€', currency.getSymbol());
        verify(currencyRepository, times(1)).save(currency);
    }

    @Test
    void deleteCurrency() {
        when(currencyRepository.findById("1")).thenReturn(
            Optional.of(new Currency("Euro", "EUR", '€')));
        doNothing().when(currencyRepository).deleteById("1");
        currencyService.deleteCurrency("1");
        verify(currencyRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteCurrencyNotFound() {
        when(currencyRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> currencyService.deleteCurrency("1"));
    }

    @Test
    void updateCurrency() {
        Currency currency = new Currency("Dollar", "USD", '$');
        currency.setId("1");
        when(currencyRepository.findById("1")).thenReturn(Optional.of(currency));
        when(currencyRepository.save(currency)).thenReturn(currency);
        currencyService.updateCurrency(currency);
        verify(currencyRepository, times(1)).save(currency);
    }

    @Test
    void updateCurrencyNotFound() {
        Currency currency = new Currency("Dollar", "USD", '$');
        currency.setId("1");
        when(currencyRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> currencyService.updateCurrency(currency));
    }
}
