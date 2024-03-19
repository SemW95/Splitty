package server.database;


import commons.Expense;
import commons.Person;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository Interface for the Expense Object.
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Optional<Expense> findById(long id);

    List<Expense> findByDescriptionContainingIgnoreCase(String description);

    Optional<Expense> findExpensesByPaidContains(BigDecimal paid);
    @Query("SELECT e FROM Expense e JOIN e.participants p WHERE UPPER(p.firstName) LIKE %:name% OR UPPER(p.lastName) LIKE %:name%")
    List<Expense> findExpensesByParticipantFirstNameOrLastNameIgnoreCase(String name);}
