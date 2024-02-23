package commons;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


/**
 *
 */
public class TotalDebt {
    BigDecimal toBePaid;
    Currency currency;
    Person receiver;
    List<Person> noParticipants;

    public TotalDebt(BigDecimal toBePaid, Currency currency, Person receiver, List<Person> noParticipants) {
        this.toBePaid = toBePaid;
        this.currency = currency;
        this.receiver = receiver;
        this.noParticipants = noParticipants;
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

    public List<Person> getNoParticipants() {
        return noParticipants;
    }

    public void setNoParticipants(List<Person> noParticipants) {
        this.noParticipants = noParticipants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalDebt totalDebt = (TotalDebt) o;
        return Objects.equals(toBePaid, totalDebt.toBePaid) && Objects.equals(currency, totalDebt.currency) && Objects.equals(receiver, totalDebt.receiver) && Objects.equals(noParticipants, totalDebt.noParticipants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toBePaid, currency, receiver, noParticipants);
    }

    @Override
    public String toString() {
        return "TotalDebt{" +
                "toBePaid=" + toBePaid +
                ", currency=" + currency +
                ", receiver=" + receiver +
                ", noParticipants=" + noParticipants +
                '}';
    }
}
