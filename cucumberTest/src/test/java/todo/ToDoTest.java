package todo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class ToDoTest {

    private WebDriver driver;

    @Given("I am on the ToDo list page")
    public void iAmOnTheToDoListPage() {
        driver = new FirefoxDriver();
        driver.get("http://localhost:8888/#/todo");
        //lets the application compile
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Given("the task {string} is on my ToDo list")
    public void theTaskIsOnMyToDoList(String arg0) {
        driver.findElement(By.xpath("//span[text()='" + arg0 + "']"));
    }

    @Given("the task {string} is not on my ToDo list")
    public void theTaskIsNotOnMyToDoList(String arg0) {
        var todo = driver.findElements(By.xpath("//span[text()='" + arg0 + "']"));
        if (todo.size() != 0) {
            throw new io.cucumber.java.PendingException();
        }
    }

    @When("I put a check box next to my task {string} to remove")
    public void iPutACheckBoxNextToMyTaskToRemove(String arg0) {
        var todoText = driver.findElement(By.xpath("//span[text()='" + arg0 + "']"));
        todoText.click();
    }

    @When("I enter my task {string}")
    public void iEnterMyTask(String arg0) {
        driver.findElement(By.name("todo")).sendKeys(arg0);
    }

    @When("I click the Remove button")
    public void iClickTheRemoveButton() {
        driver.findElement(By.name("btnRemove")).click();
    }

    @When("I click the Add button")
    public void iClickTheAddButton() {
        driver.findElement(By.name("btnAdd")).click();
    }

    @Then("the task {string} will be removed from my ToDo list")
    public void theTaskWillBeRemovedFromMyToDoList(String task) {
        var todo = driver.findElements(By.xpath("//span[text()='" + task + "']"));
        if (todo.size() != 0) {
            throw new io.cucumber.java.PendingException();
        }
    }

    @Then("the task {string} will be added to my ToDo list")
    public void theTaskWillBeAddedToMyToDoList(String task) {
        driver.findElement(By.xpath("//span[text()='" + task + "']"));
    }
}