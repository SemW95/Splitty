package server.service;

import commons.Payment;
import commons.Person;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.PaymentRepository;

/**
 * Service for Tag. [CONT -> SERV -> REPO]
 */
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * Adds a Payment.
     *
     * @param payment The Payment that should be added
     */
    public Payment addPayment(Payment payment) {
        if (payment.getId() == null || !paymentRepository.existsById(payment.getId())) {
            return paymentRepository.save(payment);
        }

        throw new IllegalStateException("A Payment with this id already exists");
    }

    /**
     * Returns all payments.
     *
     * @return All payments
     */
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    /**
     * Returns a Payment Object for a given id.
     *
     * @param id The id of the Payment that should be received
     * @return The specified Payment
     */
    public Payment getPaymentById(String id) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);

        if (optionalPayment.isEmpty()) {
            throw new IllegalStateException("There is no Payment with this id");
        }

        return optionalPayment.get();
    }

    /**
     * Retrieves the (Person) payer of the specified Payment.
     *
     * @param paymentId The id of the Payment
     * @return The payer Person Object in the Payment
     */
    public Person getPayer(String paymentId) {
        return getPaymentById(paymentId).getPayer();
    }

    /**
     * Retrieves the (Person) receiver of the specified Payment.
     *
     * @param paymentId The id of the Payment
     * @return The receiver Person Object in the Payment
     */
    public Person getReceiver(String paymentId) {
        return getPaymentById(paymentId).getReceiver();
    }

    /**
     * Retrieves the ID of the (Person) payer of the specified Payment.
     *
     * @param paymentId The id of the Payment
     * @return The payer Person Object's ID in the Payment
     */
    public String getPayerId(String paymentId) {
        return getPaymentById(paymentId).getPayer().getId();
    }

    /**
     * Retrieves the ID of the (Person) receiver of the specified Payment.
     *
     * @param paymentId The id of the Payment
     * @return The receiver Person Object's ID in the Payment
     */
    public String getReceiverId(String paymentId) {
        return getPaymentById(paymentId).getReceiver().getId();
    }

    /**
     * Retrieves the (BigDecimal) amount of the specified Payment.
     *
     * @param paymentId The id of the Payment
     * @return The amount BigDecimal Object in the Payment
     */
    public BigDecimal getAmount(String paymentId) {
        return getPaymentById(paymentId).getAmount();
    }

    /**
     * Sets a new payer for a Payment.
     *
     * @param paymentId The id of the Payment
     * @param payer     The payer for the Payment
     */
    public void setPayer(String paymentId, Person payer) {
        Payment payment = getPaymentById(paymentId);

        payment.setPayer(payer);
        paymentRepository.save(payment);
    }

    /**
     * Sets a new receiver for a Payment.
     *
     * @param paymentId The id of the Payment
     * @param receiver  The receiver for the Payment
     */
    public void setReceiver(String paymentId, Person receiver) {
        Payment payment = getPaymentById(paymentId);

        payment.setReceiver(receiver);
        paymentRepository.save(payment);
    }

    /**
     * Sets a new amount for a Payment.
     *
     * @param paymentId The id of the Payment
     * @param amount    The amount for the Payment
     */
    public void setAmount(String paymentId, BigDecimal amount) {
        Payment payment = getPaymentById(paymentId);

        payment.setAmount(amount);
        paymentRepository.save(payment);
    }

    /**
     * Deletes a Payment.
     *
     * @param id The id of the Payment that should be deleted
     */
    public void deletePayment(String id) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);

        if (optionalPayment.isEmpty()) {
            throw new IllegalStateException("There is no Payment with this id");
        }

        paymentRepository.deleteById(id);
    }

    /**
     * Updates a payment.
     *
     * @param payment the new payment
     */
    public void updatePayment(Payment payment) {
        if (!paymentRepository.existsById(payment.getId())) {
            throw new IllegalStateException("There is no Payment with this id");
        }
        paymentRepository.save(payment);
    }
}
