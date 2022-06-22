package com.splitwise.validators.validatorImpl;

import com.splitwise.enums.ExpenseType;
import com.splitwise.models.Expense;
import com.splitwise.models.Split;
import com.splitwise.validators.ExpenseValidator;

import java.util.List;

import static com.splitwise.enums.ExpenseType.PERCENTAGE;
import static java.util.Objects.isNull;

public class PercentageExpenseValidator implements ExpenseValidator {
    private static final ExpenseType expenseType = PERCENTAGE;

    @Override
    public boolean isExpenseApplicable(ExpenseType expenseType) {
        // Same plugin stuff here
        return PercentageExpenseValidator.expenseType.equals(expenseType);
    }

    @Override
    public boolean validateExpense(Expense expense) {
        if(isNull(expense)) {
            return false;
        }
        List<Split> splits = expense.getSplits();
        double totalPercentage = 0;
        double finalPercentage = 100;
        for(Split eachSplit: splits) {
            totalPercentage+=eachSplit.getPercentage();
        }
        return totalPercentage==finalPercentage;
    }
}
