package EventManagement;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import software_project.DataBase.DB_Connection;
import software_project.EventManagement.EventManipulation;
import software_project.EventManagement.EventService;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditEventTCs {

    private EventManipulation em;
    private EventService es;
    private String status;
    @When("I am in edition page")
    public void i_am_in_edition_page() {
        DB_Connection conn = new DB_Connection(5432, "Event_Planner", "postgres", "admin");
        em = new EventManipulation(conn.getCon());
        es = new EventService();

    }

    @When("I am fills in {string} with {string}")
    public void i_am_fills_in_with(String string, String string2) {
        switch (string) {
            case "id"-> es.setId(Integer.parseInt(string2));
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

    @When("I am click edit")
    public void i_am_click_edit() throws SQLException {
        em.editEventService(es);
        status = em.getStatus();
    }

    @Then("A {string} should appear")
    public void a_should_appear(String string) {
        assertEquals(status,string);
    }



}
