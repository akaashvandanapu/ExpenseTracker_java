// File: Pack4/Profile.java
package pack4;

import java.util.Random;

import db.DatabaseConnector;
import pack1.*;

// Import JDBC-related classes for database operations
// Connection class to store the connection object
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
// To store the ResultSet which is returned by sql statements
import java.sql.ResultSet;
import java.sql.SQLException;

public class Profile {
    private final int profileId;
    private String name;
    private int age;
    private String phno;
    private String address;
    private final Income[] incomes;
    private int incomeCount;
    private static final int MAX_INCOMES = 100;

    public Profile(String name, int age, String phno, String address) {
        this.profileId = generateRandomProfileId();
        this.name = name;
        this.age = age;
        this.phno = phno;
        this.address = address;
        this.incomes = new Income[MAX_INCOMES];
        this.incomeCount = 0;
    }

    private int generateRandomProfileId() {
        Random random = new Random();
        return random.nextInt(1000000);
    }//used package random

    public int getProfileId() {
        return profileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public void setAddress(String address) {
        this.address = address;
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
    }

    public Income[] getIncomes() {
        return incomes;
    }

    public int getIncomeCount() {
        return incomeCount;
    }

    public void insertProfileIntoDatabase() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "INSERT INTO profiles (profile_id, name, age, phno, address) " +
                    "VALUES (" + this.profileId + ", '" + this.name + "', " + this.age + ", '" + this.phno + "', '" + this.address + "')";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println("caught: " + e);
        }
    }


    public static Profile retrieveProfileFromDatabase(int profileId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT * FROM profiles WHERE profile_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, profileId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    String phno = resultSet.getString("phno");
                    String address = resultSet.getString("address");

                    return new Profile(name, age, phno, address);
                }
            }
        } catch (SQLException e) {
            System.out.println("caught: "+ e);
        }
        return null;
    }

    public void updateProfileInDatabase(int profileId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String updateQuery = "UPDATE profiles " +
                    "SET name=?, age=?, phno=?, address=? " +
                    "WHERE profile_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, this.name);   // Assuming this.name is a class variable
                preparedStatement.setInt(2, this.age);
                preparedStatement.setString(3, this.phno);
                preparedStatement.setString(4, this.address);
                preparedStatement.setInt(5, profileId);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("caught: " + e);
        }
    }


    public void insertExpenseIntoDatabase(Expense expense, String typeOfExpense, String frequency, int profileId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "INSERT INTO expenses (profile_id, typeofexpense, description, amount, date, frequency) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, profileId);
                preparedStatement.setString(2, typeOfExpense); // You need to set the correct type
                preparedStatement.setString(3, expense.description);
                preparedStatement.setDouble(4, expense.amount);
                preparedStatement.setString(5, expense.date);
                preparedStatement.setString(6, frequency); // You need to set the correct frequency for timely expense

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("caught: "+ e);
        }
    }

    public void displayAllExpensesFromDatabase(int profileId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT * FROM expenses WHERE profile_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, profileId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Retrieve and display expense details
                        System.out.println("Type: " + resultSet.getString("typeofexpense"));
                        System.out.println("Description: " + resultSet.getString("description"));
                        System.out.println("Amount: " + resultSet.getDouble("amount"));
                        System.out.println("Date: " + resultSet.getString("date"));
                        System.out.println("Frequency: " + resultSet.getString("frequency"));
                        System.out.println("--------------");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("caught: "+ e);
        }
    }

    // Retrieve and display expenses by date from the database
    public void displayExpenseByDateFromDatabase(String date, int profileId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT * FROM expenses WHERE profile_id = ? AND date = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, profileId);
                preparedStatement.setString(2, date);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Retrieve and display expense details
                        System.out.println("Type: " + resultSet.getString("typeofexpense"));
                        System.out.println("Description: " + resultSet.getString("description"));
                        System.out.println("Amount: " + resultSet.getDouble("amount"));
                        System.out.println("Date: " + resultSet.getString("date"));
                        System.out.println("Frequency: " + resultSet.getString("frequency"));
                        System.out.println("--------------");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("caught: "+ e);
        }
    }

    // Retrieve and display total expenses from the database
    public void displayTotalExpenseFromDatabase(int profileId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT SUM(amount) as totalExpense FROM expenses WHERE profile_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, profileId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        double totalExpenseAmount = resultSet.getDouble("totalExpense");
                        System.out.println("Total Amount of Expenses (in Rs): ₹" + totalExpenseAmount + "\n");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("caught: "+ e);
        }
    }
}
