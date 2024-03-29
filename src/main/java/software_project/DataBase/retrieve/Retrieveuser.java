package software_project.DataBase.retrieve;

import software_project.UserManagement.User;
import software_project.helper.Generator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Retrieveuser {
    private final Connection con;
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

    public List<User> findUserByUsername(String userName) throws SQLException {
        List<User> users = new ArrayList<>();
        ResultSet rs = null;
        String query = "SELECT * FROM \"users\" where \"User_Name\" = ?;";


        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, userName);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                users.add(Generator.rsToUser(rs));
            }
            setStatus("Retrieving users successfully");

            rs.close();

            return users;

        }
       catch (SQLException e) {
            setStatus("Error while retrieving users from database");

        }
        return users;
    }






}