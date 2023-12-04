package pack1;

public abstract class Expense {
    public String description;
    public double amount;
    public String date;

    public Expense(String description, double amount, String date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public abstract void displayExpense();
}