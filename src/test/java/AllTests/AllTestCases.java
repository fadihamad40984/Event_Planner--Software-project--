package AllTests;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.sf.jasperreports.engine.JRException;
import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import softwareproject.authentication.UserSessionManager;
import softwareproject.database.DBConnection;
import softwareproject.eventmanagement.Event;
import softwareproject.eventmanagement.EventManipulation;
import softwareproject.eventmanagement.EventService;
import softwareproject.eventmanagement.Places;
import softwareproject.helper.JasperReportGenerator;
import softwareproject.usermanagement.User;
import softwareproject.vendor.VendorManipulation;
import softwareproject.vendor.VendorReview;
import softwareproject.vendor.VendorService;
import softwareproject.authentication.Login;
import softwareproject.authentication.Register;
import softwareproject.helper.EmailSender;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)


public class AllTestCases {

    private Register userregister;
    private User user;
    private DBConnection conn;
    private String status;




    @When("user is in sign-up page")
    public void user_is_in_sign_up_page() {
        conn = new DBConnection(5432,"Event_Planner","postgres","admin");
        userregister = new Register(conn.getCon());
        user= new User();
    }

    @When("he fills in {string} with {string} for register")
    public void he_fills_in_with_for_register(String field, String value) {
        switch (field) {
            case "username" -> user.setUsername(value);
            case "firstName" -> user.setFirstName(value);
            case "lastName" -> user.setLastName(value);
            case "phoneNumber" -> user.setPhoneNumber(value);
            case "password" -> user.setPassword(value);
            case "email" -> user.setEmail(value);
            case "userType" -> user.setUserType(value);
            default -> {
                assert (false);
            }
        }
        assert(true);
    }

    @When("he submits the registration form")
    public void he_submits_the_registration_form() throws SQLException {
        userregister.registerUser(user);
        status = userregister.getStatus();
    }

    @Then("he should see {string} for register")
    public void he_should_see_for_register(String string) {
        assertEquals(string, status);

    }


    private String username, password;
    private Login login;
    @Given("user is connected to the database")
    public void user_is_connected_to_the_database() {
        conn = new DBConnection(5432,"Event_Planner","postgres","admin");
        login = new Login(conn.getCon());
    }

    @When("he fills in {string} with {string} for login")
    public void he_fills_in_with_for_login(String string, String string2) {
        if(string.equals("username"))
            username = string2;
        else
            password = string2;
    }

    @When("user clicks on login")
    public void user_clicks_on_login() throws SQLException {
        login.loginUser(username, password);
        status = login.getStatus();
    }

    @Then("user should see {string} for login")
    public void user_should_see_for_login(String string) {
        assertEquals(status,string);

    }

    @Then("close the connection")
    public void close_the_connection() throws SQLException {
        conn.getCon().close();

    }


    private String to = "momentsgolden806@gmail.com";
    private String subject;
    private String body;
    EmailSender em=new EmailSender(to);

    @Given("the user is ready to send an email")
    public void the_user_is_ready_to_send_an_email() {

        assert(true);
    }

    @When("they send an email with subject {string} and body {string}")
    public void they_send_an_email_with_subject_and_body(String string, String string2) throws MessagingException {
        this.subject = string;
        this.body = string2;

        em.sendEmail(subject,body);
    }

    @Then("the email should be sent successfully")
    public void the_email_should_be_sent_successfully() {
        assertEquals("Email Send Successfully",em.getStatus());
    }


    private String databaseName;
    private int port;

    @When("I want to connect to database")
    public void i_want_to_connect_to_database() {
        assert(true);
    }

    @When("I fill in {string} with {string}")
    public void i_fill_in_with(String field, String value) {
        switch (field) {
            case "databaseName" -> databaseName = value;
            case "username" -> username = value;
            case "password" -> password = value;
            case "port" -> port = Integer.parseInt(value);
            default -> {
                assert (false);
            }
        }
        assert(true);

    }

    @Then("I should see {string} for connection")
    public void i_should_see_for_connection(String msg) {
        conn = new DBConnection(port, databaseName, username, password);
        String status = conn.getStatus();
        assertEquals(status, msg);
    }


    private Places p;
    private EventManipulation em1;



    @Given("I am in the adding new venues page")
    public void i_am_in_the_adding_new_venues_page() {

        conn = new DBConnection(5432,"Event_Planner","postgres","admin");
        em1 = new EventManipulation(conn.getCon());
        p = new Places();

    }

    @When("the organizer clicks on the button to add a new venue")
    public void the_organizer_clicks_on_the_button_to_add_a_new_venue() {
        assert(true);
    }


    @When("the organizer fills in {string} details  {string}")
    public void the_organizer_fills_in_details(String field, String value) {
        switch (field) {
            case "venue_name" ->p.setName(value);
            case "capacity" -> p.setCapacity(value);
            case "amenities" -> p.setAmenities(value);

            default -> {
                assert (false);
            }
        }
        assert(true);
    }

    @Then("i click add")
    public void i_click_add() throws SQLException {
        em1.addVenue(p);
        status = em1.getStatus();
    }

    @Then("the {string} should appear")
    public void the_should_appear(String string) {
        assertEquals(string, status);
    }

    private VendorManipulation vm;
    private VendorService vs;

    @When("the registering user is vendor")
    public void the_registering_user_is_vendor() {
        DBConnection conn = new DBConnection(5432, "Event_Planner", "postgres", "admin");
        vs = new VendorService();
        vm = new VendorManipulation(conn.getCon());
    }

