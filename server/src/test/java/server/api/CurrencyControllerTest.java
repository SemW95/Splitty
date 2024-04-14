package server.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Currency;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import server.service.CurrencyService;

@WebMvcTest(CurrencyController.class)
@ActiveProfiles("test")
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CurrencyController(currencyService)).build();
    }

    @Test
    void getAllCurrency() throws Exception {
        List<Currency> expectedCurrencies = new ArrayList<>();
        expectedCurrencies.add(new Currency("Euro", "EUR", '€'));
        when(currencyService.getAllCurrency()).thenReturn(expectedCurrencies);

        mockMvc.perform(get("/currency"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expectedCurrencies)));
    }

    @Test
    void getCurrencyById() throws Exception {
        // Setup
        String currencyId = "1";
        Currency expectedCurrency = new Currency("Euro", "EUR", '€');
        expectedCurrency.setId(currencyId);

        // Mocking the service layer
        when(currencyService.getCurrencyById(currencyId)).thenReturn(expectedCurrency);

        // Perform GET request
        mockMvc.perform(get("/currency/{id}", currencyId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(currencyId))
            .andExpect(jsonPath("$.name").value("Euro"))
            .andExpect(jsonPath("$.code").value("EUR"))
            .andExpect(jsonPath("$.symbol").value("€"));

        // Verify service interactions
        verify(currencyService).getCurrencyById(currencyId);
    }


    @Test
    void createCurrency() throws Exception {
        Currency newCurrency = new Currency("Dollar", "USD", '$');
        doNothing().when(currencyService).createCurrency(any(Currency.class));

        mockMvc.perform(post("/currency")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCurrency)))
            .andExpect(status().isCreated());
    }


    @Test
    void getConversionRate() throws Exception {
        String id = "1";
        Currency otherCurrency = new Currency("Euro", "EUR", '€');
        BigDecimal rate = new BigDecimal("0.85");
        when(currencyService.getConversionRate(eq(id), any(String.class))).thenReturn(rate);

        mockMvc.perform(get("/currency/{id}/rate/{otherCurrency}", id, otherCurrency))
            .andExpect(status().isOk())
            .andExpect(content().string(rate.toString()));
    }


    @Test
    void getConversionRateWithDate() throws Exception {
        String id = "1";
        Currency otherCurrency = new Currency("Euro", "EUR", '€');
        String date = "2023-10-10";
        BigDecimal rate = new BigDecimal("0.86");
        when(
            currencyService.getConversionRate(eq(id), any(String.class), eq(date))
        ).thenReturn(rate);

        mockMvc.perform(
            get("/currency/{id}/rate/{otherCurrency}/date/{date}",
                id,
                otherCurrency,
                date))
            .andExpect(status().isOk())
            .andExpect(content().string(rate.toString()));
    }


    @Test
    void getName() throws Exception {
        String id = "1";
        String name = "Euro";
        when(currencyService.getName(id)).thenReturn(name);

        mockMvc.perform(get("/currency/{id}/name", id))
            .andExpect(status().isOk())
            .andExpect(content().string(name));
    }


    @Test
    void getCode() throws Exception {
        String id = "1";
        String code = "EUR";
        when(currencyService.getCode(id)).thenReturn(code);

        mockMvc.perform(get("/currency/{id}/code", id))
            .andExpect(status().isOk())
            .andExpect(content().string(code));
    }


    @Test
    void getSymbol() throws Exception {
        String id = "1";
        char symbol = '€';
        when(currencyService.getSymbol(id)).thenReturn(symbol);

        mockMvc.perform(get("/currency/{id}/symbol", id))
            .andExpect(status().isOk())
            .andExpect(content().string(Character.toString(symbol)));
    }


    @Test
    void setName() throws Exception {
        String id = "1";
        String name = "Dollar";
        doNothing().when(currencyService).setName(id, name);

        mockMvc.perform(put("/currency/{id}/name/{name}", id, name))
            .andExpect(status().isOk());
    }


    @Test
    void setCode() throws Exception {
        String id = "1";
        String code = "USD";
        doNothing().when(currencyService).setCode(id, code);

        mockMvc.perform(put("/currency/{id}/code/{code}", id, code))
            .andExpect(status().isOk());
    }


    @Test
    void setSymbol() throws Exception {
        String id = "1";
        char symbol = '$';
        doNothing().when(currencyService).setSymbol(id, symbol);

        mockMvc.perform(put("/currency/{id}/symbol/{symbol}", id, symbol))
            .andExpect(status().isOk());
    }


    @Test
    void deleteCurrency() throws Exception {
        String id = "1";
        doNothing().when(currencyService).deleteCurrency(id);

        mockMvc.perform(delete("/currency/{id}", id))
            .andExpect(status().isOk());
    }


    @Test
    void updateCurrency() throws Exception {
        // Create a currency object with an ID that supposedly exists in the database
        Currency currency = new Currency("Euro", "EUR", '€');
        currency.setId("existing-id");

        // Mock the service to behave as if the currency exists and can be updated
        doNothing().when(currencyService).updateCurrency(currency);
        when(currencyService.currencyExist(currency.getId())).thenReturn(true);

        // Perform the PUT request
        mockMvc.perform(put("/currency")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(currency)))
            .andExpect(status().isOk());

        // Verify that the service was called to update the currency
        verify(currencyService).updateCurrency(currency);
    }


    @Test
    public void handleIllegalStateExceptionTest() throws Exception {
        String invalidExpenseId = "nonExistingId";
        given(currencyService.getCurrencyById(invalidExpenseId)).willThrow(
            new IllegalStateException("There is no Expense with this id")
        );

        mockMvc.perform(get("/event/id/{id}", invalidExpenseId))
            .andExpect(status().isNotFound());
    }

}