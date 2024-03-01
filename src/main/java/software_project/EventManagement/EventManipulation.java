package software_project.EventManagement;

import software_project.DataBase.insert.insertData;
import software_project.UserManagement.User;
import software_project.helper.validation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EventManipulation {
    private String status;
    private insertData insertES;
    public EventManipulation(Connection con){
        insertES = new insertData(con);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addEventService(EventService eventService) throws SQLException {
        String st = validation.eventServiceValidationTest(eventService);
        setStatus(st);
        if(getStatus().equals("Valid")) {
            insertES.insertEventService(eventService);
            setStatus("Event added successfully");
        }
    }
    public void addvenue(Places place) throws SQLException {
        String st = validation.venueServiceValidationTest(place);
        setStatus(st);
        if(getStatus().equals("Valid")) {
            insertES.insertVenue(place);
            setStatus("Venue added successfully");
        }
    }
}
