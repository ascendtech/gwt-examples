import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CommonSteps {
	private WebDriver driver;

	@Given("Open the Firefox and launch the application")
	public void openTheFirefoxAndLaunchTheApplication() {
		System.setProperty("webdriver.chrome.driver", "/home/mdavis/Downloads/chromedriver");
		driver = new ChromeDriver();

		driver.get("http://demo.guru99.com/v4");

	}

	@When("Enter the user {string} and password {string}")
	public void enterTheUserAndPassword(String arg0, String arg1) {
		driver.findElement(By.name("uid")).sendKeys(arg0);
		driver.findElement(By.name("password")).sendKeys(arg1);
	}

	@Then("Reset the credential")
	public void resetTheCredential() {
		driver.findElement(By.name("btnReset")).click();
	}

	@Then("Login with the credentials")
	public void loginWithTheCredentials() {
	}

}
