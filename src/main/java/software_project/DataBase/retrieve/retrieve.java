package software_project.DataBase.retrieve;

import software_project.EventManagement.EventService;
import software_project.UserManagement.User;
import software_project.helper.Generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class retrieve {

    private Connection con;
    private String status;

    public retrieve(Connection con) {
        this.con = con;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<EventService> selectEventServicesOfParticularPlace(String place_name) {
        List<EventService> EventServices = new ArrayList<>();
        Statement stmt = null;
        try {

            String query = "SELECT \"iD\" FROM Place where \"Name\" = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1,place_name);
            ResultSet rs = preparedStmt.executeQuery(query);
            int place_id = 0;
            while (rs != null && rs.next())
                place_id = rs.getInt("iD");


            String query2 = "SELECT \"ID_EventService\" FROM Place_EventServices where \"ID_Place\" = " + place_id;
            stmt = con.createStatement();
            ResultSet rs2 = stmt.executeQuery(query2);

            List<Integer> ids = null;
            while (rs2 != null && rs2.next()) {
                ids.add(rs2.getInt("id"));
            }
            for(int i = 0; i < ids.size() ; i++) {
                String query3 = "SELECT * FROM Event_Service where \"Id\" = " + ids.get(i);
                ResultSet rs3 = stmt.executeQuery(query3);
                while (rs3 != null && rs3.next()) {
                    EventService es = new EventService();
                    es.setId(rs3.getInt("id"));
                    es.setTitle(rs3.getString("Title"));
                    es.setDetails(rs3.getString("Details"));
                    es.setEventCategory(rs3.getString("Event_Category"));
                    es.setPrice(rs3.getString("Price"));
                    es.setPlace(rs3.getString("Place"));
                    es.setStartTime(rs3.getString("Start_Time"));
                    es.setEndTime(rs3.getString("End_Time"));
                    es.setBookingTime(rs3.getString("Booking_Time"));
                    EventServices.add(es);
                }
            }
            setStatus("Retrieving event services for the place successfully");
            return EventServices;
        } catch (Exception e) {
            setStatus("Error while retrieving event services for the place from database");
            return new ArrayList<>();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
            }
        }
    }
}
