package com.tinpatiti.automation.base;

import com.tinpatiti.automation.constants.ApplicationConstants;
import com.tinpatiti.automation.util.SeleniumUtil;
import com.tinpatiti.util.ReadResources;
import org.apache.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;
import java.util.concurrent.TimeUnit;

//import static org.graalvm.compiler.options.OptionType.User;

public class InitializeResource extends TestBase {

	private static final Logger LOGGER = Logger.getLogger(InitializeResource.class);

	public void userLogin(final String userName, final String password) throws InterruptedException {
		userLogin(userName, password, ApplicationConstants.defaultTimeout, ApplicationConstants.defaultPollingFrequency);
	}
	public void userLogin(final String userName, final String password, final long timeout,
			final long pollingFrequency) throws InterruptedException {
		//try {
//			SendEmail notify = new SendEmail();
//			notify.sendEmail();
			LOGGER.info("Waiting for page");
			Thread.sleep(12000);
			//SeleniumUtil.waitForElementPresence(getWebDriverWait(),getProps().getProperty("userNameLocator"));
			//LOGGER.info("Page loaded");
			//List<WebElement> loginLocators = SeleniumUtil.getMultipleElementsAfterLoaded(getWebDriver(),"//input");
			//LOGGER.info(loginLocators);
			//System.out.println(loginLocators);
			//SeleniumUtil.enterText(loginLocators.get(0), userName);
			LOGGER.info(getProps().getProperty("userNameLocator"));
			//LOGGER.info(getWebDriver().findElement(By.xpath(getProps().getProperty("userNameLocator"))));
			//SeleniumUtil.enterText(getWebDriver(),getProps().getProperty("userNameLocator"));
			//Thread.sleep(8000);
			//SeleniumUtil.explicitWait(8000);
			SeleniumUtil.enterText(getWebDriver(), getProps().getProperty("userNameLocator"), userName, 30,5);
			randomSleep();
			//SeleniumUtil.enterText(loginLocators.get(1), password);
			LOGGER.info("User Name Filled");
			randomSleep();
			SeleniumUtil.enterText(getWebDriver(), getProps().getProperty("passwordLocator"), password, 30,5);
			LOGGER.info("Password Filled");
			//SeleniumUtil.click();
			SeleniumUtil.click(getWebDriverWait(), getWebDriver(), getProps().getProperty("loginButtonLocator"), 30,
					5);
			LOGGER.info("Login Clicked");
			Thread.sleep(5000);
			randomSleep();
			SeleniumUtil.clickElement(getWebDriver(),getProps().getProperty("ageconfirmationLocator"), 30, 5);
			randomSleep();
			getWebDriver().get("http://tenexch.com/teenpatti/twentytwenty");
			Thread.sleep(5000);
			// wait for spinner to come
//			try {
//				SeleniumUtil.spinnerWait(getWebDriverWait(), getWebDriver(), getReadConfigData().get("loginSpinner"), timeout,
//						pollingFrequency);
//			} catch (Exception e) {
//				CustomReport.reportError("Exception ::" + e.getMessage());
//			}
		//} catch (Exception e) {
		//	CustomReport.reportError(e.getMessage());
		//}
	}

	public static void randomSleep() {
		Random random = new Random();
		int time = random.nextInt(10);
		try {
			Thread.sleep(time * 1000);
		} catch (Exception e) {
			System.out.println("Exception");
		}
	}

	public void launchURL(final String testCaseModule) {
		LOGGER.info("**********************Initializing the test case for :: [" + testCaseModule
				+ "]*******************************");
		ReadResources readResources;
		try {
			if (null == getWebDriver()) {
				readResources = new ReadResources();
				setReadConfigData(readResources.getValuesFromXml("Configuration.xml", "TENEXCH"));
				setReadConfigUrls(readResources.getValuesFromXml("Configuration.xml", "Environment_URl"));
				setDatabaseConfigMap(readResources.getValuesFromXml("DBConfiguration.xml", "DataBaseConfig"));

				LOGGER.info("::::: Initializing browser and navigating to URL :::::");

				initializeGlobalVariables();
				setWebDriver(initializeWebDriver(browser));
				getWebDriver().manage().deleteAllCookies();
				setWebDriverWait(new  WebDriverWait(getWebDriver(), TimeUnit.MILLISECONDS.toSeconds(timeout)));
				setProps(ApplicationConstants.XPATH_PROPS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String url = getEnvUrl();
		try {
			getWebDriver().get(url);
		} catch (TimeoutException e) {
			setWebDriver(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
