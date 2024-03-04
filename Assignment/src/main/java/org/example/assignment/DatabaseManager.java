package org.example.assignment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private Connection connection;

    public DatabaseManager(String url, String username, String password) throws SQLException {
        // Establish database connection
        connection = DriverManager.getConnection(url, username, password);
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        // Execute query and return result set
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    // Add getConnection method to retrieve the connection
    public Connection getConnection() {
        return connection;
    }
}
