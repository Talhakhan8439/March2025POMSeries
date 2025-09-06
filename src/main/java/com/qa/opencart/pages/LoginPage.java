package com.qa.opencart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil; 
	
	public static final Logger log = LogManager.getLogger(LoginPage.class);

	// private By locators: page objects

	private final By emailID = By.id("input-email");
	private final By password = By.id("input-password");
	private final By loginBtn = By.xpath("//input[@value='Login']");
	private final By forgotPwdLink = By.linkText("Forgotten Password");
	private final By header = By.tagName("h2");
	private final By registerLink = By.linkText("Register");

	// public constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	
	
	@Step("This logic method to get title from login page")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_SHORT_WAIT);
//		System.out.println("login page title: " + title);
		log.info("login page title: " + title);
		return title;
	}

	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_FRACTION_URL, AppConstants.DEFAULT_SHORT_WAIT);
//		System.out.println("login page url: " + url); 
		log.info("login page url: " + url);
		return url;
	}

	public boolean isForgotPwdLinkExist() {

		return eleUtil.isElementDisplayed(forgotPwdLink);

	}

	public boolean isheaderExist() {
		return eleUtil.isElementDisplayed(header);
	}

	public AccountsPage doLogin(String appUsername, String appPassword) {
//		System.out.println("Application credentials: " + appUsername + ":" + appPassword); 
		log.info("Application credentials: " + appUsername + ":" + appPassword);
		eleUtil.waitForElementVisible(emailID, AppConstants.DEFAULT_MEDIUM_WAIT).sendKeys(appUsername);
		
		eleUtil.doSendKeys(password, appPassword);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);

	}

	public RegisterPage navigateToRegisterPage() {
		eleUtil.waitForElementPresence(registerLink, AppConstants.DEFAULT_SHORT_WAIT).click();
		return new RegisterPage(driver);
	}
}
