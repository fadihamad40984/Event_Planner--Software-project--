package authentication;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import software_project.DataBase.DB_Connection;
import software_project.UserManagement.User;
import software_project.authentication.Login;
import software_project.authentication.Register;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class registertest {


    private Register userregister;
    private User user;
   private DB_Connection conn;
    private String status;
    @When("user is in sign-up page")
    public void user_is_in_sign_up_page() {
        conn = new DB_Connection(5432,"Event_Planner","postgres","admin");
        userregister = new Register(conn.getCon());
        user= new User();
    }

    @When("he fills in {string} with {string} for register")
    public void he_fills_in_with_for_register(String string, String string2) {
        if(string.equals("username"))
            user.setUsername(string2);
        else if(string.equals("firstName"))
            user.setFirstName(string2);
        else if(string.equals("lastName"))
            user.setLastName(string2);
        else if(string.equals("phoneNumber"))
            user.setPhoneNumber(string2);
        else if(string.equals("password"))
            user.setPassword(string2);
        else if(string.equals("email"))
            user.setEmail(string2);
        else if(string.equals("userType"))
            user.setUserType(string2);
        else
            assert(false);
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




}
