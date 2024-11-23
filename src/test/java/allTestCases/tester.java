package allTestCases;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)


@CucumberOptions(plugin = "html:outputTesting/alltestcases.html" , features = {"Features"},
        monochrome = true,snippets = CucumberOptions.SnippetType.CAMELCASE, glue = {"allTestCases"} )


public class tester {
}
