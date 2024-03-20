package bookEvent;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(plugin = "html:outputTesting/bookEvent.html" , features = {"Event_Management_Features/bookingEventByUser.feature"},
        monochrome = true,snippets = CucumberOptions.SnippetType.CAMELCASE, glue = {"bookEvent"})

public class testuserBook {
}




