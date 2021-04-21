package iCite;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class SimpleSearch {

    private WebDriver driver;


    @Given("I am on the iCite page.")
    public void iAmOnTheiCitePage() {
        driver = new FirefoxDriver();
        driver.get("https://icite.od.nih.gov/covid19/search/");
        //lets the application compile
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @When("I click my mouse in the Search query field.")
    public void iClickInTheSearchQueryField()

    {
       // driver.findElement(By.id("searchBox")).click();
        WebElement element = driver.findElement(By.id("searchBox"));
        WebDriverWait wait = new WebDriverWait(driver, 20); //here, wait time is 20 seconds

        wait.until(ExpectedConditions.visibilityOf(element)); //this will wait for element to be visible for 20 seconds
        element.click(); //now it clicks on element
    };
    @When("I enter pcsk9.")
    public void ienterpcsk9() {
        driver.findElement(By.id("searchBox")).sendKeys("pcsk9");
    }

    @When("I click the magnifying glass.")
    public void magnifyingGlass() {
        driver.findElement(By.id("searchButton")).click();
    }

    @Then("the results for pcsk9 will be displayed.")
    public void theResultsWillBeDisplayed()

    {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

}
