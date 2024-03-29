package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Objects;


/**
 * Stores a money transaction.
 */
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @ManyToOne
    Person payer;
    @ManyToOne
    Person receiver;
    BigDecimal amount;

    /**
     * Creates the Payment class.
     *
     * @param payer    The Person that paid this money.
     * @param receiver The Person that received this money.
     * @param amount   The Money that has been transferred.
     */
    public Payment(Person payer, Person receiver, BigDecimal amount) {
        this.payer = payer;
        this.receiver = receiver;
        this.amount = amount;
    }

    /**
     * Empty constructor for JPA.
     */
    protected Payment() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Payment payment = (Payment) o;
        return Objects.equals(payer, payment.payer) && Objects.equals(receiver, payment.receiver)
            && Objects.equals(amount, payment.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payer, receiver, amount);
    }

    public Person getPayer() {
        return payer;
    }

    public void setPayer(Person payer) {
        this.payer = payer;
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }
}
