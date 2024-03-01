package software_project.DataBase.update;

import software_project.EventManagement.EventService;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class editEvent {
    private String status;
    private Connection conn;

    public editEvent(Connection conn) {
        this.conn = conn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean edit_event(EventService ES) {

        try {
            conn.setAutoCommit(false);
            String query = "update Event_Service " +
                    " set \"Id\" = ?;";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, String.valueOf(ES.getId()));
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
