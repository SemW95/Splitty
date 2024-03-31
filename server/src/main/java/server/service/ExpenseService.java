package server.service;

import commons.Expense;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.ExpenseRepository;

/**
 * Service for Person. [CONT -> SERV -> REPO]
 */
@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getAllExpense() {
        return expenseRepository.findAll();
    }

    public void deleteExpense(String id) {
        expenseRepository.deleteById(id);
    }

    /**
     * Creates an expense. Fails if an expense exists with the same id or invite code.
     *
     * @param expense the expense to be created.
     */
    public void createExpense(Expense expense) {
        if (expense.getId() != null || expenseRepository.findById(expense.getId()).isEmpty()) {
            expenseRepository.save(expense);
        }
    }

    /**
     * Updates an expense.
     *
     * @param expense expense
     */
    public void updateExpense(Expense expense) {
        expenseRepository.save(expense);

    }

    public Expense getExpenseById(String id) {
        return expenseRepository.findById(id).orElse(null);
    }
}
