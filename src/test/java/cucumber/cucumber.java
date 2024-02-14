package cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "html:target/cucumberOutput.html" , monochrome = true ,snippets = CucumberOptions.SnippetType.CAMELCASE ,features = "use-cases",glue = "cucumber")

public class cucumber {

}
