package com.splitwise.validators;

import com.splitwise.enums.ExpenseType;
import com.splitwise.models.Expense;

public interface ExpenseValidator {

    boolean isExpenseApplicable(ExpenseType expenseType);
    boolean validateExpense(Expense expense);
}
