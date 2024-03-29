package software_project.EventManagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private int id;
    private int serviceId;
    private String serviceTitle;
    private String date;
    private String time;
    private String description;
    private String balance;
    private String attendeeCount;
    private List<String> guestList;
    private List<String> images;
    private List<String> vendors;
    private String username;

    private final Connection con;
    private Statement stmt;

    public Event(Connection con) throws SQLException {
        this.con = con;
        this.stmt = con.createStatement();
        this.id = 0;
        this.serviceId = 0;
        this.serviceTitle = "";
        this.date = "";
        this.time = "";
        this.description = "";
        this.balance = "";
        this.attendeeCount = "";
        this.guestList = new ArrayList<>();
        this.images = new ArrayList<>();
        this.username="";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttendeeCount() {
        return attendeeCount;
    }

    public void setAttendeeCount(String attendeeCount) {
        this.attendeeCount = attendeeCount;
    }

    public List<String> getGuestList() {
        return guestList;
    }

    public void setGuestList(List<String> guestList) {
        this.guestList = guestList;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceIdBasedOnTitle(String serviceTitle) throws SQLException {
        try {
            String query = "SELECT \"Id\" FROM \"Event_Service\" where \"Title\" = \'" + serviceTitle +"\';";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                this.serviceId = rs.getInt("Id");
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }


    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<String> getVendors() {
        return vendors;
    }

    public void setVendors(List<String> vendors) {
        this.vendors = vendors;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}