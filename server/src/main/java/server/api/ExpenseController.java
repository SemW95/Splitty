package server.api;

import commons.Expense;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.service.ExpenseService;

/**
 * Controller for Expense [CONT -> SERV -> REPO].
 */
@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }


    // TODO move this to admin
    @GetMapping(path = "/expense")
    public List<Expense> getAllExpense() {
        return expenseService.getAllExpense();
    }

    @GetMapping(path = "/expense/{id}")
    public Expense getExpenseById(@PathVariable String id) {
        return expenseService.getExpenseById(id);
    }

    @PostMapping(path = "/expense")
    public void createExpense(@RequestBody Expense expense) {
        expenseService.createExpense(expense);
    }

    @PutMapping(path = "/expense")
    public void updateExpense(@RequestBody Expense expense) {
        expenseService.updateExpense(expense);
    }

}
