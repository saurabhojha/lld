package com.splitwise.models;

import com.splitwise.enums.ExpenseType;
import lombok.Data;

import java.util.List;

@Data
public class Expense {
    private String paidByUserId;
    private double totalAmount;
    private List<Split> splits;
    private ExpenseType expenseType;

}
