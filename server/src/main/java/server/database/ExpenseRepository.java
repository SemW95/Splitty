package server.database;


import commons.Expense;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository Interface for the Expense Object.
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Optional<Expense> findById(long id);

    List<Expense> findByDescriptionContainingIgnoreCase(String description);

    Optional<Expense> findExpensesByPaid(BigDecimal paid);

    @Query("SELECT e FROM Expense e JOIN e.participants p WHERE LOWER(p.firstName) "
        +
        "LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(p.lastName) "
        +
        "LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Expense> findExpensesByParticipantFirstNameOrLastNameIgnoreCase(String name);

}
