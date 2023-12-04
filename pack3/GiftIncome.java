package pack3;

import pack1.Income;

public class GiftIncome extends Income {

    public GiftIncome(String source, double amount, String date) {
        super(source, amount, date);
    }

    @Override
    public void displayIncome() {
        System.out.println("Gift Income from: " + source);
        System.out.println("Amount: " + amount);
        System.out.println("Date: " + date);
    }
}

