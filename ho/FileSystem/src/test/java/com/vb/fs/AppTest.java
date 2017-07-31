package com.vb.fs;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.LogStatus;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.vb.fs.misc.AbstractReader;
import com.vb.fs.misc.ReaderFactory;
import com.vb.fs.util.NotADirectoryException;

public class AppTest {

	public AppTest() {
		// loading configuration properties, like file paths.
		try {
			config = new CompositeConfiguration();
			config.addConfiguration(new SystemConfiguration());
			config.addConfiguration(new PropertiesConfiguration("config.properties"));
		} catch (Exception ex) {
			System.out.println("Could not load config.properties file. Please fix and continue");
		}
	}

	@BeforeTest
	@Parameters("browser")
	public void setup(String browserName) throws Exception {
		if (browserName.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", config.getString("webdriver.firefox", null));
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		} else if (browserName.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", config.getString("webdriver.chrome", null));
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.edge.driver", config.getString("webdriver.ie", null));
			driver = new EdgeDriver();
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		} else if (browserName.equalsIgnoreCase("Safari")) {
			// System.setProperty("webdriver.safari.driver",
			// config.getString("webdriver.safari", null));
			driver = new SafariDriver();
		} else {
			throw new Exception("Browser is not correct");
		}
	}

	CompositeConfiguration config;
	// Declare Web Driver variables
	WebDriver driver;
	// Declare Extent Reports
	ExtentReports reports;

	ExtentTest test = null;

	@Test(dataProvider = "dp")
	public void regTest(String plateNumber, String make, String color) throws Exception {

		driver.get("https://www.gov.uk/get-vehicle-information-from-dvla");

		// Maximize the window
		driver.manage().window().maximize();

		// Java script code
		JavascriptExecutor js = (JavascriptExecutor) driver;
		//Click on Start button
		js.executeScript("document.getElementsByClassName('get-started group')[0].getElementsByTagName('a')[0].click()");
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id='Vrm']")));
		// Initiate Extent Reports
		Locale usLocale = new Locale("en-US");
		reports = new ExtentReports(config.getString("reports.extent.html", null), false, DisplayOrder.NEWEST_FIRST, usLocale);
		
		// Declare Start test name
		test = reports.startTest("Get vehicle information from DVLA");
		test.log(LogStatus.PASS, "Test Case 1: Browser is opened and window is Maximized and DVLA home page is displayed.");
		driver.findElement(By.cssSelector("input[id='Vrm']")).sendKeys(plateNumber);
		driver.findElement(By.cssSelector("button[name='Continue']")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException - during thread sleep");
			e.printStackTrace();
		}
		System.out.println("\n*** Test Case Output ***");
		String webReg = (String) js
				.executeScript("return document.getElementsByClassName('list-summary-item')[0].getElementsByTagName('span')[1].innerHTML;");
		System.out.println("webReg=" + webReg);
		Assert.assertEquals(plateNumber, webReg);

		String webMake = (String) js
				.executeScript("return document.getElementsByClassName('list-summary-item')[1].getElementsByTagName('span')[1].getElementsByTagName('strong')[0].innerHTML;");
		System.out.println("webMake=" + webMake);
		Assert.assertEquals(make, webMake);

		String webColor = (String) js
				.executeScript("return document.getElementsByClassName('list-summary-item')[2].getElementsByTagName('span')[1].getElementsByTagName('strong')[0].innerHTML;");
		System.out.println("webColor=" + webColor);
		Assert.assertEquals(color, webColor);
		test.log(LogStatus.PASS, "Test Case *: Make/Model/Color is verified");
	}

	@BeforeMethod
	public void beforeMethod() {

	}

	@AfterTest
	public void afterTest() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException - during thread sleep");
			e.printStackTrace();
		}
		driver.quit();
		reports.endTest(test);
		reports.flush();
		reports.close();
	}

	@DataProvider
	public Object[][] dp() throws NotADirectoryException, IOException {
		Object[][] testData = null;
		List<String> lines = null;
		//String localPath = "/dvla";
		String localPath = config.getString("sample.file.path", null);
		FileServiceInjector injector = null;
		Consumer app = null;

		injector = new FileServiceInjectorImpl();
		app = injector.getConsumer();
		List<String> fileList = app.retrieveFileList(localPath);
		for (int i = 0; i < fileList.size(); i++) {
			System.out.println(i + "\t" + fileList.get(i));
		}

		Iterator<String> ite = fileList.iterator();
		while (ite.hasNext()) {
			String current = ite.next();
			File cFile = new File(current);
			System.out.println("\nFile Service API Testing");
			System.out.println("Name = " + app.retrieveFileName(cFile));
			System.out.println("Mime = " + app.retrieveMimeType(cFile));
			System.out.println("Size = " + app.retrieveFileSize(cFile));
			System.out.println("Extn = " + app.retrieveFileExtn(cFile));
		}
		String filters = "csv,xlsx";
		File[] list = app.retrieveFilteredFiles(new File(localPath), filters);
		System.out.println("Current filters = " + filters);
		for (int i = 0; i < list.length; i++) {
			System.out.println(list[i].getAbsolutePath());
		}
		try {
			String sampleFile = config.getString("sample.file.test", null);
			String ext = "";
			if (sampleFile != null && sampleFile.trim().length() > 0) {
				ext = sampleFile.substring(sampleFile.lastIndexOf('.') + 1);
			}
			AbstractReader reader = ReaderFactory.getReader(null, ext);
			lines =reader.readFille(new File(sampleFile));

			System.out.println("Lines Count = " + lines.size());
			testData = new Object[lines.size()][3];
			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				String[] current = line.split(",");
				testData[i][0] = current[0].trim();
				testData[i][1] = current[1].trim();
				testData[i][2] = current[2].trim();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return testData;
	}
}