package commons;

import java.util.Objects;

public class Debt {
    Person payer;
    TotalDebt debt;
    double paid;

    public Debt(Person payer, TotalDebt debt, double paid) {
        // TODO: create Debt
    }

    /** Checks if the Object that is provided is equal to this Debt object
     * @param o The Object that has to be compared to this Debt Object
     * @return true if they are equal, false when they are not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Debt debt1 = (Debt) o;
        return Double.compare(paid, debt1.paid) == 0 && Objects.equals(payer, debt1.payer) && Objects.equals(debt, debt1.debt);
    }

    /** Provides a hash for the current Object
     * @return the hash of this Object
     */
    @Override
    public int hashCode() {
        return Objects.hash(payer, debt, paid);
    }
}
