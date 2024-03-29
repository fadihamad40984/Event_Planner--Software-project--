package software_project.DataBase.retrieve;

import software_project.UserManagement.User;
import software_project.helper.Generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM users " + condition;
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs != null && rs.next())
                users.add(Generator.rsToUser(rs));
            setStatus("Retrieving users successfully");
        } catch (Exception e) {
            setStatus("Error while retrieving users from database");
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception e) {
                setStatus("Exception While Retrieve Data");
            }
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