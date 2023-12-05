package pack5;

import pack2.*;
import pack3.*;
import pack4.*;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import java.util.Scanner;

public class ExpenseTracker {

    private static int loggedInProfileId = -1;
    private static Profile loginProfile = null;
    private final static Scanner sc = new Scanner(System.in);
    private final static ProfileManager profileManager = new ProfileManager(100);
    private static final String PROFILE_FILE_PATH = "C:/java-text-file-handling/Expense-tracker-file-handling/data.txt";

    public static void main(String[] args) {

        try {

        while (true) {
            if (loggedInProfileId == -1) {
                System.out.println("--------------------------------------------------------");
                System.out.println("Expense Tracker Menu");
                System.out.println("1. Log In");
                System.out.println("2. Sign Up");
                System.out.println("3. Exit");

                int mainChoice = checkNumber("choice");

                System.out.print("\033[H\033[2J");//clear screen

                switch (mainChoice) {
                    case 1:
                        System.out.print("Enter Profile ID to Log In: \n");
                        int loginProfileId = checkNumber("Profile ID");
                        loginProfile = login(loginProfileId);

                        if (loginProfile != null) {
                            loggedInProfileId = loginProfileId;
                            System.out.println("Log In successful!\n");
                        } else {
                            System.out.println("Profile not found. Please check the profile ID.\n");
                        }
                        break;

                    case 2:
                        signUp();
                        System.out.println("Sign Up successful!\n");
                        break;

                    case 3:
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please enter a valid option.\n");
                        break;
                }
            } else {
                System.out.println("Logged in Profile Details \nProfile ID: " + loggedInProfileId);
                System.out.println("Name: " + loginProfile.getName() + "\n");
                System.out.println("1. Profile");
                System.out.println("2. Display All Profiles from file ");
                System.out.println("3. Add Expenses");
                System.out.println("4. Add Income");
                System.out.println("5. Display Expenses");
                System.out.println("6. Display Incomes");
                System.out.println("7. Surprise");
                System.out.println("8. Log Out");

                int loggedInChoice = checkNumber("choice");

                System.out.print("\033[H\033[2J");//clear screen

                switch (loggedInChoice) {
                    case 1:
                        System.out.println("\nProfile Menu");
                        System.out.println("1. Edit Profile");
                        System.out.println("2. Display Profile");
                        int profileChoice = checkNumber("choice");

                        switch (profileChoice) {
                            case 1:
                                editProfile();
                                break;
                            case 2:
                                displayLoggedInProfile();
                                break;
                            default:
                                System.out.println("Invalid choice for Profile.\n");
                                break;
                        }
                        break;

                    case 2:
                        displayAllProfilesFromFile();
                        break;

                    case 3:
                        insertExpenseIntoDatabase();
                        break;

                    case 4:
                        addIncome(loginProfile);
                        break;

                    case 5:
                        displayExpensesMenu(loginProfile, loggedInProfileId);
                        break;

                    case 6:
                        displayIncomesMenu(loginProfile);
                        break;

                    case 7:
                        throw new UserCreatedException("\nThis is a user-created exception which is thrown and terminates the program");

                    case 8:
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
        finally{
            System.out.println("This message is from finally block: \nThank you for using Expense Tracker, Visit again!");
        }
    }
    private static Profile login(int loginProfileId) {
        Profile retrievedProfile = Profile.retrieveProfileFromDatabase(loginProfileId);

        if (retrievedProfile != null) {
            System.out.println("Log In successful!\n");
        } else {
            System.out.println("Profile not found. Please check the profile ID.\n");
        }

        return retrievedProfile;
    }

    private static void signUp() {
        System.out.println("Sign Up");
        System.out.print("Name: ");
        String name = sc.nextLine();
        int age = checkNumber("age");
        String phno = checkPhoneNumber();
        System.out.print("Address: ");
        String address = sc.nextLine();

        Profile newProfile = new Profile(name, age, phno, address);
        profileManager.addProfile(newProfile);
        newProfile.insertProfileIntoDatabase();

        loggedInProfileId = newProfile.getProfileId();
        loginProfile = newProfile;

        // Write the new profile to the file
        try (FileWriter fileWriter = new FileWriter(PROFILE_FILE_PATH, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            printWriter.println("profileID:" + loggedInProfileId + ", name:" + name + ", age:" + age +
                    ", phone number:" + phno + ", address:" + address);

        } catch (IOException e) {
            System.out.println("caught: "+ e);
        }
    }


    private static void editProfile() {
        System.out.print("Edit your name: ");
        String name = sc.nextLine();
        System.out.print("Edit your age: ");
        int age = checkNumber("age");
        System.out.print("Edit your phone number: ");
        String phno = checkPhoneNumber();
        System.out.print("Edit your address: ");
        String address = sc.nextLine();

        loginProfile.setName(name);
        loginProfile.setAge(age);
        loginProfile.setPhno(phno);
        loginProfile.setAddress(address);

        // Update profile in the database using JDBC
        loginProfile.updateProfileInDatabase(loggedInProfileId);

        System.out.println("Profile updated successfully.\n");
    }

    private static void displayLoggedInProfile() {
        // Retrieve and display profile information from the database using JDBC
        loginProfile = Profile.retrieveProfileFromDatabase(loggedInProfileId);

        if (loginProfile != null) {
            System.out.println("Displaying Profile:");
            loginProfile.displayProfile();
        } else {
            System.out.println("Profile not found.\n");
        }
    }

    private static void displayAllProfilesFromFile() {
        System.out.println("Displaying all profile from file: \n");
        // Display all profiles in the specified format
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PROFILE_FILE_PATH))) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Profile not found.\n");
        }
    }

    private static void insertExpenseIntoDatabase() {
        int expenseChoice;
        System.out.println("Select to add your Expense:");
        System.out.println("1. One-Time Expense");
        System.out.println("2. Other Expense");
        System.out.println("3. Timely Expense");
        System.out.println("Enter any other number to backtrack");
        expenseChoice = checkNumber("choice");

        if (expenseChoice > 3 || expenseChoice < 1) {
            System.out.println("Going back to the main menu\n");
            return;
        }

        System.out.print("Description: ");
        String description = sc.nextLine();
        double amount = checkNumber("Amount");
        System.out.print("Date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        switch (expenseChoice) {
            case 1:
                OneTimeExpense oneTimeExpense = new OneTimeExpense(description, amount, date);
                loginProfile.insertExpenseIntoDatabase(oneTimeExpense, "One-Time Expense", null, loggedInProfileId);
                break;
            case 2:
                OtherExpense otherExpense = new OtherExpense(description, amount, date);
                loginProfile.insertExpenseIntoDatabase(otherExpense, "Other Expense", null, loggedInProfileId);
                break;
            case 3:
                System.out.print("Frequency: ");
                String frequency = sc.nextLine();
                TimelyExpense timelyExpense = new TimelyExpense(description, amount, date, frequency);
                loginProfile.insertExpenseIntoDatabase(timelyExpense, "Timely Expense", frequency, loggedInProfileId);
                break;
        }

        System.out.println("Expense added successfully.\n");
    }

    private static void addIncome(Profile loginProfile) {
        System.out.println("Select Income Type:");
        System.out.println("1. Salary Income");
        System.out.println("2. Gift Income");
        System.out.println("3. Other Income");
        System.out.println("Enter any other number to backtrack");
        int incomeTypeChoice = checkNumber("choice");

        if (incomeTypeChoice > 3 || incomeTypeChoice < 1) {
            System.out.println("Going back to the main menu\n");
            return;
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
    }

    private static void displayExpensesMenu(Profile loginProfile, int loggedInProfileId) {
        if (loggedInProfileId != -1) {
            // Display Expenses Menu
            System.out.println("\nDisplay Expenses Menu");
            System.out.println("1. Display All Expenses");
            System.out.println("2. Display Expenses by Date");
            System.out.println("3. Display Total Amount of Expenses");
            System.out.println("Enter any other number to backtrack");
            int displayExpenseChoice = checkNumber("choice");

            switch (displayExpenseChoice) {
                case 1:
                    System.out.println("\nDisplaying All Expenses from the Database:\n");
                    loginProfile.displayAllExpensesFromDatabase(loggedInProfileId);
                    System.out.println();
                    break;
                case 2:
                    System.out.print("Enter a date (YYYY-MM-DD): ");
                    String displayExpenseDate = sc.nextLine();
                    System.out.println("\nDisplaying Expenses by Date from the Database:\n");
                    loginProfile.displayExpenseByDateFromDatabase(displayExpenseDate, loggedInProfileId);
                    break;
                case 3:
                    System.out.println("\nDisplaying Total Amount of Expenses from the Database:\n");
                    loginProfile.displayTotalExpenseFromDatabase(loggedInProfileId);
                    break;
                default:
                    System.out.println("Going back to main menu");
                    break;
            }
        } else {
            System.out.println("Please log in first.\n");
        }
    }

    private static void displayIncomesMenu(Profile loginProfile) {
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
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private static int checkNumber(String x) {
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
