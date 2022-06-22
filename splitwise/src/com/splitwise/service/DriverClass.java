package com.splitwise.service;

import java.util.Scanner;

/**
* IMO this should be written under tests package
* Since this is not part of application (If we think of it as a lib)
*/
public class DriverClass {
    private static ExpenseManagerService expenseManagerService;

    public static void main(String []args) {
        expenseManagerService = new ExpenseManagerService();
        expenseManagerService.createUsers();
        Scanner sc = new Scanner(System.in);
        boolean terminateProgram = false;
        do {
            String input = sc.nextLine();
            String[] commands = input.split("\\s+");
            for(int i=0;i<commands.length;i++) {
                System.out.println(commands[i]);
            }
            switch (commands[0]) {
                case "SHOW":
                    if(commands.length==1) {
                        expenseManagerService.printBalanceOfAllUsers();
                    } else {
                        expenseManagerService.printBalanceOfUser(commands[1]);
                    }
                    break;
                default:
                    expenseManagerService.createExpenseFromInput(commands);
            }

        } while (!terminateProgram);
    }
}
