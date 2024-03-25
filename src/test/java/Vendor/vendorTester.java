package Vendor;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "html:outputTesting/VendorTester.html" , features = {"Vendor_Management_Features/reviewVendor.feature"},
        monochrome = true,snippets = CucumberOptions.SnippetType.CAMELCASE, glue = {"Vendor"})


public class vendorTester {
}
