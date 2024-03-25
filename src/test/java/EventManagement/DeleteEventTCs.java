package EventManagement;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import software_project.DataBase.DB_Connection;
import software_project.EventManagement.EventManipulation;
import software_project.EventManagement.EventService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteEventTCs {
    private EventManipulation em;
    private EventService es;
    private String status;
    @Given("I am in deletion page")
    public void i_am_in_deletion_page() {
        DB_Connection conn = new DB_Connection(5432, "Event_Planner", "postgres", "admin");
        em = new EventManipulation(conn.getCon());
        es = new EventService();
    }

    @When("I enter in {string} the value {string}")
    public void i_enter_in_the_value(String string, String string2) {
        if (string.equals("Event_ID")) {
            es.setId(Integer.parseInt(string2));
        } else {
            assert (false);
        }
        assert(true);
    }

    @When("I click delete")
    public void i_click_delete() {
        em.deleteEventService(es);
        status = em.getStatus();
    }

    @Then("A {string} must appear")
    public void a_must_appear(String string) {
        assertEquals(status,string);

    }


}
