package acceptanceTest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "html:outputTesting/cucumberOutput.html" , monochrome = true ,snippets = CucumberOptions.SnippetType.CAMELCASE ,features = "features",glue = "acceptanceTest")

public class acceptanceTest {

}
