package software_project.DataBase.retrieve;

import software_project.UserManagement.User;
import software_project.helper.Generator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Retrieveuser {
    private Connection con;
    private String status;

    public Retrieveuser(Connection con) {
        this.con = con;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> selectUsersWithCondition(String condition) {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM users WHERE " + condition;

        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE " + condition)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(Generator.rsToUser(rs));
            }

            setStatus("Retrieving users successfully");

        } catch (SQLException e) {
            setStatus("Error while retrieving users from database");
        }

        return users;
    }


    public List<User> selectFromusersTable(String field, String input) {
        return selectUsersWithCondition("where " + "\"" + field + "\"" + " = \'" + input + "\';");
    }


    public List<User> findUserByUsername(String username) {
        return selectFromusersTable("User_Name", username);
    }





}