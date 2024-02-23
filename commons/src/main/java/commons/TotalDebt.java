package commons;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Object class for the total debt to be paid relatedt o an expense,
 * with the amount, currency, recipient person, and participants.
 */
public class TotalDebt {
    BigDecimal toBePaid; // total amount to be paid to the irl payer
    Currency currency; // Currency to be paid in
    Participant receiver; // recipient of toBePaid
    int noParticipants; // amount of people to distribute toBePaid over

    /**
     * This is a constructor method for the totaldebt object.
     *
     * @param toBePaid total amount to be paid to the recipient
     * @param currency in what currency should the recipient be compensated?
     * @param receiver the participant object of the recipient
     * @param noParticipants how many people are sharing the debt to the payer
     *
     */
    public TotalDebt(BigDecimal toBePaid, Currency currency, Participant receiver,
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

    @Override
    public boolean equals(Object o) {
        //i dont think that two totaldebts
        // with the same currency, amount, recipient and number of payers should be
        //considered equal since it's not necessarily the same scenario
        //it really depends on the reference:
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return false;
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

    public Participant getReceiver() {
        return receiver;
    }

    public void setReceiver(Participant receiver) {
        this.receiver = receiver;
    }

    public int getNoParticipants() {
        return noParticipants;
    }

    public void setNoParticipants(int noParticipants) {
        this.noParticipants = noParticipants;
    }
}
