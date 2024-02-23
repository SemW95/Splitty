package commons;

import java.math.BigDecimal;
import java.util.Objects;

public class TotalDebt {
    BigDecimal toBePaid;
    Currency currency;

    public TotalDebt(double toBePaid, Currency currency) {
        // TODO: create TotalDebt
    }

    /**
     * Checks if the Object that is provided is equal to this TotalDebt object.
     *
     * @param o The Object that has to be compared to this TotalDebt Object
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
        TotalDebt totalDebt = (TotalDebt) o;
        return Objects.equals(toBePaid, totalDebt.toBePaid)
            && Objects.equals(currency, totalDebt.currency);
    }

    /**
     * Provides a hash for the current Object.
     *
     * @return the hash of this Object
     */
    @Override
    public int hashCode() {
        return Objects.hash(toBePaid, currency);
    }
}