    @When("vendor fills in {string} with {string}")
    public void vendor_fills_in_with(String string, String string2) {
        switch (string) {
            case "vendorUserName" -> vs.setVendorUserName(string2);
            case "serviceType" -> vs.setServiceType(string2);
            case "serviceDescription" -> vs.setServiceDescription(string2);
            case "servicePricePerHour" -> vs.setServicePrice(string2);
            case "serviceAvailability" -> vs.setServiceAvailability(string2);
            default -> {
                assert (false);
            }
        }
        assert(true);
    }

    @When("vendor click confirm service")
    public void vendor_click_confirm_service() {
        vm.addVendorService(vs);
        status = vm.getStatus();
    }

    @Then("vendor should see {string}")
    public void vendor_should_see(String string) {
        assertEquals(status,string);
    }


    private VendorReview vr;

    @When("customer is in reviewing page")
    public void customer_is_in_reviewing_page() {
        DBConnection conn = new DBConnection(5432, "Event_Planner", "postgres", "admin");
        vr = new VendorReview();
        vm = new VendorManipulation(conn.getCon());
    }

    @When("customer fills in {string} with {string}")
    public void customer_fills_in_with(String string, String string2) {
        switch (string) {
            case "vendorUserName" -> vr.setVendorUserName(string2);
            case "customerUserName" -> vr.setCustomerUserName(string2);
            case "rating" -> vr.setRating(string2);
            case "feedBackText" -> vr.setFeedBackText(string2);
            default -> {
                assert (false);
            }
        }
        assert(true);
    }

    @When("customer click confirm review")
    public void customer_click_confirm_review() throws SQLException {
        vm.addVendorReview(vr);
        status = vm.getStatus();
    }

    @Then("customer should see {string}")
    public void customer_should_see(String string) {
        assertEquals(status,string);
    }

    private EventService es;


    @When("I am in addition page")
    public void i_am_in_addition_page() {
        DBConnection conn = new DBConnection(5432, "Event_Planner", "postgres", "admin");
        em1 = new EventManipulation(conn.getCon());
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
        em1.addEventService(es);
        status = em1.getStatus();
    }

    @Then("{string} should appear")
    public void should_appear(String string) {
        assertEquals(status,string);
    }


    @When("I am in edition page")
    public void i_am_in_edition_page() {
        DBConnection conn = new DBConnection(5432, "Event_Planner", "postgres", "admin");
        em1 = new EventManipulation(conn.getCon());
        es = new EventService();

    }


    @When("I am fill in {string} with {string}")
    public void i_am_fill_in_with(String string, String string2) {
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
        em1.editEventService(es);
        status = em1.getStatus();


    }

    @Then("A {string} should appear")
    public void a_should_appear(String string) {
        assertEquals(status,string);
    }

    private Event e;

    private List<String> guest_list;
    private List<String> images_list;
    private List<String> vendors_list;

    @When("user is in booking page")
    public void user_is_in_booking_page() throws SQLException {
        DBConnection conn = new DBConnection(5432,"Event_Planner","postgres","admin");
        em1 = new EventManipulation(conn.getCon());
        e = new Event(conn.getCon());
        guest_list = new ArrayList<>();
        images_list = new ArrayList<>();
        vendors_list = new ArrayList<>();
    }
    @When("he fills in {string} with {string}")
    public void he_fills_in_with(String string, String string2) throws SQLException {

        switch (string) {
            case "serviceTitle" -> {
                e.setServiceTitle(string2);
                e.setServiceIdBasedOnTitle(string2);
            }
            case "date" -> e.setDate(string2);
            case "time" -> e.setTime(string2);
            case "description" -> e.setDescription(string2);
            case "vendors" -> {
                String[] vendors = string2.split(",");
                vendors_list.addAll(Arrays.asList(vendors));
                e.setVendors(vendors_list);
            }
            case "attendeeCount" -> e.setAttendeeCount(string2);
            case "guestList" -> {
                String[] guests = string2.split(",");
                guest_list.addAll(Arrays.asList(guests));
                e.setGuestList(guest_list);
            }
            case "balance" -> e.setBalance(string2);
            case "image" -> {
                String[] imagesArr = string2.split(",");
                images_list.addAll(Arrays.asList(imagesArr));
                e.setImages(images_list);
            }

            case "user"->e.setUsername(string2);
            default -> {
                assert false;
            }
        }
        assert true;

    }



    @When("he click book")
    public void he_click_book() throws SQLException {
        em1.bookEvent(e);
        status = em1.getStatus();
    }

    @Then("he should see {string}")
    public void he_should_see(String string) {
        assertEquals(string, status);
    }


    @Given("I am in deletion page")
    public void i_am_in_deletion_page() {
        DBConnection conn = new DBConnection(5432, "Event_Planner", "postgres", "admin");
        em1 = new EventManipulation(conn.getCon());
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
        em1.deleteEventService(es);
        status = em1.getStatus();
    }

    @Then("A {string} must appear")
    public void a_must_appear(String string) {
        assertEquals(status,string);

    }
    JasperReportGenerator jasperReportGenerator = new JasperReportGenerator();

    @When("Admin press to show Report")
    public void admin_press_to_show_report() {
        assert(true);
    }

    @Then("Pdf file will generated")
    public void pdf_file_will_generated() throws JRException, SQLException, IOException {
      jasperReportGenerator.generateReport("userreport.jrxml","test");
    }

    @Then("{string} should be appear")
    public void should_be_appear(String string) {
       assertEquals(string,jasperReportGenerator.getStatus());
    }

    @When("he pressed logout button and confirm it")
    public void he_pressed_logout_button_and_confirm_it() {
        UserSessionManager.logoutUser();
    }

    @Then("user should see {string} for logout")
    public void user_should_see_for_logout(String string) {
        assertEquals(string, UserSessionManager.getStatus());

    }



}
