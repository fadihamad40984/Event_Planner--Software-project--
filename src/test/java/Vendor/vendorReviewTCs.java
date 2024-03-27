package Vendor;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import software_project.DataBase.DBConnection;
import software_project.Vendor.VendorManipulation;
import software_project.Vendor.VendorReview;
import software_project.Vendor.VendorService;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class vendorReviewTCs {
    private VendorManipulation vm;
    private VendorReview vr;
    String status;

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
}
