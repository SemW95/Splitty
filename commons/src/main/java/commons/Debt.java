package commons;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * This class is meant to be used to give each participant an amount yet to pay and an amount paid.
 */
public class Debt {
    Participant payer;
    TotalDebt totalDebt;
    BigDecimal paid;

    /**
     * Constructor for a Debt.
     *
     * @param payer the person in debt
     * @param totalDebt how large is the debt, in what currency, to whom, how many other indebtees
     * @param paid how much has already been paid
     *
     */
    public Debt(Participant payer, TotalDebt totalDebt, BigDecimal paid) {
        this.payer = payer;
        this.totalDebt = totalDebt;
        this.paid = paid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Debt debt1 = (Debt) o;
        return Objects.equals(payer, debt1.payer) && Objects.equals(totalDebt, debt1.totalDebt)
                && Objects.equals(paid,
                debt1.paid);
    }

    @Override
    public String toString() {
        return "Debt{"
                + "payer="
                + payer
                + ", debt="
                + totalDebt
                + ", paid="
                + paid
                + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(payer, totalDebt, paid);
    }

    public Participant getPayer() {
        return payer;
    }

    public void setPayer(Participant payer) {
        this.payer = payer;
    }

    public TotalDebt getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(TotalDebt totalDebt) {
        this.totalDebt = totalDebt;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }
}
