package com.Baseclass;

import java.time.Duration;
import java.util.Set;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.testNgClass.BrowserDriver;

import enumVariales.AlertBox;
import enumVariales.KeyBoard;
import enumVariales.SelectBy;

public class WebTestBase extends BrowserDriver{

	private static Select select;
	private static WebDriverWait wait;
	public static WebDriver driver;
	private static Actions actions;
	private static Robot robot;
	private static JavascriptExecutor executor;
	//private static TouchActions touchActions;
	
	public WebTestBase() {}
	
	public WebTestBase(WebDriver drivers) {
		driver = drivers;
	}
	
	//public static DataProviders dp = new DataProviders(drivers);
	//private static WebDriver chrome, firefox, ieDriver, safari, opera;
	
	//private static WebTestBase testBase;
	
	/*public synchronized static WebTestBase getInstance(WebDriver driver) {
		if(testBase==null)
			testBase = new WebTestBase(driver);
		return testBase;
	}*/
	
	/*public static synchronized WebDriver getWebDriver(String browserName) {
		
		switch (browserName.toUpperCase()) {
			case "CHROME":
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				driver.manage().window().maximize();
			return driver;
			
			case "FIREFOX":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
			return driver;
				
			case "IE":
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
				driver.manage().window().maximize();
			return driver;
				
			case "SAFARI":
				WebDriverManager.safaridriver().setup();
				driver = new SafariDriver();
				driver.manage().window().maximize();
			return driver;
			
			case "EDGE":
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				driver.manage().window().maximize();
			return driver;
				
			case "OPERA":
				WebDriverManager.operadriver().setup();
				driver = new OperaDriver();
				driver.manage().window().maximize();
			return driver;
			
			default:
				System.err.println("Enter Correct Browser Name...!");
		}
		return null;
	}*/
	
	
	
	public void passURL(String URL) {
		driver.get(URL);
	}
	
	public void acceptAlert() {
		driver.switchTo().alert().accept();
	}
	
	public void declineAlert() {
		driver.switchTo().alert().dismiss();
	}
	
	public String getAlertText() {
		return driver.switchTo().alert().getText();
	}
	
	public void typeTextinAlert(String text) {
		driver.switchTo().alert().sendKeys(text);
		acceptAlert();
	}
	
	public Set<String> getWindowHandles(){
		return driver.getWindowHandles();
	}
	
	/*public void longPress(WebElement element) {
		touchActions = new TouchActions(driver);
		touchActions.longPress(element);
	}*/
	
	public void selectByValueOrVisibletext(SelectBy by, WebElement element, String text) {
		switch (by) {
		case SELECT_BY_VALUE:
			select = new Select(element);
			select.selectByValue(text);
		break;

		case SELECT_BY_VISIBLE_TEXT:
			select = new Select(element);
			select.selectByVisibleText(text);
		break;
		
		case SELECT_BY_INDEX:
			select = new Select(element);
			select.selectByIndex(Integer.parseInt(text));
		break;
		
		case DESELECT_BY_INDEX:
			select = new Select(element);
			select.deselectByIndex(Integer.parseInt(text));
		break;
		
		case DESELECT_BY_VALUE:
			select = new Select(element);
			select.deselectByValue(text);
		break;
		
		case DESELECT_BY_VISIBLE_TEXT:
			select = new Select(element);
			select.deselectByVisibleText(text);
		break;
		
		case DESELECT_ALL:
			select = new Select(element);
			select.deselectAll();
		break;
		
		}
	}
	
	public String getTitle() {
		return driver.getTitle();
	}
	
