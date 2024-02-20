package commons;

import java.util.ArrayList;
import java.util.Objects;

public class Expense {
    ArrayList<Debt> debts;
    TotalDebt totalDebt;
    Person receiver;

    public Expense(TotalDebt totalDebt, Person receiver) {
        // TODO: create Expense without a Debt list
    }

    public Expense(TotalDebt totalDebt, Person receiver, ArrayList<Debt> debts) {
        // TODO: create Expense with a Debt list
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Objects.equals(debts, expense.debts) && Objects.equals(totalDebt, expense.totalDebt) && Objects.equals(receiver, expense.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(debts, totalDebt, receiver);
    }
}
