package software_project.EventManagement;

import software_project.DataBase.delete.DeleteEvent;
import software_project.DataBase.insert.InsertData;
import software_project.DataBase.update.EditEvent;
import software_project.helper.Validation;

import java.sql.Connection;
import java.sql.SQLException;

public class EventManipulation {
    public static final String VALID = "Valid";
    private String status;
    private final InsertData insertES;

    private final EditEvent editEvent;
    private final DeleteEvent delete;
    public EventManipulation(Connection con){
        insertES = new InsertData(con);
        delete = new DeleteEvent(con);
        editEvent = new EditEvent(con);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addEventService(EventService eventService)  {
        String st = Validation.eventServiceValidationTest(eventService);
        setStatus(st);
        if(getStatus().equals(VALID)) {
            insertES.insertEventService(eventService);
            insertES.insertEventServicePlace(eventService);
            setStatus("Event added successfully");
        }
    }

    public void deleteEventService(EventService es) {
        delete.deleteEvent(es);


        setStatus(delete.getStatus());

    }

    public void addVenue(Places place)  {
        String st = Validation.venueServiceValidationTest(place);
        setStatus(st);
        if(getStatus().equals(VALID)) {
            insertES.insertVenue(place);
            setStatus("Venue added successfully");
        }
    }


    public void bookEvent(Event e) throws SQLException {
        String st = Validation.eventValidationTest(e);
        setStatus(st);
        if(getStatus().equals(VALID)) {
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