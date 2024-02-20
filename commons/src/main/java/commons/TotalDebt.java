package commons;

import java.util.Objects;

public class TotalDebt {
    double toBePaid;
    Currency currency;

    public TotalDebt(double toBePaid, Currency currency) {
        // TODO: create TotalDebt
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalDebt totalDebt = (TotalDebt) o;
        return Double.compare(toBePaid, totalDebt.toBePaid) == 0 && Objects.equals(currency, totalDebt.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toBePaid, currency);
    }
}
