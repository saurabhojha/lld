package com.splitwise.validators.validatorImpl;

import com.splitwise.enums.ExpenseType;
import com.splitwise.models.Expense;
import com.splitwise.models.Split;
import com.splitwise.validators.ExpenseValidator;

import java.util.List;

import static com.splitwise.enums.ExpenseType.EQUAL;
import static java.util.Objects.isNull;

public class EqualExpenseValidator implements ExpenseValidator {
    private static final ExpenseType expenseType = EQUAL;

    @Override
    public boolean isExpenseApplicable(ExpenseType expenseType) {
        return EqualExpenseValidator.expenseType.equals(expenseType);
    }

    @Override
    public boolean validateExpense(Expense expense) {
        if(isNull(expense)) {
            return false;
        }
        double totalAmount = expense.getTotalAmount();
        List<Split> splits = expense.getSplits();
        double amountPerPerson = totalAmount/splits.size();
        for(Split eachSplit: splits) {
            if(eachSplit.getAmount()!=amountPerPerson) {
                return false;
            }
        }
        return true;
    }
}
