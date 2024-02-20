package commons;

import java.util.Objects;

public class Debt {
    Person payer;
    TotalDebt debt;
    double paid;

    public Debt(Person payer, TotalDebt debt, double paid) {
        // TODO: create Debt
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Debt debt1 = (Debt) o;
        return Double.compare(paid, debt1.paid) == 0 && Objects.equals(payer, debt1.payer) && Objects.equals(debt, debt1.debt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payer, debt, paid);
    }
}
