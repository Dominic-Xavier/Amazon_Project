package com.testNgClass;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.Baseclass.CloudConnection;
import com.Baseclass.DesktopTestBase;
import com.Baseclass.MobileTestBase;
import com.customException.BrowserException;
import com.excelSheet.DataProviders;

import enumVariales.AUTOMATOR;
import enumVariales.BrowserName;
import enumVariales.CloudPlatform;
import enumVariales.MobileOS;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserDriver extends DataProviders {

	public static WebDriver driver;
	private static String browserName;
	public static DataProviders dp = new DataProviders();
	public static AppiumDriver androidDriver;
	
	@BeforeClass
	public WebDriver getRemoteDriver() throws IOException {
		System.err.println("Selenium Grid required? "+dp.getData("Selenium Grid"));
		if(dp.getData("Selenium Grid").toLowerCase().equals("yes")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			String oSName = System.getProperty("os.name");
			cap.setBrowserName(dp.getData("Browser names").toLowerCase());
			if(oSName.toLowerCase().contains("windows"))
				cap.setPlatform(Platform.WINDOWS);
			else if(oSName.toLowerCase().contains("linux"))
				cap.setPlatform(Platform.LINUX);
			else if(oSName.toLowerCase().contains("mac"))
				cap.setPlatform(Platform.MAC);
			String browserNamelist = dp.getData("Browser names");
			String[] split = browserNamelist.split(";;");
			for (String browserName : split) {
				switch (browserName.toUpperCase()) {
				case "CHROME":
					cap.setBrowserName(browserName);
					driver = new RemoteWebDriver(new URL(dp.getData("Hub Url")), cap);
				break;
				
				case "FIREFOX":
					cap.setBrowserName(browserName);
					driver = new RemoteWebDriver(new URL(dp.getData("Hub Url")), cap);
				break;
				
				default:
					break;
				}
			}
		}
		return driver;
	}
	
	@Test
	@Parameters("Browsername")
	private void browserName(@Optional("Chrome")String browserNames) {
		System.out.println("Browsername is "+browserNames);
		browserName = browserNames;
	}
	
	public static String getAppName() {
		return browserName;
	}
	
	//User to launch WebApp
	public static WebDriver Initialize(String browserName, String URL) throws BrowserException, IOException {
		System.out.println("Browser name in Initialize is "+browserName);
		switch (browserName.toUpperCase()) {
		case "CHROME":
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--no-sandbox");
			options.addArguments("--headless"); //!!!should be enabled for Jenkins
			options.addArguments("--disable-dev-shm-usage"); //!!!should be enabled for Jenkins
			options.addArguments("--window-size=1920x1080"); //!!!should be enabled for Jenkins
			WebDriver chrome = new ChromeDriver(options);
			chrome.manage().window().maximize();
			chrome.get(URL);
			driver = chrome;
		return chrome;
		
		case "FIREFOX":
			WebDriverManager.firefoxdriver().setup();
			WebDriver firefox = new FirefoxDriver();
			firefox.manage().window().maximize();
			firefox.get(URL);
			driver = firefox;
		return firefox;
			
		case "IE":
			WebDriverManager.iedriver().setup();
			WebDriver ieDriver = new InternetExplorerDriver();
			ieDriver.manage().window().maximize();
			ieDriver.get(URL);
			driver = ieDriver;
		return ieDriver;
			
		case "SAFARI":
			WebDriverManager.safaridriver().setup();
			WebDriver safari = new SafariDriver();
			safari.manage().window().maximize();
			safari.get(URL);
			driver = safari;
		return safari;
		
		case "EDGE":
			WebDriverManager.edgedriver().setup();
			WebDriver edge = new EdgeDriver();
			edge.manage().window().maximize();
			edge.get(URL);
			driver = edge;
		return edge;
			
		case "OPERA":
			WebDriverManager.operadriver().setup();
			WebDriver opera = new OperaDriver();
			opera.manage().window().maximize();
			opera.get(URL);
			driver = opera;
		return opera;
		
		case "DESKTOP":
			driver = new DesktopTestBase().getWiniumDriver();
		return driver;
		
		default:
			throw new BrowserException();
		}
	}
	
	
	//Used to launch Mobile app
	public AppiumDriver MobileDevice(String OSName) throws IOException {
		switch (OSName) {
		case "Android":
			String deivceName = dp.getData("Device Name");
			String UdId = dp.getData("UDID");
			String PlatformName = dp.getData("Platform Name");
			String PlatformVersion = dp.getData("Platform Version");
			String AppPackage = dp.getData("App Package");
			String AppActivity = dp.getData("App Activity");
			String Ip = dp.getData("Ip Address");
			String Port = dp.getData("Port");
			String Path = dp.getData("Path");
			androidDriver = new MobileTestBase().launchAndroidApp(deivceName, UdId, PlatformName, PlatformVersion, AppPackage, 
					AppActivity, AUTOMATOR.UIautomator2, Ip, Port, Path);
			break;
		
		case "iOS":
			String deivceName1 = dp.getData("Device Name");
			String UdId1 = dp.getData("UDID");
			String PlatformName1 = dp.getData("Platform Name");
			String PlatformVersion1 = dp.getData("Platform Version");
			String AppPackage1 = dp.getData("App Package");
			String AppActivity1 = dp.getData("App Activity");
			String Ip1 = dp.getData("Ip Address");
			String Port1 = dp.getData("Port");
			String Path1 = dp.getData("Path");
			androidDriver = new MobileTestBase().launchAndroidApp(deivceName1, UdId1, PlatformName1, PlatformVersion1, AppPackage1, 
					AppActivity1, AUTOMATOR.XCUITest, Ip1, Port1, Path1);
			break;
		}
		return androidDriver;
	}
	
	public WebDriver executeTestInCloud(CloudPlatform platform, String browserName) throws IOException, BrowserException {
		switch (platform) {
		
			case BROWSERSTACK:
				String userrname1 = dp.getData("username stack");
				String accessKey1 = dp.getData("accessKey stack");
				String url1 = dp.getData("URL BrowserStack");
				String testName = dp.getData("Test Name");
				String osName = dp.getData("Stack Os Name");
				String osVersion = dp.getData("Stack Os Version");
				String browserVersion1 = dp.getData("stack browser version");
				driver = CloudConnection.connectwithBrowserStack(userrname1, accessKey1, url1, testName, osName, osVersion, browserName, browserVersion1);
			break;
				
			case SAUCELABS:
				String buildName = dp.getData("build");
				String SeleniumVersion = dp.getData("seleniumVersion");
				String userrname = dp.getData("username");
				String accessKey = dp.getData("accessKey"); 
				String tags = dp.getData("tags");
				String url = dp.getData("URL Sauce");
				String browserVersion = dp.getData("Browser Version");
				String oSName = dp.getData("Os Name");
				driver = CloudConnection.connectWithSauceLabs(buildName, SeleniumVersion, oSName, userrname, accessKey, tags, browserName, browserVersion, url);
			break;
		}
		return driver;
	}
	
	public void sauceLabs(BrowserName browserName) throws MalformedURLException {
		driver = CloudConnection.connectWithSauceLabs(browserName);
	}
	
	public WebDriver getWebDriver() {
		return driver;
	}
	
	/*@Test(priority = 0)
	private void browser() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.google.com");
		System.out.println("THread ID is "+Thread.currentThread().getId());
		driver.findElement(By.name("q")).sendKeys("Trisha");
	}
	
	@Test(priority = 1)
	private void browser2() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.google.com");
		System.out.println("THread ID is "+Thread.currentThread().getId());
		driver.findElement(By.name("q")).sendKeys("Samantha");
	}*/
}
