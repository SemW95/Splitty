package server.api;

import commons.Payment;
import commons.Person;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public void addPayment(@RequestBody Payment payment) {
        paymentService.addPayment(payment);
    }

    /** Gets all the Payments.
     *
     * @return All payments
     */
    @GetMapping(path = "/payment")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    /** Gets a Payment.
     *
     * @param id The id of the Payment
     * @return The Payment
     */
    @GetMapping(path = "/payment/{id}")
    public Payment getPaymentById(@PathVariable(name = "id") Long id) {
        return paymentService.getPaymentById(id);
    }

    /** Gets the payer of a Payment.
     *
     * @param paymentId The id of the Payment
     * @return The payer of the Payment
     */
    @GetMapping(path = "/payment/{id}/payer")
    public Person getPayer(@PathVariable(name = "id") Long paymentId) {
        return paymentService.getPayer(paymentId);
    }

    /** Gets the receiver of a Payment.
     *
     * @param paymentId The id of the Payment
     * @return The receiver of the Payment
     */
    @GetMapping(path = "/payment/{id}/receiver")
    public Person getReceiver(@PathVariable(name = "id") Long paymentId) {
        return paymentService.getReceiver(paymentId);
    }

    /** Gets the amount of a Payment.
     *
     * @param paymentId The id of the Payment
     * @return The amount of the Payment
     */
    @GetMapping(path = "/payment/{id}/amount")
    public BigDecimal getAmount(@PathVariable(name = "id") Long paymentId) {
        return paymentService.getAmount(paymentId);
    }

    /** sets the payer for a Payment.
     *
     * @param paymentId The id of the Payment
     * @param payer The payer for the Payment
     */
    @PutMapping(path = "/payment/{id}/payer")
    public void setPayer(
        @PathVariable(name = "id") Long paymentId,
        @RequestBody Person payer
    ) {
        paymentService.setPayer(paymentId, payer);
    }

    /** sets the receiver for a Payment.
     *
     * @param paymentId The id of the Payment
     * @param receiver The receiver for the Payment
     */
    @PutMapping(path = "/payment/{id}/receiver")
    public void setReceiver(
        @PathVariable(name = "id") Long paymentId,
        @RequestBody Person receiver
    ) {
        paymentService.setReceiver(paymentId, receiver);
    }

    /** sets the amount for a Payment.
     *
     * @param paymentId The id of the Payment
     * @param amount The amount for the Payment
     */
    @PutMapping(path = "/payment/{id}/amount")
    public void setAmount(
        @PathVariable(name = "id") Long paymentId,
        @RequestBody BigDecimal amount
    ) {
        paymentService.setAmount(paymentId, amount);
    }

    @DeleteMapping(path = "/payment/{id}")
    public void deletePayment(@PathVariable(name = "id") Long paymentId) {
        paymentService.deletePayment(paymentId);
    }
}
