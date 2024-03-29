package authentication;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import software_project.DataBase.DBConnection;
import software_project.authentication.Login;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class logintest {

    private String status, username, password;
    private DBConnection conn;
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


}
