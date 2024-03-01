package EventManagement;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "html:outputTesting/EventTester.html" , features = {"Event_Management_Features"},
        monochrome = true,snippets = CucumberOptions.SnippetType.CAMELCASE, glue = {"EventManagement"})


public class EventManagementTester {
}
