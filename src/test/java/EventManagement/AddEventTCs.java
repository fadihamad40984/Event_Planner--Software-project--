package EventManagement;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import softwareproject.database.DBConnection;
import softwareproject.eventmanagement.EventManipulation;
import softwareproject.eventmanagement.EventService;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddEventTCs {
    private EventManipulation em;
    private EventService es;
    private String status;

    @When("I am in addition page")
    public void i_am_in_addition_page() {
        DBConnection conn = new DBConnection(5432, "Event_Planner", "postgres", "admin");
        em = new EventManipulation(conn.getCon());
       es = new EventService();
    }

    @When("I am fills in {string} with {string}")
    public void i_am_fills_in_with(String string, String string2) {
        switch (string) {
            case "title" -> es.setTitle(string2);
            case "details" -> es.setDetails(string2);
            case "eventCategory" -> es.setEventCategory(string2);
            case "price" -> es.setPrice(string2);
            case "place" -> es.setPlace(string2);
            case "startTime" -> es.setStartTime(string2);
            case "endTime" -> es.setEndTime(string2);
            case "bookingTime" -> es.setBookingTime(string2);
            default -> {
                assert (false);
            }
        }
        assert(true);
    }

    @When("I am click add")
    public void i_am_click_add() throws SQLException {
        em.addEventService(es);
       status = em.getStatus();
    }

    @Then("{string} should appear")
    public void should_appear(String string) {
        assertEquals(status,string);
    }

}
