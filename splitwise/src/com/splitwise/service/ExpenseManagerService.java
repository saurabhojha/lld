package com.splitwise.service;

import com.splitwise.enums.ExpenseType;
import com.splitwise.models.Expense;
import com.splitwise.models.Split;
import com.splitwise.models.User;

import java.nio.file.LinkPermission;
import java.util.*;

public class ExpenseManagerService {

    private Map<String, User> userMapping;
    private Map<String,Map<String,Double>> balanceMapping;
    private List<Expense> expenses;

    private UserService userService;



    private ExpenseService expenseService;

    public ExpenseManagerService() {
        userMapping = new HashMap<>();
        balanceMapping = new HashMap<>();
        expenses = new ArrayList<>();
        // Seems like you forgot your old friend
        // They call it Dependency Injection xD
        userService = new UserService();
        expenseService = new ExpenseService();
    }

    public void createUsers() {
        User user1 = userService.createUser("Saurabh","sko","123","u1");
        User user2 = userService.createUser("Harman","hps","123","u2");
        User user3 = userService.createUser("Pkk","pkk","123","u3");
        User user4 = userService.createUser("Vaibhav","vv","123","u4");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        for(User user:users) {
            // In a utils, make a transformer:
            /*
             public static Map<String, IndexedObject> transformToMapById(
                Collection<IndexedObject> idObjects,
                Function<IndexedObject, String> idGetter
             ){
                return idObjects.stream()
                .collect(Collectors.toMap(idObject -> idGetter.apply(idObject), 
                            identity()));
             }

            */
            userMapping.put(user.getId(),user);
            balanceMapping.put(user.getId(),new HashMap<>());
        }
    }

    private void printBalance(String userId1,String userId2,double amount) {
        User user1 = userMapping.get(userId1);
        User user2 = userMapping.get(userId2);
        String message = "User %s owes user %s amount: %s";
        if(amount<0) {
            System.out.println(String.format(message,user1.getName(),user2.getName(),amount));
        } else {
            System.out.println(String.format(message,user2.getName(),user1.getName(),amount));
        }
    }

    public void printBalanceOfUser(String userId) {
        Map<String,Double> userBalance = balanceMapping.get(userId);
        userBalance.entrySet()
                .stream()
                // Could user .filter(Predicate<T>) here instead
                .forEach(userOwingBalance -> {
                    printBalance(userId,userOwingBalance.getKey(),userOwingBalance.getValue());
                });
    }

    public void printBalanceOfAllUsers() {
        balanceMapping.entrySet()
                .forEach(userBalanceMapping -> {
                    printBalanceOfUser(userBalanceMapping.getKey());
                });
    }

    public void addExpense(Expense expense) {
        if(expenseService.validateExpense(expense)) {
            expenses.add(expense);
            // Should be another method `doSplit(...)`
            for(Split split:expense.getSplits()) {
                updateBalanceMapping(expense.getPaidByUserId(),split.getUserId(),split.getAmount());
                updateBalanceMapping(split.getUserId(),expense.getPaidByUserId(),-1*split.getAmount());
            }
        }
    }

    public void updateBalanceMapping(String paidByUserId,String owedByUserId,double amount) {
        if(!balanceMapping.get(paidByUserId).containsKey(owedByUserId)) {
            balanceMapping.get(paidByUserId).put(owedByUserId,0.0);
        }
        double newBalance = balanceMapping.get(paidByUserId).get(owedByUserId);
        newBalance +=amount;
        balanceMapping.get(paidByUserId).put(owedByUserId,newBalance);
    }

    public void createExpenseFromInput(String []commands) {
        Expense expense = this.expenseService.createExpenseFromInput(commands);
        addExpense(expense);
    }
}
