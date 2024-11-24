package DataBase;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "html:outputTesting/DB.html" , features = "DB_Features",
        monochrome = true,snippets = CucumberOptions.SnippetType.CAMELCASE, glue = {"DataBase"})

public class DB_Tester {
}
