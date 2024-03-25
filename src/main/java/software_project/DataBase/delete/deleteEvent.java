package software_project.DataBase.delete;

import software_project.EventManagement.EventService;


import java.sql.Connection;
import java.sql.PreparedStatement;


public class deleteEvent {
    private String status;
    private Connection conn;

    public deleteEvent(Connection conn) {
        this.conn = conn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean delete_event(EventService ES) {

        try {
            conn.setAutoCommit(false);
            String query = "delete from \"Event_Service\" where \"Id\" = ?;";
            try (PreparedStatement preparedStmt = conn.prepareStatement(query)) {
                preparedStmt.setInt(1, ES.getId());
                preparedStmt.execute();
            }
            setStatus("Event service deleted successfully");
            conn.commit();
            return true;
        } catch (Exception e) {
            setStatus("Couldn't delete the event service");

            return false;
        }

    }


    public boolean delete_user(String username) {

        try {
            conn.setAutoCommit(false);
            String query = "delete from \"users\" where \"User_Name\" = ?;";
            try (PreparedStatement preparedStmt = conn.prepareStatement(query)) {
                preparedStmt.setString(1, username);
                preparedStmt.execute();
            }
            setStatus("user deleted successfully");
            conn.commit();
            return true;
        } catch (Exception e) {
            setStatus("Couldn't delete the user");

            return false;
        }

    }



}
