package pack2;

import pack1.Expense;

public class TimelyExpense extends Expense {

    public String frequency;

    public TimelyExpense(String description, double amount, String date, String frequency) {
        super(description, amount, date);
        this.frequency = frequency;
    }

    @Override
    public void displayExpense() {
        System.out.println("Timely Expense: " + description);
        System.out.println("Amount: " + amount);
        System.out.println("Date: " + date);
        System.out.println("Frequency of Expense: " + frequency);
    }
}
