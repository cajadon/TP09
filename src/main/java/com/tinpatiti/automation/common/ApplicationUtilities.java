package com.tinpatiti.automation.common;

import com.tinpatiti.automation.util.CustomReport;
import com.tinpatiti.automation.util.LogAndScreenShotUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;

public class ApplicationUtilities {

    private static final Logger LOGGER = Logger.getLogger(ApplicationUtilities.class);

    public void closeTheBrowser(WebDriver webDriver) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.info("Executing Test Step :: " + methodName);
        CustomReport.reporter("Executing Test Step :: " + methodName);

        try {
            if (null != webDriver) {
                webDriver.close();
                webDriver.quit();
            }
        } catch (TimeoutException e) {
            LOGGER.error(
                    "******* Time out exception occurred while accessing webdriver : " + e.getMessage() + " *******");
        } catch (UnhandledAlertException e) {
            Alert alert = webDriver.switchTo().alert();
            alert.dismiss();
        } catch (Exception e) {
            LOGGER.error("Error occurred while closing the browser after test module execution : " + e.getMessage());
        }
    }


    public boolean customizedReport(final WebDriver webDriver, final String sTestcaseName, final String sExpected,
                                    final String sActual, final boolean isScreenshotRequiredWhenPass) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.info("Executing Test Step :: " + methodName);
        CustomReport.reporter("Executing Test Step :: " + methodName);

        LOGGER.info("Generating report for Test case [" + sTestcaseName + "] with Expected [" + sExpected.trim()
                + "], Actual [" + sActual.trim() + "]");
        if ((sExpected.trim()).equalsIgnoreCase(sActual.trim())) {
            LogAndScreenShotUtil.logPass(webDriver,
                    "Expected Value : " + sExpected + "</br>Actual Value :" + sActual + "::PASS", getClass(),
                    methodName, isScreenshotRequiredWhenPass);
            return true;
        } else {
            LogAndScreenShotUtil.logErrorWithoutAssert(webDriver,
                    "Expected Value " + sExpected + "\nActual Value" + sActual + "::Fail", null, getClass(),
                    methodName);
        }
        return false;
    }

    public boolean customizedReport(final WebDriver webDriver, final String testcaseName, final boolean expected,
                                    final boolean actual) {
        return customizedReport(webDriver, testcaseName, expected, actual, false);
    }
    public boolean customizedReport(final WebDriver webDriver, final String testcaseName, final String expected,
                                    final String actual) {
        return customizedReport(webDriver, testcaseName, expected, actual, false);
    }

    public boolean customizedReport(final WebDriver webDriver, final String testcaseName, final boolean expected,
                                    final boolean actual, final boolean isScreenshotRequiredWhenPass) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.info("Executing Test Step :: " + methodName);
        CustomReport.reporter("Executing Test Step :: " + methodName);

        LOGGER.info("Generating report for Test case [" + testcaseName + "] with Expected [" + expected
                + "], Actual [" + actual + "]");
        if (expected == actual) {
            LogAndScreenShotUtil.logPass(webDriver,
                    "Expected Value : " + expected + "</br>Actual Value :" + actual + "::PASS", getClass(),
                    methodName, isScreenshotRequiredWhenPass);
            return true;
        } else {
            LogAndScreenShotUtil.logErrorWithoutAssert(webDriver,
                    "Expected Value " + expected + "\n Actual Value" + actual + "::Fail", null, getClass(),
                    methodName);
        }
        return false;
    }

}
