package software_project.DataBase.retrieve;

import software_project.UserManagement.User;
import software_project.Vendor.AVendorBooking;
import software_project.helper.Generator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class retrieveuser {
    private Connection con;
    private String status;

    public retrieveuser(Connection con) {
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
        Statement st = null;
        try {

            String query = "SELECT * FROM users " + condition;
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs != null && rs.next())
                users.add(Generator.rsToUser(rs));
            setStatus("Retrieving users successfully");
            return users;
        } catch (Exception e) {
            setStatus("Error while retrieving users from database");
            return new ArrayList<>();
        } finally {
            try {
                if (st != null) st.close();
            } catch (Exception e) {
            }
        }
    }

    public List<User> selectFromusersTable(String field, String input) {
        return selectUsersWithCondition("where " + "\"" + field + "\"" + " = \'" + input + "\';");
    }


    public List<User> findUserByUsername(String username) {
        return selectFromusersTable("User_Name", username);
    }

    public List<User> findUserByFirstName(String firstName) {
        return selectFromusersTable("First_Name", firstName);
    }

    public List<User> findUserByLastName(String lastName) {
        return selectFromusersTable("Last_Name", lastName);
    }

    public List<User> findUserByEmail(String email) {
        return selectFromusersTable("Email", email);
    }

    public List<User> findUserByPhone(String phone) {
        return selectFromusersTable("Phone", phone);
    }

    public List<User> findUserByUserType(String userType) {
        return selectFromusersTable("User_Type", userType);
    }

    public List<User> ALLUsers(){

        Statement stmt = null;
        List<User> users = new ArrayList<>();

        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM \"users\";";

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                User user = new User();
                user.setFirstName(rs.getString("First_Name"));
                user.setLastName(rs.getString("Last_Name"));
                user.setUsername(rs.getString("User_Name"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setPhoneNumber(rs.getString("Phone"));
                user.setUserType(rs.getString("User_Type"));
                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;

    }







}