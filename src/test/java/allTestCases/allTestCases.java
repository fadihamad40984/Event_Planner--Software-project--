package allTestCases;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import softwareproject.authentication.Login;
import softwareproject.database.DBConnection;
import softwareproject.eventmanagement.Event;
import softwareproject.eventmanagement.EventManipulation;
import softwareproject.eventmanagement.EventService;
import softwareproject.eventmanagement.Places;
import softwareproject.helper.EmailSender;
import softwareproject.usermanagement.User;
import softwareproject.authentication.Register;
import softwareproject.vendor.VendorManipulation;
import softwareproject.vendor.VendorReview;
import softwareproject.vendor.VendorService;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class allTestCases {
    private Register userregister;
    private User user;
    private DBConnection conn;
    private String status;
    @When("user is in sign-up page")
    public void user_is_in_sign_up_page() {
        conn = new DBConnection(5432,"postgres","postgres","SfE#76132");
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




    private String status1, username, password;
    private Login login;
    private DBConnection conn8;
    @Given("user is connected to the database")
    public void user_is_connected_to_the_database() {
         conn8 = new DBConnection(5432, "postgres", "postgres", "SfE#76132");
        login = new Login(conn8.getCon());
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
        status1 = login.getStatus();
    }

    @Then("user should see {string} for login")
    public void user_should_see_for_login(String string) {
        assertEquals(status1,string);

    }

    @Then("close the connection")
    public void close_the_connection() throws SQLException {
        conn8.getCon().close();

    }

    private String to = "fadihamad40984@gmail.com";
    private String subject;
    private String body;
    EmailSender es5=new EmailSender(to);

    @Given("the user is ready to send an email")
    public void the_user_is_ready_to_send_an_email() {

        assert(true);
    }

    @When("they send an email with subject {string} and body {string}")
    public void they_send_an_email_with_subject_and_body(String string, String string2) throws MessagingException {
        this.subject = string;
        this.body = string2;

        es5.sendEmail(subject,body);
    }

    @Then("the email should be sent successfully")
    public void the_email_should_be_sent_successfully() {
        assertEquals("Email Send Successfully",es5.getStatus());
    }



    private String databaseName, username1, password1;
    private int port;

    @When("I want to connect to database")
    public void i_want_to_connect_to_database() {
        assert(true);
    }

    @When("I fill in {string} with {string}")
    public void i_fill_in_with(String field, String value) {
        switch (field) {
            case "databaseName" -> databaseName = value;
            case "username" -> username1 = value;
            case "password" -> password1 = value;
            case "port" -> port = Integer.parseInt(value);
            default -> {
                assert (false);
            }
        }
        assert(true);

    }

    @Then("I should see {string} for connection")
    public void i_should_see_for_connection(String msg) {
        DBConnection conn2 = new DBConnection(port, databaseName, username1, password1);
        String status = conn2.getStatus();
        assertEquals(status, msg);
    }

    private EventManipulation em1;
    private Event e;
    private String status3;
    private List<String> guest_list;
    private List<String> images_list;
    private List<String> vendors_list;


    @When("user is in booking page")
    public void user_is_in_booking_page() throws SQLException {
        DBConnection conn = new DBConnection(5432,"postgres","postgres","SfE#76132");
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
        status3 = em1.getStatus();
    }

    @Then("he should see {string}")
    public void he_should_see(String string) {
        assertEquals(string, status3);
    }




    private EventManipulation em3;
    private EventService es;
    private String status4;

    @When("I am in addition page")
    public void i_am_in_addition_page() {
        DBConnection conn = new DBConnection(5432, "postgres", "postgres", "SfE#76132");
        em3 = new EventManipulation(conn.getCon());
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
        em3.addEventService(es);
        status4 = em3.getStatus();
    }

    @Then("{string} should appear")
    public void should_appear(String string) {
        assertEquals(status4,string);
    }

    private EventManipulation em4;
    private EventService es1;
    private String status5;
    @Given("I am in deletion page")
    public void i_am_in_deletion_page() {
        DBConnection conn = new DBConnection(5432, "postgres", "postgres", "SfE#76132");
        em4 = new EventManipulation(conn.getCon());
        es1 = new EventService();
    }

    @When("I enter in {string} the value {string}")
    public void i_enter_in_the_value(String string, String string2) {
        if (string.equals("Event_ID")) {
            es1.setId(Integer.parseInt(string2));
        } else {
            assert (false);
        }
        assert(true);
    }

    @When("I click delete")
    public void i_click_delete() {
        em4.deleteEventService(es1);
        status5 = em4.getStatus();
    }

    @Then("A {string} must appear")
    public void a_must_appear(String string) {
        assertEquals(status5,string);

    }

    private EventManipulation em5;
    private EventService es2;
    private String status6;
    @When("I am in edition page")
    public void i_am_in_edition_page() {
        DBConnection conn = new DBConnection(5432, "postgres", "postgres", "SfE#76132");
        em5 = new EventManipulation(conn.getCon());
        es2 = new EventService();

    }

    @When("I am fill in {string} with {string}")
    public void i_am_fill_in_with(String string, String string2) {
        switch (string) {
            case "id"-> es2.setId(Integer.parseInt(string2));
            case "title" -> es2.setTitle(string2);
            case "details" -> es2.setDetails(string2);
            case "eventCategory" -> es2.setEventCategory(string2);
            case "price" -> es2.setPrice(string2);
            case "place" -> es2.setPlace(string2);
            case "startTime" -> es2.setStartTime(string2);
            case "endTime" -> es2.setEndTime(string2);
            case "bookingTime" -> es2.setBookingTime(string2);
            default -> {
                assert (false);
            }
        }
        assert(true);

    }

    @When("I am click edit")
    public void i_am_click_edit() throws SQLException {
        em5.editEventService(es2);
         status6 = em5.getStatus();
    }

    @Then("A {string} should appear")
    public void a_should_appear(String string) {
        assertEquals(status6,string);
    }



    private EventManipulation em6;
    private Places p;
    private String status7;

    @Given("I am in the adding new venues page")
    public void i_am_in_the_adding_new_venues_page() {

        DBConnection conn4 = new DBConnection(5432, "postgres", "postgres", "SfE#76132");
        em6 = new EventManipulation(conn4.getCon());
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
        em6.addVenue(p);
        status7 = em6.getStatus();
    }

    @Then("the {string} should appear")
    public void the_should_appear(String string) {
        assertEquals(string, status7);
    }


    private VendorManipulation vm;
    private VendorService vs;
    String status8;

    @When("the registering user is vendor")
    public void the_registering_user_is_vendor() {
        DBConnection conn = new DBConnection(5432, "postgres", "postgres", "SfE#76132");
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
        status8 = vm.getStatus();
    }
    @Then("vendor should see {string}")
    public void vendor_should_see(String string) {
        assertEquals(status8,string);
    }


    private VendorManipulation vm1;
    private VendorReview vr;
    String status9;

    @When("customer is in reviewing page")
    public void customer_is_in_reviewing_page() {
        DBConnection conn = new DBConnection(5432, "postgres", "postgres", "SfE#76132");
        vr = new VendorReview();
        vm1 = new VendorManipulation(conn.getCon());
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
        vm1.addVendorReview(vr);
        status9 = vm1.getStatus();
    }
    @Then("customer should see {string}")
    public void customer_should_see(String string) {
        assertEquals(status9,string);
    }

    private EventManipulation em7;
    private EventService es3;
    private String status10;
    private String username2;
    @Given("I am in delete page")
    public void iAmInDeletePage() {
        DBConnection conn9 = new DBConnection(5432, "postgres", "postgres", "SfE#76132");
        em6 = new EventManipulation(conn9.getCon());
        es3 = new EventService();
    }

    @When("I enter {string} the value {string}")
    public void iEnterUser_NameTheValue(String arg0 , String arg1) {
        if (arg0.equals("User_Name")) {
            username2 = arg1;
        } else {
            assert (false);
        }
        assert(true);
    }

    @And("click delete")
    public void clickDelete() {
        em6.deleteUser(username);
        status10 = em6.getStatus();
    }

    @Then("A {string} must beee appear")
    public void aMustBeeeAppear(String arg0) {
        assertEquals(status10,arg0);
    }
}
