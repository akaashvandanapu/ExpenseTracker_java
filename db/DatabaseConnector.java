package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

//loading and registering the driver, however in JDBC 4.0 it's not required
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("caught: "+ e);
        }
    }

    static final String DB_URL = "jdbc:mysql://localhost:3306/akaashdb";
    static final String USER = "root";
    static final String PWD = "Qxevtn@95";

    // Connecting the driver and returning a connection object
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PWD);
    }
}
