package pack2;

import pack1.Expense;

public class OtherExpense extends Expense {
    public OtherExpense(String description, double amount, String date) {
        super(description, amount, date);
    }

    @Override
    public void displayExpense() {
        System.out.println("Other Expense: " + description);
        System.out.println("Amount: " + amount);
        System.out.println("Date: " + date);
    }
}
