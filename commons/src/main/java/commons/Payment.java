package commons;

import java.util.Objects;


/** Stores a money transaction.
 */
public class Payment {
    Person payer;
    Person receiver;
    Money amount;

    /** Creates the Payment class.
     *
     * @param payer The Person that paid this money.
     * @param receiver The Person that received this money.
     * @param amount The Money that has been transferred.
     */
    public Payment(Person payer, Person receiver, Money amount) {
        this.payer = payer;
        this.receiver = receiver;
        this.amount = amount;
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

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }
}
