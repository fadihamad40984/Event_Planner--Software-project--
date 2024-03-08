package software_project.DataBase.insert;

import software_project.DataBase.DB_Connection;
import software_project.DataBase.retrieve.retrieve;
import software_project.EventManagement.Event;
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

    retrieve retrive;

    public insertData(Connection conn) {
        this.conn = conn;
        retrive = new retrieve(conn);
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

    public boolean insertEventService_Place(EventService es) {
        try {
            conn.setAutoCommit(false);
            String query = "insert into \"Place_EventServices\" values (?, ?);";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,retrive.retriveplace(es.getPlace()).getId());
            preparedStmt.setInt(2,retrive.retriveeventid(es.getTitle()));

            preparedStmt.execute();
            setStatus("Event_Place added successfully");
            conn.commit();
            return true;
        } catch (Exception e) {

            return false;
        }

    }

    public boolean insertVenue(Places p) {

        try {
            conn.setAutoCommit(false);
            String query = "insert into \"Places\" (\"Name\", \"Capacity\", \"Amenities\") values (?, ?, ? );";
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

    public boolean insertEvent(Event e) {
        try {
            conn.setAutoCommit(false);
            String query = "insert into \"Event\" (\"EventService_id\",\"Date\", \"Time\", \"Description\", \"Attendee_Count\") values (?,?, ?, ?, ?);";//id is serial
            String query2 = "insert into \"Guests\" (\"Event_id\",\"Guest_Name\") values (?,?)";//guest id is serial
            String query3 = "insert into \"images\" (\"Event_id\",\"Image_Path\") values (?,?)";//image id is serial

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt = Generator.eventBookingStatementToPS(preparedStmt, e);
            preparedStmt.execute();

            for(String s: e.getGuestList()) {
                PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
                preparedStmt2 = Generator.guestListStatementToPS(preparedStmt2, e, s);
                preparedStmt2.execute();
            }

            for(String path: e.getImages()) {
                PreparedStatement preparedStmt3 = conn.prepareStatement(query3);
                preparedStmt3 = Generator.imageStatementToPS(preparedStmt3, e, path);
                preparedStmt3.execute();
            }

            setStatus("Event added successfully");
            conn.commit();
            return true;
        } catch (Exception exception) {

            return false;
        }
    }



}

