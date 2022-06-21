package com.splitwise.service;

import com.splitwise.enums.ExpenseType;
import com.splitwise.models.Expense;
import com.splitwise.models.Split;
import com.splitwise.validators.ExpenseValidator;
import com.splitwise.validators.validatorImpl.EqualExpenseValidator;
import com.splitwise.validators.validatorImpl.ExactExpenseValidator;
import com.splitwise.validators.validatorImpl.PercentageExpenseValidator;

import java.util.ArrayList;
import java.util.List;

public class ExpenseService {

    private List<ExpenseValidator> expenseValidators;
    private SplitService splitService;
    public ExpenseService() {
        this.expenseValidators = new ArrayList<>();
        splitService = new SplitService();
        initialiseExpenseValidators();
    }

    private void initialiseExpenseValidators() {
        List<ExpenseValidator> expenseValidators = new ArrayList<>();
        EqualExpenseValidator equalExpenseValidator = new EqualExpenseValidator();
        PercentageExpenseValidator percentageExpenseValidator = new PercentageExpenseValidator();
        ExactExpenseValidator exactExpenseValidator = new ExactExpenseValidator();
        expenseValidators.add(exactExpenseValidator);
        expenseValidators.add(equalExpenseValidator);
        expenseValidators.add(percentageExpenseValidator);
        this.expenseValidators = expenseValidators;
    }

    public Expense createExpense(String paidByUserId, double totalAmount, List<Split> splits, ExpenseType expenseType) {
        Expense expense = new Expense();
        expense.setExpenseType(expenseType);
        expense.setSplits(splits);
        expense.setPaidByUserId(paidByUserId);
        expense.setTotalAmount(totalAmount);

        return expense;
    }

    public boolean validateExpense(Expense expense) {
        for(ExpenseValidator expenseValidator:expenseValidators) {
            if(expenseValidator.isExpenseApplicable(expense.getExpenseType())) {
                return expenseValidator.validateExpense(expense);
            }
        }
        return false;
    }

    public Expense createExpenseFromInput(String []commands) {
        String userId="";
        double totalAmount=0.0;
        List<Split> splits = new ArrayList<>();
        ExpenseType expenseType=ExpenseType.DEFAULT;
        int totalSplits;
        double perHeadAmount;
        switch (commands[0]) {
            case "EQUAL":
                userId = commands[1];
                totalAmount = Double.parseDouble(commands[2]);
                expenseType = ExpenseType.EQUAL;
                totalSplits = Integer.parseInt(commands[3]);
                perHeadAmount = totalAmount/(1.0d *totalSplits);
                for(int i= 4;i<commands.length;i++) {
                    Split split = splitService.createSplit(commands[i],perHeadAmount,0);
                    splits.add(split);
                }
                break;

            case "EXACT":
                userId = commands[1];
                totalAmount = Double.parseDouble(commands[2]);
                expenseType = ExpenseType.EXACT;
                totalSplits = Integer.parseInt(commands[3]);
                for(int i= 4;i<4+totalSplits;i++) {
                    perHeadAmount = Double.parseDouble(commands[i+totalSplits]);
                    Split split = splitService.createSplit(commands[i],perHeadAmount,0);
                    splits.add(split);
                }
                break;

            case "PERCENTAGE":
                userId = commands[1];
                totalAmount = Double.parseDouble(commands[2]);
                expenseType = ExpenseType.PERCENTAGE;
                totalSplits = Integer.parseInt(commands[3]);
                for(int i= 4;i<4+totalSplits;i++) {
                    perHeadAmount = 0.01d*Double.parseDouble(commands[i+totalSplits])*totalAmount;
                    Split split = splitService.createSplit(commands[i],perHeadAmount,Double.parseDouble(commands[i+totalSplits]));
                    splits.add(split);
                }
                break;
        }
        return createExpense(userId,totalAmount,splits,expenseType);
    }
}
