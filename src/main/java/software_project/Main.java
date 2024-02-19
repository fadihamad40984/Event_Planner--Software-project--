package software_project;


import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {


    public static void main(String[] args) throws SQLException {

        database.connection();

        try (Connection connection = database.connection;
             Statement statement = connection.createStatement()) {
            String sqlQuery = "SELECT * FROM admin";
            try (ResultSet resultSet = statement.executeQuery(sqlQuery)) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");

                    System.out.println("Name: " + name + ", Email: " + email);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        } finally {
            try {
                if (database.connection != null) {
                    database.connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }

    }
}