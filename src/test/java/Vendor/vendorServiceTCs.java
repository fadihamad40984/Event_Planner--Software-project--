package Vendor;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import softwareproject.database.DBConnection;
import softwareproject.Vendor.VendorManipulation;
import softwareproject.Vendor.VendorService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class vendorServiceTCs {

    private VendorManipulation vm;
    private VendorService vs;
    String status;

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
}
