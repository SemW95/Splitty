package commons;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Expense {
    ArrayList<Debt> debts;
    TotalDebt totalDebt;
    Person receiver;

    public Expense(Person receiver, BigDecimal totalAmount, Currency currency) {
        this.debts = new ArrayList<Debt>();
        this.receiver = receiver;
        this.totalDebt = new TotalDebt(
                totalAmount,
                currency,
                receiver,
                1
        );
    }

    public Expense(TotalDebt totalDebt, Person receiver, ArrayList<Debt> debts) {
        this.totalDebt = totalDebt;
        this.receiver = receiver;
        this.debts = new ArrayList<Debt>();
    }

    public void removeParticipant(Person participant) throws NoSuchElementException {
        // TODO: check index of that participant and remove that from the list
        // TODO: totalDebt.decreaseNoParticipants();
        // TODO: when participant doesn't exist: throw new NoSuchElementException();
    }

    public ArrayList<Debt> getDebts() {
        return debts;
    }

    public TotalDebt getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(TotalDebt totalDebt) {
        this.totalDebt = totalDebt;
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
        totalDebt.setReceiver(receiver);
    }

    /**
     * Checks if the Object that is provided is equal to this Expense object.
     *
     * @param o The Object that has to be compared to this Expense Object
     * @return true if they are equal, false when they are not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Expense expense = (Expense) o;
        return Objects.equals(debts, expense.debts)
            && Objects.equals(totalDebt, expense.totalDebt)
            && Objects.equals(receiver, expense.receiver);
    }


    /**
     * Provides a hash for the current Object.
     *
     * @return the hash of this Object
     */
    @Override
    public int hashCode() {
        return Objects.hash(debts, totalDebt, receiver);
    }
}
