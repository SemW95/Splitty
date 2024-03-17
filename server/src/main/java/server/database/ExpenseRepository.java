package server.database;


import commons.Expense;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository Interface for the Expense Object.
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Optional<Expense> findById(long id);

    List<Expense> findByDescriptionContainingIgnoreCase(String description);
}
