package commons;

import java.math.BigDecimal;
import java.util.Objects;

public class Debt {
    Person payer;
    TotalDebt debt;
    BigDecimal paid;

    public Debt(Person payer, TotalDebt debt, BigDecimal paid) {
        this.payer = payer;
        this.debt = debt;
        this.paid = paid;
    }

    /**
     * Checks if the Object that is provided is equal to this Debt object.
     *
     * @param o The Object that has to be compared to this Debt Object
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
        Debt debt1 = (Debt) o;
        return Objects.equals(paid, debt1.paid) && Objects.equals(payer, debt1.payer)
            && Objects.equals(debt, debt1.debt);
    }

    /**
     * Provides a hash for the current Object.
     *
     * @return the hash of this Object
     */
    @Override
    public int hashCode() {
        return Objects.hash(payer, debt, paid);
    }

    public Person getPayer() {
        return payer;
    }

    public void setPayer(Person payer) {
        this.payer = payer;
    }

    public TotalDebt getDebt() {
        return debt;
    }

    public void setDebt(TotalDebt debt) {
        this.debt = debt;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }
}
