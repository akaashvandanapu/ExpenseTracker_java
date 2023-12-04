package pack1;

public abstract class Income {
    public String source;
    public double amount;
    public String date;

    public Income(String source, double amount, String date) {
        this.source = source;
        this.amount = amount;
        this.date = date;
    }

    public abstract void displayIncome();
}
