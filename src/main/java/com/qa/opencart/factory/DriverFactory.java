package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {

	public WebDriver driver;

	public Properties prop;

	public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();

	public static final Logger log = LogManager.getLogger(DriverFactory.class);

	public OptionsManager optionsManager;

	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");

		log.info("browser name : " + browserName);

		optionsManager = new OptionsManager(prop); 
		
		

		boolean remoteExecution = Boolean.parseBoolean(prop.getProperty("remote").trim()); 
		

		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			if (remoteExecution) {
 
				System.out.println("in remote block");
				// run tcs on remote - grid
				init_remoteDriver("chrome");

			} else { 
				System.out.println("in local block");
				// run tcs in local
				tldriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
			break;
		case "firefox":
			if (remoteExecution) {

				// run tcs on remote - grid
				init_remoteDriver("firefox");

			} else {
				// run tcs in local
				tldriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}
			break;
		case "edge":

			if (remoteExecution) {

				// run tcs on remote - grid
				init_remoteDriver("edge");

			} else {
				tldriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}
			break;
		case "safari":

			tldriver.set(new SafariDriver());
			break;

		default:

			log.error(AppError.INVALID_BROWSER_MESG + " : " + browserName);
			throw new FrameworkException("=====INVALID BROWSER====");
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}

	private void init_remoteDriver(String browserName) {
		log.info("Running tests on selenoum grid --"+ browserName);

		try {
			switch (browserName) {
			case "chrome":
				tldriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
				break;
				
			case "firefox":
				tldriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
				break;
				
			case "edge":
				tldriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
				break;
				
			default:
				log.error("Plz supply the right browser name for selenium grid....");
				FrameworkException fe = new FrameworkException(AppError.INVALID_BROWSER_MESG + " : " + browserName);
				log.error("Exception occurred while initializing driver: ", fe);
				throw new FrameworkException("=====INVALID BROWSER====");
			}
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}


	public static WebDriver getDriver() {
		return tldriver.get();
	}

	public Properties initProp() {

		prop = new Properties();
		FileInputStream ip = null;

		String envName = System.getProperty("env");
		log.info("Env name =========================================================================>" + envName);

		try {
			if (envName == null) {
				log.warn("no env.. is passed, hence running tcs on QA environment...by default..");
				ip = new FileInputStream("./src/test/resources/config/config.uat.properties");
			}

			else {
				switch (envName.trim().toLowerCase()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/config.stage.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/config.uat.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/config.dev.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;
				default:
					log.error("Env value is invalid...plz pass the right env value..");
					throw new FrameworkException("====INVALID ENVIRONMENT====");
				}

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();

		}
		try {
			prop.load(ip);
		}

		catch (IOException e) {

			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * takescreenshot
	 */

	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		return srcFile;
	}

	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir

	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir

	}
}
