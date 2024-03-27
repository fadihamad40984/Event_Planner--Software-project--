package software_project.DataBase.update;

import software_project.EventManagement.EventService;
import software_project.helper.Generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditEvent {
    private String status;
    private final Connection conn;

    public EditEvent(Connection conn) {
        this.conn = conn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }










    public boolean updateEventService(EventService es) {
        try {
            conn.setAutoCommit(false);
            String query = "update \"Event_Service\" set \"Title\"=?, \"Details\"=?, \"Event_Category\"=?, \"Price\"=?, \"Place\"=?, \"Start_Time\"=?, \"End_Time\"=?, \"Booking_Time\"=? where \"Id\"=?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt = Generator.eventStatementToPS(preparedStmt, es);
            preparedStmt.setInt(9, es.getId());
            int rowsUpdated = preparedStmt.executeUpdate();
            if (rowsUpdated > 0) {
                setStatus("Event updated successfully");
                conn.commit();
                return true;
            } else {
                setStatus("Event ID not found");
                conn.rollback();
                return false;
            }

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            return false;
        }
    }






}
