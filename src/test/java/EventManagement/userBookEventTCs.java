package EventManagement;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import software_project.DataBase.DB_Connection;
import software_project.EventManagement.Event;
import software_project.EventManagement.EventManipulation;
import software_project.EventManagement.EventService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class userBookEventTCs {
    private EventManipulation em;
    private Event e;
    private String status;
    private List<String> guest_list;
    private List<String> images_list;


    @When("user is in booking page")
    public void user_is_in_booking_page() throws SQLException {
        DB_Connection conn = new DB_Connection(5432,"Event_Planner","postgres","admin");
        em = new EventManipulation(conn.getCon());
        e = new Event(conn.getCon());
        guest_list = new ArrayList<>();
        images_list = new ArrayList<>();
    }

    @When("he fills in {string} with {string}")
    public void he_fills_in_with(String string, String string2) throws SQLException {
        switch (string) {
            case "ServiceTitle" -> {
                e.setServiceTitle(string2);
                e.setServiceIdBasedOnTitle(string2);
            }
            case "Date" -> e.setDate(string2);
            case "Time" -> e.setTime(string2);
            case "Description" -> e.setDescription(string2);
            case "AttendeeCount" -> e.setAttendeeCount(string2);
            case "GuestList" -> {
                String[] guests = string2.split(",");
                guest_list.addAll(Arrays.asList(guests));
                e.setGuestList(guest_list);
            }
            case "Image" -> {
                String[] imagesArr = string2.split(",");
                images_list.addAll(Arrays.asList(imagesArr));
                e.setImages(images_list);
            }
            default -> {
                assert(false);
            }
        }
        assert(true);
    }

    @When("he click book")
    public void he_click_book() throws SQLException {
        em.bookEvent(e);
        status = em.getStatus();
    }

    @Then("he should see {string}")
    public void he_should_see(String string) {
        assertEquals(string, status);
    }

}