package com.tinpatiti.automation.util;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class LogAndScreenShotUtil {

	public static void logError(final WebDriver webDriver, final String message, final Class<?> clazz, final String methodName) {
		logErrorWithoutAssert(webDriver, message, null, clazz, methodName);
		assert false;
	}

	public static void logError(final WebDriver webDriver, final String message, final Exception e,
			final Class<?> clazz, final String methodName) {
		logErrorWithoutAssert(webDriver, message, e, clazz, methodName);
		assert false;
	}

	public static void logErrorWithoutAssert(final WebDriver webDriver, final String message, final Exception e,
			final Class<?> clazz, final String methodName) {
		final Logger logger = Logger.getLogger(clazz);
		logger.error(message);
		CustomReport.reportError(message);
		if (null != e && e instanceof Exception) {
			e.printStackTrace();
		}
		SeleniumUtil.saveFailedScreenshot(webDriver, methodName.concat("_").concat("" + System.currentTimeMillis()));
	}

	public static void logErrorAndWaitBeforeAssert(final WebDriver webDriver, final String message, final Exception e,
			final Class<?> clazz, final String methodName, long wait) {
		final Logger logger = Logger.getLogger(clazz);
		logger.error(message);
		CustomReport.reportError(message);
		if (null != e && e instanceof Exception) {
			e.printStackTrace();
		}
		SeleniumUtil.saveFailedScreenshot(webDriver, methodName.concat("_").concat("" + System.currentTimeMillis()));
		SeleniumUtil.explicitWait(wait);
		assert false;
	}

	public static void logPass(final WebDriver webDriver, final String message, final Class<?> clazz,
			final String methodName, boolean isScreenshotRequired) {
		final Logger logger = Logger.getLogger(clazz);
		logger.info(message);
		CustomReport.reportPass(message);
		if (isScreenshotRequired) {
			SeleniumUtil.saveFailedScreenshot(webDriver,
					methodName.concat("_").concat("" + System.currentTimeMillis()));
		}
	}

	public static void logPass(final WebDriver webDriver, final String message, final Class<?> clazz,
			final String methodName) {
		logPass(webDriver, message, clazz, methodName, false);
	}

	public static void log(final WebDriver webDriver, final String message, final Class<?> clazz,
							   final String methodName) {
		final Logger logger = Logger.getLogger(clazz);
		logger.info(message);
		CustomReport.reporter(message);
	}
}
