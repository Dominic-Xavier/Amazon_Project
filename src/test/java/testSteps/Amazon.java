package testSteps;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.Baseclass.WebTestBase;
import com.Reports.ReportStatus;
import com.Reports.Reports;
import com.aventstack.extentreports.ExtentTest;
import com.customException.BrowserException;
import com.runner.TestRunner;

import io.cucumber.java.AfterAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObject.AmazonPageObject;

public class Amazon extends WebTestBase {
	
	private WebDriver driver;
	private WebTestBase webTestBase;
	private TestRunner runner = new TestRunner();
	private ExtentTest createTest;
	ExtentTest extentTest;
	private AmazonPageObject amazon;
	
	@Given("User opens the app")
	public void user_opens_the_app() throws IOException, BrowserException {
		String URL = getData("URL");
		driver = Initialize(getAppName(), URL);
		createTest = runner.createTest("Amazon Test", "User Actions", driver);
		extentTest = new Reports().createNode(createTest, "Launching app");
		amazon = new AmazonPageObject(driver, extentTest);
		webTestBase = new WebTestBase(driver);
	}
	
	@Given("user clicks on hamburger menu")
	public void user_clicks_on_hamburger_menu() throws IOException {
		amazon.click_hamburger_menu();
		Reports.log(extentTest, "Clicked HamBurger Menu ", ReportStatus.Pass);
	}
	
	@When("user selects Tv, Electronics category and selects Televisions")
	public void user_selects_tv_electronics_category_and_selects_televisions() throws IOException {
		amazon.choose_Category_and_Product("TV, Appliances, Electronics", "Televisions");
	}
	
	@Then("Select Samsung and sort by proci high to low")
	public void select_samsung_and_sort_by_proci_high_to_low() throws InterruptedException, IOException {
		amazon.chooseCategory("Brands", "Samsung");
	}
	
	@Then("User selects the second largest price TV and prints the description under About this item section")
	public void user_selects_the_second_largest_price_tv_and_prints_the_description_under_about_this_item_section() throws IOException {
		amazon.selectProduct();
		amazon.readAbtProduct();
		runner.closeReports();
	}
	
	/*@AfterAll
	public void close() {
		runner.closeReports();
	}*/
}
