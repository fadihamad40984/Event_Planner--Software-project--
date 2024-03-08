package software_project.DataBase.retrieve;

import software_project.EventManagement.Event;
import software_project.EventManagement.EventService;
import software_project.EventManagement.Places;
import software_project.UserManagement.User;
import software_project.helper.Generator;

import java.sql.*;
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
            stmt = con.createStatement();
            String query = "SELECT \"iD\" FROM \"Places\" where \"Name\" = \'" + place_name + "\';";

            ResultSet rs = stmt.executeQuery(query);
            int place_id = 0;
            while (rs.next())
                place_id = rs.getInt("iD");


            String query2 = "SELECT \"ID_EventService\" FROM \"Place_EventServices\" where \"ID_Place\" = " + place_id + ";";

            ResultSet rs2 = stmt.executeQuery(query2);

            List<Integer> ids = new ArrayList<>();
            while (rs2.next()) {
                ids.add(rs2.getInt("ID_EventService"));
            }
            for (int i = 0; i < ids.size(); i++) {
                String query3 = "SELECT * FROM \"Event_Service\" where \"Id\" = " + ids.get(i) + ";";
                ResultSet rs3 = stmt.executeQuery(query3);
                while (rs3 != null && rs3.next()) {
                    EventService es = new EventService();
                    es.setId(rs3.getInt("Id"));
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


    public Places retriveplace(String place_name) throws SQLException {
        Statement stmt = null;


        Places place = new Places();

        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM \"Places\" where \"Name\" = \'" + place_name + "\';";

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                place.setId(rs.getInt("iD"));
                place.setName(rs.getString("Name"));
                place.setCapacity(rs.getString("Capacity"));
                place.setAmenities(rs.getString("Amenities"));

            }

            return place;



        } catch (SQLException e) {
            setStatus("Error while retrieving placeID for the place from database");

        }
        return place;
    }





    public int retriveeventid(String title) throws SQLException {
        Statement stmt = null;
        int event_id = 0;

        try {
            stmt = con.createStatement();
            String query = "SELECT \"Id\" FROM \"Event_Service\" where \"Title\" = \'" + title + "\';";

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
                event_id = rs.getInt("Id");
            return event_id;



        } catch (SQLException e) {
            setStatus("Error while retrieving event_id for the EventServices from database");

        }
        return event_id;
    }


    public List<EventService> retrieveAllEventServices()
    {
        Statement stmt = null;

        List<EventService> eventServices = new ArrayList<>();
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM \"Event_Service\";";

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                EventService eventService = new EventService(rs.getString(2) , rs.getString(3) ,rs.getString(4) , rs.getString(5),rs.getString(6) , rs.getString(7) ,rs.getString(8),rs.getString(9));
                eventService.setId(rs.getInt(1));
                eventServices.add(eventService);

            }

        } catch (SQLException e) {
            setStatus("Error while retrieving EventServices from database");

        }

        return  eventServices;

    }

    public EventService selectEventServicesOfParticularName(String serviceTitle) throws SQLException {
        Statement stmt = null;
        EventService es = new EventService();
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM \"Event_Service\" where \"Name\" = \'" + serviceTitle + "\';";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                es.setId(rs.getInt("Id"));
                es.setTitle(rs.getString("Title"));
                es.setDetails(rs.getString("Details"));
                es.setEventCategory(rs.getString("Event_Category"));
                es.setPrice(rs.getString("Price"));
                es.setPlace(rs.getString("Place"));
                es.setStartTime(rs.getString("Start_Time"));
                es.setEndTime(rs.getString("End_Time"));
                es.setBookingTime(rs.getString("Booking_Time"));
            }

            setStatus("Retrieving event successfully");
            return es;

        } catch (Exception e) {
            setStatus("Error while retrieving event from database");
            return es;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
            }
        }
    }

    public List<Event> selectEventOfParticularDateAndServiceId(String date, int service_id) {
        List<Event> Events = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            String query ="SELECT * FROM \"Event\" where \"Date\" = \'" + date + "\' and \"event_service_id\" = " + service_id + ";";

            String query1 = "SELECT \"Title\" FROM \"Event_Service\" where \"Id\" = " + service_id +";";

            ResultSet rs1 = stmt.executeQuery(query1);
            String service_title = "";
            while (rs1.next()) {
                service_title = rs1.getString("Title");
            }

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Event e = new Event(con);
                e.setId(rs.getInt("id"));
                e.setServiceId(rs.getInt("event_service_id"));
                e.setServiceTitle(service_title);
                e.setDate(rs.getString("Date"));
                e.setTime(rs.getString("Time"));
                e.setDescription(rs.getString("Description"));
                e.setAttendeeCount(String.valueOf(rs.getInt("Attendee_Count")));
                Events.add(e);
            }

            setStatus("Retrieving events for the given date successfully");
            return Events;
        } catch (Exception e) {
            setStatus("Error while retrieving events for the date from database");
            return Events;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
            }
        }
    }

}
