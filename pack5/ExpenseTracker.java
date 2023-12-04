package pack5;

import pack2.*;
import pack3.*;
import pack4.*;

import java.util.Scanner;

public class ExpenseTracker {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ProfileManager profileManager = new ProfileManager(100);
        int loggedInProfileId = -1;
        Profile loginProfile = null;

        while (true) {
            if (loggedInProfileId == -1) {
                System.out.println("--------------------------------------------------------");
                System.out.println("Expense Tracker Menu");
                System.out.println("1. Log In");
                System.out.println("2. Sign Up");
                System.out.println("3. Exit");

                int mainChoice = checkNumber("choice");

                System.out.print("\033[H\033[2J");

                switch (mainChoice) {
                    case 1:
                        System.out.print("Enter Profile ID to Log In: \n");
                        int loginProfileId = checkNumber("Profile ID");
                        loginProfile = profileManager.getProfileById(loginProfileId);

                        if (loginProfile != null) {
                            loggedInProfileId = loginProfileId;
                            System.out.println("Log In successful!\n");
                        } else {
                            System.out.println("Profile not found. Please check the profile ID.\n");
                        }
                        break;

                    case 2:
                        System.out.println("Sign Up");
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        int age = checkNumber("age");
                        String phno = checkPhoneNumber();
                        System.out.print("Address: ");
                        String address = sc.nextLine();

                        Profile newProfile = new Profile(name, age, phno, address);
                        profileManager.addProfile(newProfile);

                        loggedInProfileId = newProfile.getProfileId();
                        loginProfile = newProfile;
                        System.out.println("Sign Up successful!\n");
                        break;

                    case 3:
                        System.out.println("Thank you for using Expense Tracker!");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please enter a valid option.\n");
                        break;
                }
            } else {
                System.out.println("Logged In Profile ID: " + loggedInProfileId);
                System.out.println("1. Profile");
                System.out.println("2. Add Expenses");
                System.out.println("3. Add Income");
                System.out.println("4. Display Expenses");
                System.out.println("5. Display Incomes");
                System.out.println("6. Log Out");

                int loggedInChoice = checkNumber("choice");

                System.out.print("\033[H\033[2J");

                switch (loggedInChoice) {
                    case 1:
                        System.out.println("Profile Menu");
                        System.out.println("1. Edit Profile");
                        System.out.println("2. Display Profile");
                        int profileChoice = checkNumber("choice");

                        switch (profileChoice) {
                            case 1:
                                System.out.print("Edit your name: ");
                                String name = sc.nextLine();
                                System.out.print("Edit your age: \n");
                                int age = checkNumber("age");
                                System.out.print("Edit your phone number: \n");
                                String phno = checkPhoneNumber();
                                System.out.print("Edit your address: ");
                                String address = sc.nextLine();

                                loginProfile = new Profile(name, age, phno, address);
                                System.out.println("Profile updated successfully.\n");
                                break;

                            case 2:
                                System.out.println("Displaying Profile:");
                                loginProfile.displayProfile();
                                break;

                            default:
                                System.out.println("Invalid choice for Profile.\n");
                                break;
                        }
                        break;

                    case 2:
                        System.out.println("Select to add your Expense:");
                        System.out.println("1. One-Time Expense");
                        System.out.println("2. Other Expense");
                        System.out.println("3. Timely Expense");
                        System.out.println("Enter any other number to backtrack");
                        int expenseTypeChoice = checkNumber("choice");

                        if (expenseTypeChoice > 3 || expenseTypeChoice < 1) {
                            System.out.println("Going back to the main menu\n");
                            break;
                        }

                        System.out.print("Description: ");
                        String description = sc.nextLine();
                        double amount = checkNumber("Amount");
                        System.out.print("Date (YYYY-MM-DD): ");
                        String date = sc.nextLine();

                        switch (expenseTypeChoice) {
                            case 1:
                                loginProfile.addExpense(new OneTimeExpense(description, amount, date));
                                break;
                            case 2:
                                loginProfile.addExpense(new OtherExpense(description, amount, date));
                                break;
                            case 3:
                                System.out.print("Frequency: ");
                                String frequency = sc.nextLine();
                                loginProfile.addExpense(new TimelyExpense(description, amount, date, frequency));
                                break;
                        }

                        System.out.println("Expense added successfully.\n");
                        break;

                    case 3:
                        System.out.println("Select Income Type:");
                        System.out.println("1. Salary Income");
                        System.out.println("2. Gift Income");
                        System.out.println("3. Other Income");
                        System.out.println("Enter any other number to backtrack");
                        int incomeTypeChoice = checkNumber("choice");

                        if (incomeTypeChoice > 3 || incomeTypeChoice < 1) {
                            System.out.println("Going back to the main menu\n");
                            break;
                        }

                        System.out.print("Source: ");
                        String source = sc.nextLine();
                        double incomeAmount = checkNumber("Amount");
                        System.out.print("Date (YYYY-MM-DD): ");
                        String incomeDate = sc.nextLine();

                        switch (incomeTypeChoice) {
                            case 1:
                                loginProfile.addIncome(new SalaryIncome(source, incomeAmount, incomeDate));
                                break;
                            case 2:
                                loginProfile.addIncome(new GiftIncome(source, incomeAmount, incomeDate));
                                break;
                            case 3:
                                loginProfile.addIncome(new OtherIncome(source, incomeAmount, incomeDate));
                                break;
                        }

                        System.out.println("Income added successfully.\n");
                        break;

                    case 4:
                    if (loggedInProfileId != -1) {
                        // Display Expenses Menu
                        System.out.println("Display Expenses Menu");
                        System.out.println("1. Display All Expenses");
                        System.out.println("2. Display Expenses by Date");
                        System.out.println("3. Display Total Amount of Expenses");
                        System.out.println("Enter any other number to backtrack");
                        int displayExpenseChoice = checkNumber("choice");

                        switch (displayExpenseChoice) {
                            case 1:
                                System.out.println("\nDisplaying All Expenses:\n");
                                for (int i = 0; i < loginProfile.getExpenseCount(); i++) {
                                    loginProfile.getExpenses()[i].displayExpense();
                                }
                                System.out.println();
                                break;
                            case 2:
                                System.out.print("Enter a date (YYYY-MM-DD): ");
                                String displayExpenseDate = sc.nextLine();
                                boolean foundExpenseByDate = false;
                                for (int i = 0; i < loginProfile.getExpenseCount(); i++) {
                                    if (loginProfile.getExpenses()[i].date.equals(displayExpenseDate)) {
                                        loginProfile.getExpenses()[i].displayExpense();
                                        foundExpenseByDate = true;
                                    }
                                }
                                if (!foundExpenseByDate) {
                                    System.out.println("No expenses found for the given date.\n");
                                }
                                break;
                            case 3:
                                double totalExpenseAmount = 0;
                                for (int i = 0; i < loginProfile.getExpenseCount(); i++) {
                                    totalExpenseAmount += loginProfile.getExpenses()[i].amount;
                                }
                                System.out.println("Total Amount of Expenses (in Rs): ₹" + totalExpenseAmount + "\n");
                                break;
                            default:
                                System.out.println("Going back to main menu");
                                break;
                        }
                    } else {
                        System.out.println("Please log in first.\n");
                    }
                    break;

                    case 5:
                        System.out.println("Display Incomes Menu");
                        System.out.println("1. Display All Incomes");
                        System.out.println("2. Display Incomes by Date");
                        System.out.println("3. Display Total Amount of Incomes");
                        System.out.println("Enter any other number to backtrack");
                        int displayIncomeChoice = checkNumber("choice");

                        switch (displayIncomeChoice) {
                            case 1:
                                System.out.println("\nDisplaying All Incomes:\n");
                                for (int i = 0; i < loginProfile.getIncomeCount(); i++) {
                                    loginProfile.getIncomes()[i].displayIncome();
                                }
                                System.out.println();
                                break;
                            case 2:
                                System.out.print("Enter a date (YYYY-MM-DD): ");
                                String displayIncomeDate = sc.nextLine();
                                boolean foundIncomeByDate = false;
                                for (int i = 0; i < loginProfile.getIncomeCount(); i++) {
                                    if (loginProfile.getIncomes()[i].date.equals(displayIncomeDate)) {
                                        loginProfile.getIncomes()[i].displayIncome();
                                        foundIncomeByDate = true;
                                    }
                                }
                                if (!foundIncomeByDate) {
                                    System.out.println("No incomes found for the given date.\n");
                                }
                                break;
                            case 3:
                                double totalIncomeAmount = 0;
                                for (int i = 0; i < loginProfile.getIncomeCount(); i++) {
                                    totalIncomeAmount += loginProfile.getIncomes()[i].amount;
                                }
                                System.out.println("Total Amount of Incomes (in Rs): ₹" + totalIncomeAmount + "\n");
                                break;
                            default:
                                System.out.println("Going back to main menu");
                                break;
                        }
                        break;

                    case 6:
                        loggedInProfileId = -1;
                        loginProfile = null;
                        System.out.println("Logged out successfully.\n");
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter a valid option.\n");
                        break;
                }
            }
        }
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private static int checkNumber(String x) {
        Scanner sc = new Scanner(System.in);
        boolean validInput = false;
        int returnInput = 0;

        while (!validInput) {
            System.out.print("Enter " + x + ": ");
            String input = sc.nextLine();

            if (isNumeric(input)) {
                returnInput = Integer.parseInt(input);
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter a valid number:");
            }
        }

        return returnInput;
    }

    private static String checkPhoneNumber() {
        Scanner sc = new Scanner(System.in);
        boolean validPhoneNumber = false;
        String phno = "";

        while (!validPhoneNumber) {
            System.out.print("Phone Number: ");
            phno = sc.nextLine();

            if (phno.matches("\\d{10}")) {
                validPhoneNumber = true;
            } else {
                System.out.println("Invalid phone number. Please enter a 10-digit numeric phone number:");
            }
        }

        return phno;
    }
}
