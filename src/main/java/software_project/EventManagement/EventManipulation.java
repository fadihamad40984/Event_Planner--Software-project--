package software_project.EventManagement;

import software_project.DataBase.delete.deleteEvent;
import software_project.DataBase.insert.insertData;
import software_project.DataBase.update.editEvent;
import software_project.UserManagement.User;
import software_project.helper.validation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EventManipulation {
    private String status;
    private insertData insertES;

    private editEvent editEvent;
    private deleteEvent delete;
    public EventManipulation(Connection con){
        insertES = new insertData(con);
        delete = new deleteEvent(con);
        editEvent = new editEvent(con);
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
            insertES.insertEventService_Place(eventService);
            setStatus("Event added successfully");
        }
    }

    public void deleteEventService(EventService es) {
        boolean b = delete.delete_event(es);

        setStatus(delete.getStatus());

    }

    public void addvenue(Places place) throws SQLException {
        String st = validation.venueServiceValidationTest(place);
        setStatus(st);
        if(getStatus().equals("Valid")) {
            insertES.insertVenue(place);
            setStatus("Venue added successfully");
        }
    }


    public void bookEvent(Event e) throws SQLException {
        String st = validation.eventValidationTest(e);
        setStatus(st);
        if(getStatus().equals("Valid")) {
            insertES.insertEvent(e);
            insertES.insertNotAvailableVendor(e);

            setStatus("Event booked successfully");
        }
    }


    public void editEventService(EventService eventService) {


            editEvent.updateEventService(eventService);

            setStatus("Event updated successfully");


    }
}
