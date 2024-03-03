package software_project.DataBase.insert;

import software_project.EventManagement.EventService;
import software_project.EventManagement.Places;
import software_project.UserManagement.User;
import software_project.helper.Generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class insertData {
    private String status;
    private Connection conn;

    public insertData(Connection conn) {
        this.conn = conn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean insertUser(User user) {

        try {
            conn.setAutoCommit(false);
            String query = "insert into users values (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt = Generator.userToPS(preparedStmt, user);
            preparedStmt.execute();
            setStatus("User was inserted successfully");
            conn.commit();
            return true;
        } catch (Exception e) {
            setStatus("Couldn't insert user");

            return false;
        }

    }

    public boolean insertEventService(EventService es) {

        try {
            conn.setAutoCommit(false);
            String query = "insert into \"Event_Service\" (\"Title\", \"Details\", \"Event_Category\", \"Price\", \"Place\", \"Start_Time\", \"End_Time\" , \"Booking_Time\") values (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt = Generator.eventStatementToPS(preparedStmt, es);
            preparedStmt.execute();
            setStatus("Event added successfully");
            conn.commit();
            return true;
        } catch (Exception e) {

            return false;
        }

    }

    public boolean insertVenue(Places p) {

        try {
            conn.setAutoCommit(false);
            String query = "insert into places values (?, ?, ? );";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt = Generator.venueStatementToPS(preparedStmt, p);
            preparedStmt.execute();
            setStatus("Venue added successfully");
            conn.commit();
            return true;
        } catch (Exception e) {


            return false;
        }

    }

    }

