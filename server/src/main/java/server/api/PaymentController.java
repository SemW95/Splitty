package server.api;

import commons.Payment;
import commons.Person;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import server.service.PaymentService;

/**
 * Controller for Tag. [CONT -> SERV -> REPO]
 */
@RestController
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(path = "/payment")
    public ResponseEntity<Object> addPayment(@RequestBody Payment payment) {
        paymentService.addPayment(payment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // The user should not be able to get all payments for security reasons.
    // Therefor: no getAllPayments()

    /**
     * Gets a Payment.
     *
     * @param id The id of the Payment
     * @return The Payment
     */
    @GetMapping(path = "/payment/{id}")
    @ResponseBody
    public Payment getPaymentById(@PathVariable(name = "id") String id) {
        return paymentService.getPaymentById(id);
    }

    /**
     * Gets the payer of a Payment.
     *
     * @param paymentId The id of the Payment
     * @return The payer of the Payment
     */
    @GetMapping(path = "/payment/{id}/payer")
    @ResponseBody
    public Person getPayer(@PathVariable(name = "id") String paymentId) {
        return paymentService.getPayer(paymentId);
    }

    /**
     * Gets the receiver of a Payment.
     *
     * @param paymentId The id of the Payment
     * @return The receiver of the Payment
     */
    @GetMapping(path = "/payment/{id}/receiver")
    @ResponseBody
    public Person getReceiver(@PathVariable(name = "id") String paymentId) {
        return paymentService.getReceiver(paymentId);
    }

    /**
     * Gets the payer's ID of a Payment.
     *
     * @param paymentId The id of the Payment
     * @return The payer's ID of the Payment
     */
    @GetMapping(path = "/payment/{id}/payer/id")
    @ResponseBody
    public String getPayerId(@PathVariable(name = "id") String paymentId) {
        return paymentService.getPayerId(paymentId);
    }

    /**
     * Gets the receiver's ID of a Payment.
     *
     * @param paymentId The id of the Payment
     * @return The receiver's ID of the Payment
     */
    @GetMapping(path = "/payment/{id}/receiver/id")
    @ResponseBody
    public String getReceiverId(@PathVariable(name = "id") String paymentId) {
        return paymentService.getReceiverId(paymentId);
    }

    /**
     * Gets the amount of a Payment.
     *
     * @param paymentId The id of the Payment
     * @return The amount of the Payment
     */
    @GetMapping(path = "/payment/{id}/amount")
    @ResponseBody
    public BigDecimal getAmount(@PathVariable(name = "id") String paymentId) {
        return paymentService.getAmount(paymentId);
    }

    /**
     * sets the payer for a Payment.
     *
     * @param paymentId The id of the Payment
     * @param payer     The payer for the Payment
     */
    @PutMapping(path = "/payment/{id}/payer")
    public void setPayer(
        @PathVariable(name = "id") String paymentId,
        @RequestBody Person payer
    ) {
        paymentService.setPayer(paymentId, payer);
    }

    /**
     * sets the receiver for a Payment.
     *
     * @param paymentId The id of the Payment
     * @param receiver  The receiver for the Payment
     */
    @PutMapping(path = "/payment/{id}/receiver")
    public void setReceiver(
        @PathVariable(name = "id") String paymentId,
        @RequestBody Person receiver
    ) {
        paymentService.setReceiver(paymentId, receiver);
    }

    /**
     * sets the amount for a Payment.
     *
     * @param paymentId The id of the Payment
     * @param amount    The amount for the Payment
     */
    @PutMapping(path = "/payment/{id}/amount")
    public void setAmount(
        @PathVariable(name = "id") String paymentId,
        @RequestBody BigDecimal amount
    ) {
        paymentService.setAmount(paymentId, amount);
    }

    @DeleteMapping(path = "/payment/{id}")
    public void deletePayment(@PathVariable(name = "id") String paymentId) {
        paymentService.deletePayment(paymentId);
    }

    @PutMapping(path = "/payment")
    public void updatePayment(@RequestBody Payment payment) {
        paymentService.updatePayment(payment);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex) {
        // Return a ResponseEntity with the NOT_FOUND status
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
