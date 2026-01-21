package iCite;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;


//SimpleSearch
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
    public void iClickInTheSearchQueryField() {
        // driver.findElement(By.id("searchBox")).click();
        WebElement element = driver.findElement(By.id("searchBox"));
        WebDriverWait wait = new WebDriverWait(driver, 20); //here, wait time is 20 seconds
        wait.until(ExpectedConditions.visibilityOf(element)); //this will wait for element to be visible for 20 seconds
        element.click(); //now it clicks on element
    }

    @When("I enter pcsk9.")
    public void ienterpcsk9() {
        driver.findElement(By.id("searchBox")).sendKeys("pcsk9");
    }

    @When("I click the magnifying glass.")
    public void magnifyingGlass() {
        driver.findElement(By.id("searchButton")).click();
    }

    @Then("the results for pcsk9 will be displayed.")
    public void theResultsWillBeDisplayed() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

//Simple Search using AND function

    @When("I search for COVID and SYMPTOMS.")
    public void isearchforcovidandsymptoms() {
        driver.findElement(By.id("searchBox")).sendKeys("COVID and SYMPTOMS");
    }

    @Then("all results for COVID as well as SYMPTOMS will be displayed.")
    public void alltheresultsforcovidaswellassymptomswillbedisplayed() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

//Simple Search using OR function

    @When("I search for COVID or SYMPTOMS.")
    public void isearchforcovidorsymptoms() {
        driver.findElement(By.id("searchBox")).sendKeys("COVID or SYMPTOMS");
    }

    @Then("all results for COVID or SYMPTOMS will be displayed.")
    public void alltheresultsforcovidorsymptomswillbedisplayed() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    //Simple Search using a date range

    @When("I click the Advanced Filter icon.")
    public void iclicktheadvancedfiltericon() {
        WebElement element = driver.findElement(By.xpath("//button[@title='Advanced Filters']"));
        WebDriverWait wait = new WebDriverWait(driver, 5); //here, wait time is 5 seconds
        wait.until(ExpectedConditions.visibilityOf(element)); //this will wait for element to be visible for 20 seconds
        element.click(); //now it clicks on element
    }

    @When("I click the From box.")
    public void iclickthefrombox() {
        //"FROM BOX"
        WebElement element = driver.findElement(By.xpath("//input[@class='form-control']"));
        WebDriverWait wait = new WebDriverWait(driver, 5); //here, wait time is 5 seconds
        wait.until(ExpectedConditions.visibilityOf(element)); //this will wait for element to be visible for 20 seconds
        element.click(); //now it clicks on element
    }

    @When("I enter 2021-03-03.")
    public void ienter20210303() {
        //"Enters start date. This is the line for the FROM Date Picker")
        driver.findElement(By.xpath("//input[@class='form-control']")).sendKeys("2021-03-03");
    }

    @When("I click the To box.")
    public void iclickthetobox() {
        //"TO BOX"
        WebElement element = driver.findElement(By.xpath("(//input[@class='form-control'])[2]"));
        WebDriverWait wait = new WebDriverWait(driver, 5); //here, wait time is 5 seconds
        wait.until(ExpectedConditions.visibilityOf(element)); //this will wait for element to be visible for 20 seconds
        element.click(); //now it clicks on element
    }

    @When("I enter 2021-04-26.")
    public void ienter20210426() {
        //"Enters start date. This is the line for the To Date Picker")
        driver.findElement(By.xpath("(//input[@class='form-control'])[2]")).sendKeys("2021-04-26");

    }

    @When("I click the Apply filters button.")
    public void iclicktheapplyfiltersbutton() {
        //Clicks apply filter button
        driver.findElement(By.xpath("//button[contains(@class,'btn btn-sm')]")).click();

    }

    @Then("all publications will be displayed.")
    public void allpublicationswithinthedaterangewillbedisplayedandiamdonewithmytest() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }


}