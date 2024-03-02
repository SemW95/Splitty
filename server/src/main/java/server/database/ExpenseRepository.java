package server.database;


import commons.Expense;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository Interface for the Expense Object.
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long>{
    Expense findById(long id);

    List<Expense> findByDescription(String description);
}
