package runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/features",
        glue = {"steps", "functions", "pages", "hooks", "utils"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html"},
        monochrome = true,
        tags = "@CT-09"
)
public class TestRunner {
}