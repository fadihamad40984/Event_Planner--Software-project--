package EventManagement;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import software_project.DataBase.DB_Connection;
import software_project.EventManagement.EventManipulation;
import software_project.EventManagement.EventService;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddEventTCs {
    private EventManipulation em;
    private EventService es;
    private String status;
    private DB_Connection conn;

    @When("I am in addition page")
    public void i_am_in_addition_page() {
        conn = new DB_Connection(5432,"Event_Planner","postgres","admin");
        em = new EventManipulation(conn.getCon());
        es = new EventService();
    }

    @When("I fill in {string} with  {string}")
    public void i_fill_in_with(String string, String string2) {
        switch (string) {
            case "Title" -> es.setTitle(string2);
            case "Details" -> es.setDetails(string2);
            case "EventCategory" -> es.setEventCategory(string2);
            case "Price" -> es.setPrice(string2);
            case "Place" -> es.setPlace(string2);
            case "StartTime" -> es.setStartTime(string2);
            case "EndTime" -> es.setEndTime(string2);
            case "BookingTime" -> es.setBookingTime(string2);
            default -> {
                assert (false);
            }
        }
        assert(true);
    }

    @When("I click add")
    public void i_click_add() throws SQLException {
        em.addEventService(es);
        status = em.getStatus();
    }

    @Then("A {string} should appear")
    public void a_should_appear(String string) {
        assertEquals(string, status);
    }

}
