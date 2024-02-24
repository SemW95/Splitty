package commons;

import java.util.ArrayList;
import java.util.Objects;

public class Expense {
    ArrayList<Debt> debts;
    TotalDebt totalDebt;
    Person receiver;

    public Expense(TotalDebt totalDebt, Person receiver) {
        this.totalDebt = totalDebt;
        this.receiver = receiver;
    }

    public Expense(TotalDebt totalDebt, Person receiver, ArrayList<Debt> debts) {
        this.totalDebt = totalDebt;
        this.receiver = receiver;
        this.debts = new ArrayList<Debt>();
    }

    public ArrayList<Debt> getDebts() {
        return debts;
    }

    public void setDebts(ArrayList<Debt> debts) {
        this.debts = debts;
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
