package org.example.Homework29;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS employees (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "age INTEGER, " +
                "position VARCHAR(100), " +
                "salary FLOAT);";

        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
             statement.execute(createTableSQL);
             System.out.println("Database initialized.");
        } catch (SQLException e) {
             System.out.println("Error: " + e.getMessage());
        }
    }
}
