package DataBase;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import software_project.DataBase.DBConnection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DB_ConnectionTest {
    private String databaseName, username, password;
    private int port;
    private DBConnection conn;
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
}
