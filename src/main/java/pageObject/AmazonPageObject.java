package pageObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import com.Baseclass.WebTestBase;
import com.Reports.ReportStatus;
import com.Reports.Reports;
import com.aventstack.extentreports.ExtentTest;

public class AmazonPageObject extends WebTestBase{
	List<Integer> priceDetails = new ArrayList<>();
	WebDriver driver;
	ExtentTest extentTest;
	public AmazonPageObject(WebDriver driver, ExtentTest extentTest) {
		this.driver = driver;
		this.extentTest = extentTest;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[contains(@id,'hamburger-menu')]")
	private WebElement hamburger_menu;
	
	@FindBys({@FindBy(xpath  = "//ul[@class='hmenu hmenu-visible']/li/a/div")})
	private List<WebElement> categoryList;
	
	@FindBys({@FindBy(xpath  = "//ul[@class='hmenu hmenu-visible hmenu-translateX']/li/a")})
	private List<WebElement> categoryElementsList;
	
	@FindBy(xpath = "//span[contains(@id,'autoid-0-')]")
	private WebElement priceCategory;
	
	@FindBys({@FindBy(xpath  = "//ul[@class='a-nostyle a-list-link']/li/a")})
	private List<WebElement> priceSubCategory;
	
	@FindBys({@FindBy(xpath  = "//span[@class='a-price']/span/span[@class='a-price-whole']")})
	private List<WebElement> priceList;
	
	/*public WebElement selectCategory(String category) {
		return driver.findElement(By.xpath("//div/span[text()='Brands']/parent::div//following-sibling::ul//li/span/a/span[text()='Samsung']"));
	}*/
	
	@FindBy(xpath = "//span[text()='Samsung']")
	private WebElement selectSamsungCategory;
	
	public void click_hamburger_menu() {
		hamburger_menu.click();
	}
	
	public void choose_Category_and_Product(String category, String product) throws IOException {
		for (WebElement webElement : categoryList) {
			String categoryName = webElement.getText();
			if(categoryName.equals(category) || categoryName.contains(category)) {
				//System.out.println(webElement.getText());
				webElement.click();
				Reports.log(extentTest, "Clicked Category "+category, ReportStatus.Pass);
				break;
			}
		}
		
		for (WebElement webElement : categoryElementsList) {
			String categoryName = webElement.getText();
			if(categoryName.equals(product) || categoryName.contains(product)) {
				webElement.click();
				Reports.log(extentTest, "Clicked Product "+product, ReportStatus.Pass);
				break;
			}
		}
	}
	
	public void chooseCategory(String category, String subCategory) throws InterruptedException {
		//WebElement selectCategory = selectCategory(category);
		implicitWait(10);
		scroll(selectSamsungCategory);
		selectSamsungCategory.click();
		//selectCategory.click();
		//explicitWait(10, priceCategory);
		priceCategory.click();
		
		for (WebElement webElement : priceSubCategory) {
			String text = webElement.getText();
			if(text.equals("Price: High to Low")) {
				webElement.click();
				break;
			}
		}
	}
	
	public void selectProduct() throws IOException {
		priceList.get(1).click();
		Reports.log(extentTest,priceList.get(1).getText(), ReportStatus.Pass);
	}
	
	
}
