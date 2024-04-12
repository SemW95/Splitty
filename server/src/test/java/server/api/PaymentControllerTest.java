package server.api;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Payment;
import commons.Person;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import server.service.PaymentService;

/**
 * The test class for the PaymentController.
 */
@WebMvcTest(PaymentController.class)
@ActiveProfiles("test")
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Payment payment;
    private Person payer;
    private Person receiver;

    /**
     * The preparation that will run before every test.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(
            new PaymentController(paymentService)).build();

        payer = new Person(
            "John",
            "Doe",
            "john.doe@example.com",
            "NL39RABO8247360527",
            "DEUTDEDBFRA");
        payer.setId("1");
        receiver = new Person(
            "Jane",
            "Doe",
            "jane.doe@example.com",
            "DE95500105175827669536",
            "NWBKGB22");
        receiver.setId("2");
        payment = new Payment(payer, receiver, new BigDecimal("100.00"));
    }

    @Test
    public void addPaymentTest() throws Exception {
        given(paymentService.addPayment(payment)).willReturn(payment);

        mockMvc.perform(post("/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payment)))
            .andExpect(status().isCreated());
    }

    // @Test
    // public void getAllPaymentsTest() throws Exception {
    //     List<Payment> payments = Collections.singletonList(payment);
    //
    //     given(paymentService.getAllPayments()).willReturn(payments);
    //
    //     mockMvc.perform(get("/payment"))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    //         .andExpect(jsonPath("$", hasSize(1)))
    //         .andExpect(jsonPath("$[0].amount", is(100.0)));
    // }

    @Test
    public void getPaymentByIdTest() throws Exception {
        final String paymentId = "1";

        given(paymentService.getPaymentById(paymentId)).willReturn(payment);

        mockMvc.perform(get("/payment/{id}", paymentId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.amount", is(100.0)));
    }

    @Test
    public void getPayerTest() throws Exception {
        final String paymentId = "1";

        given(paymentService.getPayer(paymentId)).willReturn(payer);

        mockMvc.perform(get("/payment/{id}/payer", paymentId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    public void getReceiverTest() throws Exception {
        final String paymentId = "1";

        given(paymentService.getReceiver(paymentId)).willReturn(receiver);

        mockMvc.perform(get("/payment/{id}/receiver", paymentId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstName", is("Jane")));
    }

    @Test
    public void deletePaymentTest() throws Exception {
        final String paymentId = "1";
        doNothing().when(paymentService).deletePayment(paymentId);

        mockMvc.perform(delete("/payment/{id}", paymentId))
            .andExpect(status().isOk());
    }

    // Test for getting the payer's ID of a payment
    @Test
    public void getPayerIdTest() throws Exception {
        final String paymentId = "1";
        final String expectedPayerId = payer.getId();

        given(paymentService.getPayerId(paymentId)).willReturn(expectedPayerId);

        mockMvc.perform(get("/payment/{id}/payer/id", paymentId))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedPayerId));
    }

    // Test for getting the receiver's ID of a payment
    @Test
    public void getReceiverIdTest() throws Exception {
        final String paymentId = "1";
        final String expectedReceiverId = receiver.getId();

        given(paymentService.getReceiverId(paymentId)).willReturn(expectedReceiverId);

        mockMvc.perform(get("/payment/{id}/receiver/id", paymentId))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(expectedReceiverId)));
    }

    // Test for getting the receiver's ID of a payment
    @Test
    public void getAmountTest() throws Exception {
        final String paymentId = "1";
        final BigDecimal expectedAmount = payment.getAmount();

        given(paymentService.getAmount(paymentId)).willReturn(expectedAmount);

        mockMvc.perform(get("/payment/{id}/amount", paymentId))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(expectedAmount.toString())));
    }

    // Test for setting the payer of a payment
    @Test
    public void setPayerTest() throws Exception {
        final String paymentId = "1";
        final Person newPayer = new Person("Alice",
            "Smith",
            "alice@example.com",
            "GB29NWBK60161331926819",
            "NWBKGB22");

        doNothing().when(paymentService).setPayer(paymentId, newPayer);

        mockMvc.perform(put("/payment/{id}/payer", paymentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPayer)))
            .andExpect(status().isOk());

        // Verifying the interaction with the mock service
        verify(paymentService).setPayer(eq(paymentId), any(Person.class));
    }

    // Test for setting the receiver of a payment
    @Test
    public void setReceiverTest() throws Exception {
        final String paymentId = "1";
        final Person newReceiver = new Person(
            "Bob",
            "Brown",
            "bob@example.com",
            "DE89370400440532013000",
            "DEUTDEDBFRA");

        doNothing().when(paymentService).setReceiver(paymentId, newReceiver);

        mockMvc.perform(put("/payment/{id}/receiver", paymentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newReceiver)))
            .andExpect(status().isOk());

        // Verifying the interaction with the mock service
        verify(paymentService).setReceiver(eq(paymentId), any(Person.class));
    }

    // Test for setting the amount of a payment
    @Test
    public void setAmountTest() throws Exception {
        final String paymentId = "1";
        final BigDecimal newAmount = new BigDecimal("150.00");

        doNothing().when(paymentService).setAmount(paymentId, newAmount);

        mockMvc.perform(put("/payment/{id}/amount", paymentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newAmount)))
            .andExpect(status().isOk());

        // Verifying the interaction with the mock service
        verify(paymentService).setAmount(eq(paymentId), eq(newAmount));
    }

    @Test
    public void handleIllegalStateExceptionTest() throws Exception {
        String invalidExpenseId = "nonExistingId";
        given(paymentService.getPaymentById(invalidExpenseId)).willThrow(
            new IllegalStateException("There is no Expense with this id")
        );

        mockMvc.perform(get("/payment/{id}", invalidExpenseId))
            .andExpect(status().isNotFound());
    }
}
