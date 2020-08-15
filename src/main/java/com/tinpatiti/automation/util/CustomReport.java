package com.tinpatiti.automation.util;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomReport {

	private static Logger LOGGER = Logger.getLogger(CustomReport.class);
	private static int i = 1;
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void reporter(String sValue, String sValue1) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.log("Step-" + i + " [" + simpleDateFormat.format(new Date()) + "] :" + sValue + sValue1 + "</br>");
		i++;
	}

	public static void reportError(String sValue, String sValue1) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.log("<font color='red'>Step-" + i + " [" + simpleDateFormat.format(new Date()) + "] :" + sValue
				+ sValue1 + "</font></br>");
		i++;
	}

	public static void reportPass(String sValue, String sValue1) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.log("<font color='green'><b>Step-" + i + " [" + simpleDateFormat.format(new Date()) + "] :" + sValue
				+ sValue1 + "</b></font></br>");
		i++;
	}

	public static void reporter(String sValue) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.log("Step-" + i + " [" + simpleDateFormat.format(new Date()) + "] :" + sValue + "</br>");
		i++;
	}

	public static void reportError(String sValue) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.log("<font color='red'>Step-" + i + " [" + simpleDateFormat.format(new Date()) + "] :" + sValue
				+ "</font></br>");
		i++;
	}

	public static void reportPass(String sValue) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.log("<font color='green'><b>Step-" + i + " [" + simpleDateFormat.format(new Date()) + "] :" + sValue
				+ "</b></font></br>");
		i++;
	}

	public static int getI() {
		return i;
	}

	public static void reset() {
		i = 1;
	}

	public static void verifyData(boolean actualValue, boolean expectedValue, WebDriver driver, String sTestCaseName,
			Class<?> clazz) {
		LOGGER.info("The Expected value is" + expectedValue);
		LOGGER.info("The Actual value is" + actualValue);
		LOGGER.info("The value for the object in the boolean format is" + String.valueOf(actualValue));
		if (actualValue == expectedValue) {
			reportPass("The Actual Value " + "<b>" + actualValue + "</b>" + " is equal to Expected Value" + "<b>"
					+ expectedValue + "</b" + "", "");
			assert true;
		} else {
			reportError("The Actual Value:: " + actualValue + " is not equal to Expected Value:: " + expectedValue + "",
					"");
			saveFailedScreenshot(driver, sTestCaseName, clazz);
			assert false;
		}
	}

	static int j = 1;

	/**
	 * Comparing two string values and determined whether the case is passed or
	 * failed
	 * 
	 * @param sExpected
	 * @param sActual
	 * @param statusValue
	 * @param driver
	 * @param sTestcaseName
	 * @param clazz
	 */
	public static void customizedReport(String sExpected, String sActual, List<String> statusValue, WebDriver driver,
			String sTestcaseName, Class<?> clazz) {
//		this.driver = driver;
		LOGGER.info("Expected value is" + sExpected);
		LOGGER.info("Actual  value  is" + sActual);
		LOGGER.info("Expected value after trim is" + sExpected.trim());
		LOGGER.info("Actual  value after trim is" + sActual.trim());

		if ((sExpected.trim()).equalsIgnoreCase(sActual.trim())) {
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			LOGGER.info("I am in customized report expected equals");
			LOGGER.info("driver value in customize report " + driver.toString());
			reporter("Expected Value : " + sExpected + "</br>" + "Actual Value :" + sActual + "::PASS", "");
			statusValue.add("PASS");

			LOGGER.info("sTestcaseName ::::" + sTestcaseName + "::::" + "Sequence:::::" + j);
			LOGGER.info("sTestcaseName" + sTestcaseName + "::::" + "PASSED");
			j = j + 1;

		} else {
			reporter("<font color='red'>" + "Expected Value " + sExpected + "\n" + "Actual Value" + sActual + "::Fail"
					+ "</font>", "");
			statusValue.add("FAIL");
			LOGGER.error("*************sTestcaseName" + sTestcaseName + "::::" + "FAILED");
			saveFailedScreenshot(driver, sTestcaseName, clazz);
		}
	}

	public static void customizedReport(boolean sExpected, boolean sActual, List<String> statusValue, WebDriver driver,
			String sTestcaseName, Class<?> clazz) {
//		this.driver = driver;

		LOGGER.info("@@@@Expected ::::" + sExpected);
		LOGGER.info("@@@@sActual ::::" + sActual);
		if (String.valueOf(sExpected).equals(String.valueOf(sActual))) {
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			LOGGER.info("I am in customized report expected equals");
			LOGGER.info("driver value in customize report " + driver.toString());
			reporter("Expected Value : " + sExpected + "</br>" + "Actual Value :" + sActual + "::PASS", "");
			statusValue.add("PASS");
			LOGGER.info("sTestcaseName" + sTestcaseName + "::::" + "PASSED");

		} else {
			reporter("<font color='red'>" + "Expected Value " + sExpected + "\n" + "Actual Value" + sActual + "::Fail"
					+ "</font>", "");
			statusValue.add("FAIL");
			LOGGER.error("*************sTestcaseName" + sTestcaseName + "::::" + "FAILED");
			saveFailedScreenshot(driver, sTestcaseName, clazz);
		}
	}

	public static void checkinglist(List<String> ls) {
		for (String temp : ls) {
			if (temp.equals("FAIL")) {

				assert false;
			}
		}
	}

	public static void saveFailedScreenshot(WebDriver driver, String imageFileName, Class<?> clazz) {
		imageFileName = clazz.getSimpleName() + "-" + imageFileName;
		// Taking and storing screenshot
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File imageDir = new File(new File("").getAbsolutePath() + "/target/surefire-reports/html/images");
		if (!imageDir.exists()) {
			imageDir.mkdirs();
		}
		File dest = new File(imageDir.getAbsolutePath() + "/" + imageFileName + ".png");
		try {
			FileUtils.copyFile(scrFile, dest);
			String path = "<img src=\"images/" + dest.getName() + "\" alt=\"Failed screenshot for " + imageFileName
					+ "\" />";
			Reporter.log(path);
		} catch (Exception e) {
			LOGGER.error("Exception occurred while saving failed screenshot for " + imageFileName, e);
			reportError("Exception occurred while saving failed screenshot for " + imageFileName + ". Exception is="
					+ e.getMessage());
		}
	}

}
