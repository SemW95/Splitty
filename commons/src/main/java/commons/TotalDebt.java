package commons;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Object class for the total debt to be paid related o an expense,
 * with the amount, currency, recipient person, and participants.
 */
public class TotalDebt {
    BigDecimal toBePaid; // total amount to be paid to the irl payer
    Currency currency; // Currency to be paid in
    Person receiver; // recipient of toBePaid
    int noParticipants; // amount of people to distribute toBePaid over

    /**
     * This is a constructor method for the TotalDebt object.
     *
     * @param toBePaid total amount to be paid to the recipient
     * @param currency in what currency should the recipient be compensated?
     * @param receiver the participant object of the recipient
     * @param noParticipants how many people are sharing the debt to the payer
     *
     */
    public TotalDebt(BigDecimal toBePaid, Currency currency, Person receiver,
                     int noParticipants) {
        this.toBePaid = toBePaid;
        this.currency = currency;
        this.receiver = receiver;
        this.noParticipants = noParticipants;
    }

    /**
     * This returns a string representation of this object.
     *
     * @return a string representation of the totalDebt class.
     */
    @Override
    public String toString() {
        return "TotalDebt{"
                + "toBePaid="
                + toBePaid
                + ", currency="
                + currency
                + ", receiver="
                + receiver
                + ", noParticipants="
                + noParticipants
                + '}';
    }

    public void increaseNoParticipants() {
        noParticipants += 1;
    }

    public void decreaseNoParticipants() {
        noParticipants -= 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalDebt totalDebt = (TotalDebt) o;
        return noParticipants == totalDebt.noParticipants && Objects.equals(toBePaid, totalDebt.toBePaid) && Objects.equals(currency, totalDebt.currency) && Objects.equals(receiver, totalDebt.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toBePaid, currency, receiver, noParticipants);
    }

    public BigDecimal getToBePaid() {
        return toBePaid;
    }

    public void setToBePaid(BigDecimal toBePaid) {
        this.toBePaid = toBePaid;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }

    public int getNoParticipants() {
        return noParticipants;
    }

    public void setNoParticipants(int noParticipants) {
        this.noParticipants = noParticipants;
    }
}
