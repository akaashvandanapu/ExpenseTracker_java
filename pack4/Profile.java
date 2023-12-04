// File: Pack4/Profile.java
package pack4;

import java.util.Random;
import pack1.*;

public class Profile {
    private int profileId;
    private String name;
    private int age;
    private String phno;
    private String address;
    private Expense[] expenses;
    private Income[] incomes;
    private int expenseCount;
    private int incomeCount;
    private static final int MAX_EXPENSES = 100;
    private static final int MAX_INCOMES = 100;

    public Profile(String name, int age, String phno, String address) {
        this.profileId = generateRandomProfileId();
        this.name = name;
        this.age = age;
        this.phno = phno;
        this.address = address;
        this.expenses = new Expense[MAX_EXPENSES];
        this.incomes = new Income[MAX_INCOMES];
        this.expenseCount = 0;
        this.incomeCount = 0;
    }

    private int generateRandomProfileId() {
        Random random = new Random();
        return random.nextInt(1000000); // Adjust the range as needed
    }

    public int getProfileId() {
        return profileId;
    }

    public String getName() {
        return name;
    }

    public void addExpense(Expense expense) {
        if (expenseCount < MAX_EXPENSES) {
            expenses[expenseCount] = expense;
            expenseCount++;
        } else {
            System.out.println("Cannot add more expenses. Maximum limit reached.");
        }
    }

    public void addIncome(Income income) {
        if (incomeCount < MAX_INCOMES) {
            incomes[incomeCount] = income;
            incomeCount++;
        } else {
            System.out.println("Cannot add more incomes. Maximum limit reached.");
        }
    }

    public void displayProfile() {
        System.out.println("Profile ID: " + profileId);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Phone Number: " + phno);
        System.out.println("Address: " + address);

        System.out.println("\nExpenses:");
        for (int i = 0; i < expenseCount; i++) {
            expenses[i].displayExpense();
        }

        System.out.println("\nIncomes:");
        for (int i = 0; i < incomeCount; i++) {
            incomes[i].displayIncome();
        }
    }

    public Income[] getIncomes() {
        return incomes;
    }

    public int getIncomeCount() {
        return incomeCount;
    }

     public Expense[] getExpenses() {
        return expenses;
    }

    public int getExpenseCount() {
        return expenseCount;
    }
    

    // Method to resize the incomes array
    private void expandIncomeArray() {
        int newCapacity = incomes.length * 2;
        Income[] newArray = new Income[newCapacity];
        System.arraycopy(incomes, 0, newArray, 0, incomes.length);
        incomes = newArray;
    }
}

