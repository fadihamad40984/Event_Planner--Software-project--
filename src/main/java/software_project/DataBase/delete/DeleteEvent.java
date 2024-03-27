package software_project.DataBase.delete;
import software_project.EventManagement.EventService;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class DeleteEvent {
    private String status;
    private final Connection conn;

    public DeleteEvent(Connection conn) {
        this.conn = conn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void deleteEvent(EventService es) {

        try {
            conn.setAutoCommit(false);
            String query = "delete from \"Event_Service\" where \"Id\" = ?;";

            try (PreparedStatement preparedStmt = conn.prepareStatement(query)) {
                preparedStmt.setInt(1, es.getId());
                preparedStmt.execute();
            }
            setStatus("Event service deleted successfully");
            conn.commit();
        } catch (Exception e) {
            setStatus("Couldn't delete the event service");

        }



    }


    public boolean deleteUser(String username) {

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
