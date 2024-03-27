package software_project.DataBase.retrieve;

import software_project.EventManagement.Event;
import software_project.EventManagement.EventService;
import software_project.EventManagement.Places;
import software_project.Vendor.AVendorBooking;
import software_project.Vendor.VendorService;
import software_project.helper.Generator;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

public class Retrieve {

    public static final String PRICE = "Price";
    public static final String TITLE = "Title";
    public static final String EVENT_ID = "Event_id";
    public static final String EVENT_SERVICE_ID = "EventService_id";
    public static final String DESCRIPTION = "Description";
    private final Connection con;
    private String status;

    public Retrieve(Connection con) {
        this.con = con;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<EventService> selectEventServicesOfParticularPlace(String placename) {
        List<EventService> eventServices = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            String query = "SELECT \"iD\" FROM \"Places\" where \"Name\" = \'" + placename + "\';";

            ResultSet rs = stmt.executeQuery(query);
            int placeid = 0;
            while (rs.next())
                placeid = rs.getInt("iD");


            String query2 = "SELECT \"ID_EventService\" FROM \"Place_EventServices\" where \"ID_Place\" = " + placeid + ";";

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
                    es.setTitle(rs3.getString(TITLE));
                    es.setDetails(rs3.getString("Details"));
                    es.setEventCategory(rs3.getString("Event_Category"));
                    es.setPrice(rs3.getString(PRICE));
                    es.setPlace(rs3.getString("Place"));
                    es.setStartTime(rs3.getString("Start_Time"));
                    es.setEndTime(rs3.getString("End_Time"));
                    es.setBookingTime(rs3.getString("Booking_Time"));
                    eventServices.add(es);
                }
            }
            setStatus("Retrieving event services for the place successfully");
            return eventServices;
        } catch (Exception e) {
            setStatus("Error while retrieving event services for the place from database");
            return new ArrayList<>();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public Places retriveplace(String placename) throws SQLException {
        Statement stmt = null;


        Places place = new Places();

        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM \"Places\" where \"Name\" = \'" + placename + "\';";

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
        finally {
            if(stmt!=null)
            {
                stmt.close();
            }
        }
        return place;
    }


    public static int retriveeventIID(Connection con2) throws SQLException {
        Statement stmt = null;
        int eventid = 0;


        try {
            stmt = con2.createStatement();
            stmt.execute("SELECT nextval('public.\"Event_Event_id_seq\"');");

            String query = "SELECT currval('public.\"Event_Event_id_seq\"') as \"ii\";";

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
                eventid = rs.getInt("ii");
            return eventid-1;



        } catch (SQLException e) {

            e.printStackTrace();
        }
       finally {
            if(stmt!=null)
            {
                stmt.close();
            }
        }
        return eventid-1;
    }



    public int retriveeventid(String title) throws SQLException {
        Statement stmt = null;
        int eventid = 0;

        try {
            stmt = con.createStatement();
            String query = "SELECT \"Id\" FROM \"Event_Service\" where \"Title\" = \'" + title + "\';";

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
                eventid = rs.getInt("Id");
            return eventid;



        } catch (SQLException e) {
            setStatus("Error while retrieving event_id for the EventServices from database");

        }
        finally {
            if(stmt!=null)
            {
                stmt.close();
            }
        }
        return eventid;
    }


    public List<EventService> retrieveAllEventServices() throws SQLException {
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
        finally {
            if(stmt!=null)
            {
                stmt.close();
            }
        }

        return  eventServices;

    }

    public EventService selectEventServicesOfParticularName(String serviceTitle) throws SQLException {
        Statement stmt = null;
        EventService es = new EventService();
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM \"Event_Service\" where \"Title\" = \'" + serviceTitle + "\';";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                es.setId(rs.getInt("Id"));
                es.setTitle(rs.getString(TITLE));
                es.setDetails(rs.getString("Details"));
                es.setEventCategory(rs.getString("Event_Category"));
                es.setPrice(rs.getString(PRICE));
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
                e.printStackTrace();
            }
        }
    }



    public String selectEventServicesOfParticularid(int id) throws SQLException {
        Statement stmt = null;
        String title = "";
        try {
            stmt = con.createStatement();
            String query = "SELECT \"Title\" FROM \"Event_Service\" where \"Id\" = " + id + ";";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                title = rs.getString(TITLE);

            }

            setStatus("Retrieving event successfully");
            return title;

        } catch (Exception e) {
            setStatus("Error while retrieving event from database");
            return title;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Event> selectEventOfParticularDateAndServiceId(String date, int serviceid) {
        List<Event> events = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            String query ="SELECT * FROM \"Event\" where \"Date\" = \'" + date + "\' and \"EventService_id\" = " + serviceid + ";";

            String query1 = "SELECT \"Title\" FROM \"Event_Service\" where \"Id\" = " + serviceid +";";

            ResultSet rs1 = stmt.executeQuery(query1);
            String servicetitle = "";
            while (rs1.next()) {
                servicetitle = rs1.getString(TITLE);
            }

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Event e = new Event(con);
                e.setId(rs.getInt(EVENT_ID));
                e.setServiceId(rs.getInt(EVENT_SERVICE_ID));
                e.setServiceTitle(servicetitle);
                e.setDate(rs.getString("Date"));
                e.setTime(rs.getString("Time"));
                e.setDescription(rs.getString(DESCRIPTION));
                e.setAttendeeCount(String.valueOf(rs.getInt("Attendee_Count")));
                events.add(e);
            }

            setStatus("Retrieving events for the given date successfully");
            return events;
        } catch (Exception e) {
            setStatus("Error while retrieving events for the date from database");
            return events;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public VendorService selectVendorServiceOfParticularName(String vendorName) throws SQLException {
        Statement stmt = null;
        VendorService vs = new VendorService();
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM \"Vendor_Service\" where \"Vendor_User_Name\" = \'" + vendorName + "\';";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                vs.setVendorUserName(rs.getString("Vendor_User_Name"));
                vs.setServiceType(rs.getString("Type"));
                vs.setServiceDescription(rs.getString(DESCRIPTION));
                vs.setServicePrice(rs.getString(PRICE));
                vs.setServiceAvailability(rs.getString("Availability"));
                vs.setAverageRating(rs.getInt("Average_Rating"));
            }

            setStatus("Retrieving vendor service successfully");
            return vs;

        } catch (Exception e) {
            setStatus("Error while retrieving vendor service from database");
            return vs;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<AVendorBooking> selectVendorBookingOfParticularName(String s) {
        Statement stmt = null;
        List<AVendorBooking> vbs = new ArrayList<>();
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM \"Vendor_Bookings\" where \"Vendor_UN\" = \'" + s + "\';";

            ResultSet rs = stmt.executeQuery(query);

            List<Integer> eventIDs = new ArrayList<>();

            while (rs.next()) {
                eventIDs.add(rs.getInt(EVENT_ID));
            }

            for(int i = 0; i < eventIDs.size(); i++) {

                String query2 = "SELECT * FROM \"Event\" where \"Event_id\" = " + eventIDs.get(i) + ";";

                ResultSet rs1 = stmt.executeQuery(query2);

                AVendorBooking vb = new AVendorBooking();

                String serviceTitle = "";
                int serviceId = 0;


                while (rs1.next()) {
                    serviceId = rs1.getInt(EVENT_SERVICE_ID);
                    vb.setBookingdate(rs1.getString("Date"));
                    vb.setStarttime(rs1.getString("Time"));
                }

               serviceTitle =  this.selectEventServicesOfParticularid(serviceId);



                vb.setBookingtime(this.selectEventServicesOfParticularName(serviceTitle).getBookingTime());
                vb.setVendorusername(s);

                vbs.add(vb);
            }

            setStatus("Retrieving vendor booking successfully");
            return vbs;

        } catch (Exception e) {
            setStatus("Error while retrieving vendor booking from database");
            return vbs;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }






    private static void incrementCount(Map<String, Integer> map, String date) {
        map.put(date, map.getOrDefault(date, 0) + 1);
    }






    public Map<Integer,Boolean> checkDays(int year , int month ,EventService eventService) {

        Map<String, Integer> dateCountMap = new HashMap<>();

        Map<Integer, Boolean> dateAvalability = new HashMap<>();


        List<Event> events = this.selectEventOfParticularServiceId(eventService);

        int timeDiff = abs(Generator.getTimeDifference(eventService.getStartTime(), eventService.getEndTime()));
        int bookingTime = Integer.parseInt(eventService.getBookingTime()) * 60;

        int numberOfEvents = timeDiff / bookingTime;


        for (Event event : events) {
            incrementCount(dateCountMap, event.getDate());
        }

        for (Map.Entry<String, Integer> entry : dateCountMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            LocalDate date = LocalDate.parse(key, formatter);


            int day1 = date.getDayOfMonth();
            int month1 = date.getMonthValue();
            int year1 = date.getYear();


            if (year == year1 && month == month1 && (value == numberOfEvents)) {
                    dateAvalability.put(day1, false);



            }



        }
        return dateAvalability;


    }








    public List<Event> selectEventOfParticularServiceId(EventService eventService) {
        List<Event> events = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            String query ="SELECT * FROM \"Event\" where \"EventService_id\" = " + eventService.getId() + ";";

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Event e = new Event(con);
                e.setId(rs.getInt(EVENT_ID));
                e.setServiceId(rs.getInt(EVENT_SERVICE_ID));
                e.setServiceTitle(eventService.getTitle());
                e.setDate(rs.getString("Date"));
                e.setTime(rs.getString("Time"));
                e.setDescription(rs.getString(DESCRIPTION));
                e.setAttendeeCount(String.valueOf(rs.getInt("Attendee_Count")));
                events.add(e);
            }


            return events;
        } catch (Exception e) {

            return events;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }





}



