package pack2;

import pack1.Expense;

public class OneTimeExpense extends Expense {
    public OneTimeExpense(String description, double amount, String date) {
        super(description, amount, date);
    }

    @Override
    public void displayExpense() {
        System.out.println("One-Time Expense: " + description);
        System.out.println("Amount: " + amount);
        System.out.println("Date: " + date);
    }
}