	public void implicitWait(long seconds) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
	}
	
	public void explicitWait(long seconds, WebElement element) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void pageLoadTimeOut(long seconds) {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(seconds));
	}
	
	public void navigateFordWard() {
		driver.navigate().forward();
	}
	
	public void navigateBackWard() {
		driver.navigate().back();
	}
	
	
	
	public void pressEnter() {
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	public void doubleClick(WebElement element) {
		actions = new Actions(driver);
		actions.doubleClick(element).build().perform();
	}
	
	public void rightClick(WebElement element) {
		actions = new Actions(driver);
		actions.contextClick(element).build().perform();
	}
	
	public void openNewTab(String URL) {
		driver.switchTo().newWindow(WindowType.TAB).get(URL);
	}
	
	public void closeTab() {
		if(driver!=null)
			driver.close();
	}
	
	public void closeBrowser() {
		if(driver!=null)
			driver.quit();
	}
	
	public void pressTab(int noOfTimes) throws AWTException {
		robot = new Robot();
		for(int i=0; i < noOfTimes; i++) {
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
		}
	}
	
	public String ifAlertIsPresent(WebDriver driver, AlertBox alertBox, String text) {
		String alertText = null;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		if(wait.until(ExpectedConditions.alertIsPresent())==null) {
			System.err.println("Alert is not present");
			return null;
		}
		else {
				switch (alertBox) {
					case ACCEPT:
						acceptAlert();
					break;
					
					case DECLINE:
						declineAlert();
					break;
					
					case ENTER_TEXT:
						typeTextinAlert(text);
					break;
					
					case GET_TEXT:
						alertText = getAlertText();
					break;
			}
		}
		return alertText;
	}
	
	public void scroll() {
		executor = (JavascriptExecutor) driver;
		executor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	public void scroll(WebElement element) {
		executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].scrollIntoView();", element);
	}
	
	public void forceclick(WebElement element) {
		executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}
	
	public void javaScript(String script, WebElement element) {
		executor = (JavascriptExecutor) driver;
		executor.executeScript(script, element);
	}
	
	public void javaScript(String script) {
		executor = (JavascriptExecutor) driver;
		executor.executeScript(script);
	}
	
	public void keyBoardOperations(KeyBoard keyBoard) throws AWTException {
		robot = new Robot();
		switch (keyBoard) {
			case PAGE_DOWN:
				robot.keyPress(KeyEvent.VK_PAGE_DOWN);
				robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			break;
			
			case PAGE_UP:
				robot.keyPress(KeyEvent.VK_PAGE_UP);
				robot.keyRelease(KeyEvent.VK_PAGE_UP);
			break;
			
			case SELECT_ALL:
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_A);
				robot.keyRelease(KeyEvent.VK_A);
				robot.keyRelease(KeyEvent.VK_CONTROL);
			break;
			
			case DOWN:
				robot.keyPress(KeyEvent.VK_DOWN);
				robot.keyRelease(KeyEvent.VK_DOWN);
			break;
			
			case UP:
				robot.keyPress(KeyEvent.VK_UP);
				robot.keyRelease(KeyEvent.VK_UP);
			break;
			
			case LEFT:
				robot.keyPress(KeyEvent.VK_LEFT);
				robot.keyRelease(KeyEvent.VK_LEFT);
			break;
			
			case RIGHT:
				robot.keyPress(KeyEvent.VK_RIGHT);
				robot.keyRelease(KeyEvent.VK_RIGHT);
			break;
			
			case SPACE:
				robot.keyPress(KeyEvent.VK_SPACE);
				robot.keyRelease(KeyEvent.VK_SPACE);
			break;
			
			case BACKSPACE:
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
			break;
		}
	}
	
	public static void enterText(String text) throws AWTException {
		robot = new Robot();
		for (int i = 0; i < text.length(); i++) {
			char charAt = text.charAt(i);
			switch (charAt) {
				case 'A':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_A);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_A);
				break;
				
				case 'a':
					robot.keyPress(KeyEvent.VK_A);
					robot.keyRelease(KeyEvent.VK_A);
				break;
				
				case 'B':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_B);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_B);
				break;
				
				case 'b':
					robot.keyPress(KeyEvent.VK_B);
					robot.keyRelease(KeyEvent.VK_B);
				break;
				
				case 'C':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_C);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_C);
				break;
				
				case 'c':
					robot.keyPress(KeyEvent.VK_C);
					robot.keyRelease(KeyEvent.VK_C);
				break;
				
				case 'D':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_D);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_D);
				break;
				
				case 'd':
					robot.keyPress(KeyEvent.VK_D);
					robot.keyRelease(KeyEvent.VK_D);
				break;
				
				case 'E':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_E);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_E);
				break;
				
				case 'e':
					robot.keyPress(KeyEvent.VK_E);
					robot.keyRelease(KeyEvent.VK_E);
				break;
				
				case 'F':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_F);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_F);
				break;
				
				case 'f':
					robot.keyPress(KeyEvent.VK_F);
					robot.keyRelease(KeyEvent.VK_F);
				break;
				
				case 'G':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_G);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_G);
				break;
				
				case 'g':
					robot.keyPress(KeyEvent.VK_G);
					robot.keyRelease(KeyEvent.VK_G);
				break;
				
				case 'H':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_H);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_H);
				break;
				
				case 'h':
					robot.keyPress(KeyEvent.VK_H);
					robot.keyRelease(KeyEvent.VK_H);
				break;
				
				case 'I':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_I);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_I);
				break;
				
				case 'i':
					robot.keyPress(KeyEvent.VK_I);
					robot.keyRelease(KeyEvent.VK_I);
				break;
				
				case 'J':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_J);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_J);
				break;
				
				case 'j':
					robot.keyPress(KeyEvent.VK_J);
					robot.keyRelease(KeyEvent.VK_J);
				break;
				
				case 'K':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_K);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_K);
				break;
				
				case 'k':
					robot.keyPress(KeyEvent.VK_K);
					robot.keyRelease(KeyEvent.VK_K);
				break;
				
				case 'L':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_L);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_L);
				break;
				
				case 'l':
					robot.keyPress(KeyEvent.VK_L);
					robot.keyRelease(KeyEvent.VK_L);
				break;
				
				case 'M':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_M);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_M);
				break;
				
				case 'm':
					robot.keyPress(KeyEvent.VK_M);
					robot.keyRelease(KeyEvent.VK_M);
				break;
				
				case 'N':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_N);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_N);
				break;
				
				case 'n':
					robot.keyPress(KeyEvent.VK_N);
					robot.keyRelease(KeyEvent.VK_N);
				break;
				
				case 'O':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_O);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_O);
				break;
				
				case 'o':
					robot.keyPress(KeyEvent.VK_O);
					robot.keyRelease(KeyEvent.VK_O);
				break;
				
				case 'P':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_P);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_P);
				break;
				
				case 'p':
					robot.keyPress(KeyEvent.VK_P);
					robot.keyRelease(KeyEvent.VK_P);
				break;
				
				case 'Q':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_Q);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_Q);
				break;
				
				case 'q':
					robot.keyPress(KeyEvent.VK_Q);
					robot.keyRelease(KeyEvent.VK_Q);
				break;
				
				case 'R':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_R);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_R);
				break;
				
				case 'r':
					robot.keyPress(KeyEvent.VK_R);
					robot.keyRelease(KeyEvent.VK_R);
				break;
				
				case 'S':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_S);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_S);
				break;
				
				case 's':
					robot.keyPress(KeyEvent.VK_S);
					robot.keyRelease(KeyEvent.VK_S);
				break;
				
				case 'T':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_T);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_T);
				break;
				
				case 't':
					robot.keyPress(KeyEvent.VK_T);
					robot.keyRelease(KeyEvent.VK_T);
				break;
				
				case 'U':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_U);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_U);
				break;
				
				case 'u':
					robot.keyPress(KeyEvent.VK_U);
					robot.keyRelease(KeyEvent.VK_U);
				break;
				
				case 'V':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_V);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_V);
				break;
				
				case 'v':
					robot.keyPress(KeyEvent.VK_V);
					robot.keyRelease(KeyEvent.VK_V);
				break;
				
				case 'W':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_W);
				break;
				
				case 'w':
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
				break;
				
				case 'X':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_X);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_X);
				break;
				
				case 'x':
					robot.keyPress(KeyEvent.VK_X);
					robot.keyRelease(KeyEvent.VK_X);
				break;
				
				case 'Y':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_Y);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_Y);
				break;
				
				case 'y':
					robot.keyPress(KeyEvent.VK_Y);
					robot.keyRelease(KeyEvent.VK_Y);
				break;
				
				case 'Z':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_Z);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_Z);
				break;
				
				case 'z':
					robot.keyPress(KeyEvent.VK_Z);
					robot.keyRelease(KeyEvent.VK_Z);
				break;
				
				case '0':
					robot.keyPress(KeyEvent.VK_0);
					robot.keyRelease(KeyEvent.VK_0);
				break;
				
				case '1':
					robot.keyPress(KeyEvent.VK_1);
					robot.keyRelease(KeyEvent.VK_1);
				break;
				
				case '2':
					robot.keyPress(KeyEvent.VK_2);
					robot.keyRelease(KeyEvent.VK_2);
				break;
				
				case '3':
					robot.keyPress(KeyEvent.VK_3);
					robot.keyRelease(KeyEvent.VK_3);
				break;
				
				case '4':
					robot.keyPress(KeyEvent.VK_4);
					robot.keyRelease(KeyEvent.VK_4);
				break;
				
				case '5':
					robot.keyPress(KeyEvent.VK_5);
					robot.keyRelease(KeyEvent.VK_5);
				break;
				
				case '6':
					robot.keyPress(KeyEvent.VK_6);
					robot.keyRelease(KeyEvent.VK_6);
				break;
				
				case '7':
					robot.keyPress(KeyEvent.VK_7);
					robot.keyRelease(KeyEvent.VK_7);
				break;
				
				case '8':
					robot.keyPress(KeyEvent.VK_8);
					robot.keyRelease(KeyEvent.VK_8);
				break;
				
				case '9':
					robot.keyPress(KeyEvent.VK_9);
					robot.keyRelease(KeyEvent.VK_9);
				break;
				
				case ' ':
					robot.keyPress(KeyEvent.VK_SPACE);
					robot.keyRelease(KeyEvent.VK_SPACE);
				break;
				
				case '@':
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_2);
					robot.keyRelease(KeyEvent.VK_2);
					robot.keyRelease(KeyEvent.VK_SHIFT);
				break;
				case '.':
					robot.keyPress(KeyEvent.VK_PERIOD);
					robot.keyRelease(KeyEvent.VK_PERIOD);
				break;
			}
		}
	}
}