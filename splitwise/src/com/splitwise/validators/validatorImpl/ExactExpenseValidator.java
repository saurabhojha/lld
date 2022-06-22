package com.splitwise.validators.validatorImpl;

import com.splitwise.enums.ExpenseType;
import com.splitwise.models.Expense;
import com.splitwise.models.Split;
import com.splitwise.validators.ExpenseValidator;

import java.util.List;

import static com.splitwise.enums.ExpenseType.EXACT;
import static java.util.Objects.isNull;

public class ExactExpenseValidator implements ExpenseValidator {

    private static final ExpenseType expenseType = EXACT;

    @Override
    public boolean isExpenseApplicable(ExpenseType expenseType) {
        // Same for this, could be one plugin in PluginRegistry<ExpenseValidator>
        return ExactExpenseValidator.expenseType.equals(expenseType);
    }

    @Override
    public boolean validateExpense(Expense expense) {
        if(isNull(expense)) {
            return false;
        }
        List<Split> splits = expense.getSplits();
        double totalAmountPerUser = 0;
        // Use streams, use mutable reduction. All these things count
        // as plus points in interview I think :^) 
        for(Split eachSplit: splits) {
            totalAmountPerUser+=eachSplit.getAmount();
        }
        return totalAmountPerUser==expense.getTotalAmount();
    }
}
