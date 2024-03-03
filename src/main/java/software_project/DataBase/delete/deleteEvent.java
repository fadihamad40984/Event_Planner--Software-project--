package software_project.DataBase.delete;

import software_project.EventManagement.EventService;
import software_project.UserManagement.User;
import software_project.helper.Generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

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
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,ES.getId());
            preparedStmt.execute();
            setStatus("Event service deleted successfully");
            conn.commit();
            return true;
        } catch (Exception e) {
            setStatus("Couldn't delete the event service");

            return false;
        }

    }

}
