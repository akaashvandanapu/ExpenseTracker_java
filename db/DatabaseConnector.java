package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    static final String DB_URL = "jdbc:mysql://localhost:3306/akaashdb";
    static final String USER = "root";
    static final String PWD = "Qxevtn@95";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PWD);
    }
}
