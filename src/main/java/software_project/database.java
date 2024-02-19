package software_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {

    public static Connection connection;

    public static void connection(){
        String url = "jdbc:postgresql://localhost:5432/Event_Planner";
        String username = "postgres";
        String password = "admin";

        try {
             connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException ignored) {

        }
    }
}
