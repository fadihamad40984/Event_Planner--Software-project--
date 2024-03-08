//package EventManagement;
//
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import software_project.DataBase.DB_Connection;
//import software_project.EventManagement.EventManipulation;
//import software_project.EventManagement.EventService;
//import software_project.EventManagement.Places;
//
//import java.sql.SQLException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//public class venueTCs {
//    private EventManipulation em;
//    private Places p;
//    private String status;
//    private DB_Connection conn;
//    @Given("I am in the adding new venues page")
//    public void i_am_in_the_adding_new_venues_page() {
//
//        conn = new DB_Connection(5432,"Event_Planner","postgres","admin");
//        em = new EventManipulation(conn.getCon());
//        p = new Places();
//
//    }
//
//    @When("the organizer clicks on the button to add a new venue")
//    public void the_organizer_clicks_on_the_button_to_add_a_new_venue() {
//        assert(true);
//    }
//
//
//    @When("the organizer fills in {string} details  {string}")
//    public void the_organizer_fills_in_details(String field, String value) {
//        switch (field) {
//            case "venue_name" ->p.setName(value);
//            case "capacity" -> p.setCapacity(value);
//            case "amenities" -> p.setAmenities(value);
//
//            default -> {
//                assert (false);
//            }
//        }
//        assert(true);
//    }
//
//    @Then("i click add")
//    public void i_click_add() throws SQLException {
//        em.addvenue(p);
//        status = em.getStatus();
//    }
//
//    @Then("the {string} should appear")
//    public void the_should_appear(String string) {
//        assertEquals(string, status);
//    }
//
//}
