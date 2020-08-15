package com.tinpatiti.automation.util;

import com.tinpatiti.automation.constants.ApplicationConstants;
import com.google.common.base.Function;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.AssertJUnit;
import org.testng.Reporter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SeleniumUtil {
    private static final Logger LOGGER = Logger.getLogger(SeleniumUtil.class);

    public static void checkCheckBox(final WebDriver webDriver, final String objLocator) {
        checkCheckBox(webDriver, objLocator, ApplicationConstants.defaultTimeout,
                ApplicationConstants.defaultPollingFrequency);
    }

    public static void checkCheckBox(final WebDriver webDriver, final String objLocator, final long timeout,
                                     final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        WebElement element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
        if (!element.isEnabled()) {
            LOGGER.error("The checkbox with location [" + objLocator
                    + "] is not enable and cannot be clicked. Please verify the screenshot. Test cannot proceed.");
            CustomReport.reportError("The checkbox with location [" + objLocator
                    + "] is not enable and cannot be clicked. Please verify the screenshot. Test cannot proceed.");
            assert false;
        }
        if (!element.isSelected()) {
            element.click();
            LOGGER.debug("The Given Element is clicked");
        }
    }

    public static void checkCheckBox(final WebDriver webDriver, final String objLocator, final int index,
                                     final long timeout, final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        WebElement element = getMultipleElementsAfterLoaded(webDriver, objLocator, timeout, pollingFrequency)
                .get(index);
        if (!element.isEnabled()) {
            LOGGER.error("The checkbox with location [" + objLocator
                    + "] is not enable and cannot be clicked. Please verify the screenshot. Test cannot proceed.");
            CustomReport.reportError("The checkbox with location [" + objLocator
                    + "] is not enable and cannot be clicked. Please verify the screenshot. Test cannot proceed.");
            assert false;
        }
        if (!element.isSelected()) {
            element.click();
            LOGGER.debug("The Given Element is clicked");
        }
    }

    public static void unCheckCheckBox(final WebDriver webDriver, final String objLocator, final int index,
                                       final long timeout, final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        WebElement element = getMultipleElementsAfterLoaded(webDriver, objLocator, timeout, pollingFrequency)
                .get(index);
        if (!element.isEnabled()) {
            LOGGER.error("The checkbox with location [" + objLocator
                    + "] is not enable and cannot be clicked. Please verify the screenshot. Test cannot proceed.");
            CustomReport.reportError("The checkbox with location [" + objLocator
                    + "] is not enable and cannot be clicked. Please verify the screenshot. Test cannot proceed.");
            assert false;
        }
        if (element.isSelected()) {
            element.click();
            LOGGER.debug("The Given Element is clicked");
        }
    }

    public static void unCheckCheckBox(final WebDriver webDriver, String objLocator, final long timeout,
                                       final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        WebElement element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
        if (!element.isEnabled()) {
            LOGGER.error("The checkbox with location [" + objLocator
                    + "] is not enable and cannot be clicked. Please verify the screenshot. Test cannot proceed.");
            CustomReport.reportError("The checkbox with location [" + objLocator
                    + "] is not enable and cannot be clicked. Please verify the screenshot. Test cannot proceed.");
            assert false;
        }
        if (element.isSelected()) {
            element.click();
            LOGGER.debug("The Given Element is clicked");
        }
    }

    public static void click(final WebDriverWait webDriverWait, final WebDriver webDriver, final String objLocator) {
        click(webDriverWait, webDriver, objLocator, ApplicationConstants.defaultTimeout,
                ApplicationConstants.defaultPollingFrequency);
    }

    public static void click(final WebDriver webDriver, final WebDriverWait webDriverWait, final String objLocator) {
        click(webDriverWait, webDriver, objLocator, ApplicationConstants.defaultTimeout,
                ApplicationConstants.defaultPollingFrequency);
    }

    public static void click(final WebDriverWait webDriverWait, final WebDriver webDriver, final String objLocator,
                             final long timeout, final long pollingFrequency) {
        final long t1 = System.currentTimeMillis();
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.debug("Executing Test Step::" + methodName);

        long t3 = System.currentTimeMillis();
        WebElement element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
        if (null != element) {
            if (isElementClickable(webDriverWait, objLocator, timeout, pollingFrequency)) {
                element.click();
            } else {
                LogAndScreenShotUtil.logError(webDriver,
                        "Button with xpath [" + objLocator.split("===")[1] + "] is not clickable", null,
                        SeleniumUtil.class, methodName);
            }
        } else {
            LogAndScreenShotUtil.logError(webDriver,
                    "Button with xpath [" + objLocator.split("===")[1] + "] is not available on the page", null,
                    SeleniumUtil.class, methodName);
        }
        long t4 = System.currentTimeMillis();
        LOGGER.debug("Time taken in click on the element only : " + (t4 - t3));
        final long t2 = System.currentTimeMillis();
        LOGGER.debug("The Given Element is clicked");
        LOGGER.debug("Time taken in click method : " + (t2 - t1));
    }

    public static void click(final WebDriverWait webDriverWait, final WebDriver webDriver, final String objLocator,
                             final int index, final long timeout, final long pollingFrequency) {
        final long t1 = System.currentTimeMillis();
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        long t3 = System.currentTimeMillis();
        List<WebElement> multipleElementsAfterLoaded = getMultipleElementsAfterLoaded(webDriver, objLocator, timeout,
                pollingFrequency);

        if (webDriverWait.ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(multipleElementsAfterLoaded.get(index))).isEnabled()) {
            multipleElementsAfterLoaded.get(index).click();
        } else {
            LOGGER.error("Button with xpath [" + objLocator.split("===")[1] + "] is not clickable");
            CustomReport.reportError("Button with xpath [" + objLocator.split("===")[1] + "] is not clickable");
            assert false;
        }
        long t4 = System.currentTimeMillis();
        LOGGER.debug("Time taken in click on the element only : " + (t4 - t3));
        final long t2 = System.currentTimeMillis();
        LOGGER.debug("The Given Element is clicked");
        LOGGER.debug("Time taken in click method : " + (t2 - t1));
    }

    public static void clickAndWait(final WebDriver webDriver,
                                    final String objLocator, final long timeout) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        WebElement element = waitAndgetElement(webDriver, objLocator, timeout);
        element.click();
        LOGGER.debug("After waiting, the Given Element is clicked");
    }

    public static void clickElement(final WebDriver webDriver, String objLocator, final long timeout,
                                    final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        WebElement element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
        element.sendKeys(Keys.ENTER);
        LOGGER.debug("The Given Element is clicked");
    }

    public static void enterText(final WebDriver webDriver, final String objLocator, final String text) {
        enterText(webDriver, objLocator, text, ApplicationConstants.defaultTimeout,
                ApplicationConstants.defaultPollingFrequency);
    }

    public static void enterText(final WebDriver webDriver, final String objLocator, final String text,
                                 final long timeout, final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        if (ValueValidations.isValueNull(text)) {
            LOGGER.error("The given text is null. Test cannot proceed.");
            CustomReport.reportError("The given text is null. Test cannot proceed.");
            assert false;
        }

        WebElement element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
        if (!element.isEnabled()) {
            LOGGER.error("The text box with location [" + objLocator
                    + "] is not enable and text cannot be added. Please verify the screenshot. Test cannot proceed.");
            CustomReport.reportError("The text box with location [" + objLocator
                    + "] is not enable and text cannot be added. Please verify the screenshot. Test cannot proceed.");
            assert false;
        }
        LOGGER.debug("The data to be filled in the Textbox is " + text);
        element.clear();
        element.sendKeys(text);
        LOGGER.debug("The data is entered to the Text Field");
    }

    public static void enterText(final WebDriver webDriver, final String objLocator, final int index, final String text,
                                 final long timeout, final long pollingFrequency) {
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.debug("Executing Test Step::" + methodName);

        if (ValueValidations.isValueNull(text)) {
            LOGGER.error("The given text is null. Test cannot proceed.");
            CustomReport.reportError("The given text is null. Test cannot proceed.");
            assert false;
        }

        List<WebElement> elements = getMultipleElementsAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
        if (null == elements || elements.isEmpty()) {
            LogAndScreenShotUtil.logError(webDriver,
                    "Multiple elements with given locator [" + objLocator + "] is not available", null,
                    SeleniumUtil.class, methodName);
        } else if ((elements.size() - 1) < index) {
            LogAndScreenShotUtil
                    .logError(
                            webDriver, "Multiple elements with given locator [" + objLocator
                                    + "] is loaded but given index[" + index + "] element is not available.",
                            null, SeleniumUtil.class, methodName);
        } else {
            WebElement element = elements.get(index);
            if (!element.isEnabled()) {
                LogAndScreenShotUtil.logError(webDriver, "The text box with location [" + objLocator
                                + "] is not enable and text cannot be added. Please verify the screenshot. Test " +
                                "cannot " +
                                "proceed.",
                        null, SeleniumUtil.class, methodName);
            }
            LOGGER.debug("The data to be filled in the Textbox is " + text);
            element.clear();
            element.sendKeys(text);
            LOGGER.debug("The data is entered to the Text Field");
        }
    }

    public static void enterText(final WebElement element, final String text) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        if (ValueValidations.isValueNull(text)) {
            LOGGER.error("The given text is null. Test cannot proceed.");
            CustomReport.reportError("The given text is null. Test cannot proceed.");
            assert false;
        }

        if (!element.isEnabled()) {
            LOGGER.error(
                    "The text box is not enable and text cannot be added. Please verify the screenshot. Test cannot " +
                            "proceed.");
            CustomReport.reportError(
                    "The text box is not enable and text cannot be added. Please verify the screenshot. Test cannot " +
                            "proceed.");
            assert false;
        }
        LOGGER.debug("The data to be filled in the Textbox is " + text);
        //element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
        element.clear();
        element.sendKeys(text);
        LOGGER.debug("The data is entered to the Text Field");
    }

    public static String getElementText(final WebDriver webDriver, final String objLocator) {
        return getElementText(webDriver, objLocator, ApplicationConstants.defaultTimeout,
                ApplicationConstants.defaultPollingFrequency);
    }

    public static String getElementText(final WebDriver webDriver, final String objLocator, final long timeout,
                                        final long pollingFrequency) {
        final long t1 = System.currentTimeMillis();
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.debug("Executing Test Step::" + methodName);

        WebElement element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
        if (null == element) {
            LogAndScreenShotUtil.logErrorWithoutAssert(webDriver,
                    "The element with locator [" + objLocator + "] is not available on the page. Text cannot read.",
                    null, SeleniumUtil.class, methodName);
            return "";
        }
        LOGGER.debug("Element is having the text... " + element.getText());
        String text = element.getText();
        final long t2 = System.currentTimeMillis();
        LOGGER.debug("Time taken in getElementText method : " + (t2 - t1));
        return text;
    }

    public static String getElementText(final WebDriver webDriver, final String objLocator, final int index,
                                        final long timeout, final long pollingFrequency) {
        final long t1 = System.currentTimeMillis();
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        WebElement element = getMultipleElementsAfterLoaded(webDriver, objLocator, timeout, pollingFrequency)
                .get(index);
        LOGGER.debug("Element is having the text... " + element.getText());
        String text = element.getText();
        final long t2 = System.currentTimeMillis();
        LOGGER.debug("Time taken in getElementText method : " + (t2 - t1));
        return text;
    }

    public static void selectDropDown(final WebDriver webDriver, final String selectionType, final String objLocator,
                                      final int elementIndex, final String value, boolean trimFlg, final long timeout,
                                      final long pollingFrequency, boolean isMultiple) {
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.debug("Executing Test Step::" + methodName);

        Select select = null;
        if (isMultiple) {
            List<WebElement> elements = getMultipleElementsAfterLoaded(webDriver, objLocator, timeout,
                    pollingFrequency);
            if (null == elements || elements.size() <= 0) {
                LogAndScreenShotUtil.logError(webDriver,
                        "The dropdown with locator [" + objLocator
                                + "] is not visible. Please verify screenshot. Test cannot proceed",
                        null, SeleniumUtil.class, methodName);
            } else {
                select = new Select(elements.get(elementIndex));
            }
        } else {
            WebElement element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
            if (null == element) {
                LogAndScreenShotUtil.logError(webDriver,
                        "The dropdown with locator [" + objLocator
                                + "] is not visible. Please verify screenshot. Test cannot proceed",
                        null, SeleniumUtil.class, methodName);
            } else {
                select = new Select(element);
            }
        }
        if (null != select && selectionType.equals(ApplicationConstants.DROPDOWN_BYINDEX)) {
            select.selectByIndex(elementIndex);
            return;
        }
        if (null != select) {
            List<WebElement> list = select.getOptions();
            boolean notFoundFlg = true;
            int index = 0;
            boolean condition;
            for (int i = 0; i < list.size(); i++) {
                if (trimFlg) {
                    condition = list.get(i).getText().trim().equalsIgnoreCase(value.trim());
                } else {
                    condition = list.get(i).getText().equalsIgnoreCase(value);
                }
                if (condition) {
                    LOGGER.debug(list.get(i).getText() + "=" + value);
                    notFoundFlg = false;
                    index = i;
                    break;
                }
            }
            if (notFoundFlg) {
                LogAndScreenShotUtil.logError(webDriver, "Given value [" + value + "] is not available in the dropdown",
                        null, SeleniumUtil.class, methodName);
                SeleniumUtil.pressEscape(webDriver);
                SeleniumUtil.pressEscape(webDriver);
            }
            if (selectionType.equals(ApplicationConstants.DROPDOWN_BYVALUE)) {
                select.selectByValue(value);
            } else if (selectionType.equals(ApplicationConstants.DROPDOWN_BYVISIBLETEXT)) {
                select.selectByVisibleText(value);
            } else {
                list.get(index).click();
            }
        }
    }

    public static void selectMultipleSelectDropDownByValue(final WebDriver webDriver, String objLocator, String value,
                                                           final long timeout, final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        Select select = new Select(getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency));
        List<WebElement> list = select.getOptions();
        boolean notFoundFlg = true;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getText().equalsIgnoreCase(value)) {
                LOGGER.debug(list.get(i).getText() + "=" + value);
                select.selectByIndex(i);
                notFoundFlg = false;
                break;
            }
        }
        if (notFoundFlg) {
            AssertJUnit.fail("Given value [" + value + "] is not available in the dropdown");
        }
    }

    public static List<WebElement> getDropDownValues(final WebDriverWait webDriverWait, final WebDriver webDriver,
                                                     final String objLocator, final long timeout,
                                                     final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        click(webDriverWait, webDriver, objLocator, timeout, pollingFrequency);
        Select select = new Select(getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency));
        return select.getOptions();
    }

    public static String getSelectedDropDownValues(final WebDriver webDriver, final String objLocator) {
        return getSelectedDropDownValues(webDriver, objLocator, ApplicationConstants.defaultTimeout,
                ApplicationConstants.defaultPollingFrequency);
    }

    public static String getSelectedDropDownValues(final WebDriver webDriver, final String objLocator,
                                                   final long timeout, final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        Select select = new Select(getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency));
        LOGGER.debug("Selected option is the drop down is : " + select.getFirstSelectedOption().getText());
        return select.getFirstSelectedOption().getText();
    }

    public static String getSelectedDropDownValues(final WebElement element) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        Select select = new Select(element);
        LOGGER.debug("Selected option is the drop down is : " + select.getFirstSelectedOption().getText());
        return select.getFirstSelectedOption().getText();
    }

    public static String getSelectedDropDownValues(final WebDriver webDriver, final String objLocator, final int index,
                                                   final long timeout, final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        Select select = new Select(
                getMultipleElementsAfterLoaded(webDriver, objLocator, timeout, pollingFrequency).get(index));
        LOGGER.debug("Selected option is the drop down is : " + select.getFirstSelectedOption().getText());
        return select.getFirstSelectedOption().getText();
    }

    public static List<String> getDropDownText(final WebDriver webDriver, final String objLocator,
                                               final boolean trimValues) {
        return getDropDownText(webDriver, objLocator, trimValues, ApplicationConstants.defaultTimeout,
                ApplicationConstants.defaultPollingFrequency);
    }

    public static List<String> getDropDownText(final WebDriver webDriver, final String objLocator,
                                               final boolean trimValues, final long timeout,
                                               final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        Select select = new Select(getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency));
        List<String> dropDownText = new ArrayList<>();
        for (WebElement element : select.getOptions()) {
            dropDownText.add(trimValues ? element.getText().trim() : element.getText());
        }
        return dropDownText;
    }

    public static boolean verifyMultipleElementDisplayed(final WebDriverWait webDriverWait, final WebDriver webDriver,
                                                         final String objLocator, final int index) {
        return verifyMultipleElementDisplayed(webDriverWait, webDriver, objLocator, index,
                ApplicationConstants.defaultTimeout, ApplicationConstants.defaultPollingFrequency);
    }

    public static boolean verifyMultipleElementDisplayed(final WebDriverWait webDriverWait, final WebDriver webDriver,
                                                         final String objLocator, final int index, final long timeout
            , final long pollingFrequency) {
        List<WebElement> multipleElementsAfterLoaded = getMultipleElementsAfterLoaded(webDriver, objLocator, timeout,
                pollingFrequency);

        WebElement webElement;
        if (null != multipleElementsAfterLoaded && multipleElementsAfterLoaded.size() >= 1) {
            try {
                webElement = multipleElementsAfterLoaded.get(index);
                LOGGER.debug(webElement.getClass());
            } catch (IndexOutOfBoundsException e) {
                LOGGER.info("Element is loaded but index [" + index
                        + "] is not available. i.e. lesser elements are loaded.");
                CustomReport.reporter("Element is loaded but index [" + index
                        + "] is not available. i.e. lesser elements are loaded.");
                return false;
            }
        } else {
            return false;
        }

        return verifyElementDisplayed(webDriverWait, webElement, timeout,
                pollingFrequency);
    }

    public static boolean verifyElementDisplayed(final WebDriverWait webDriverWait, final WebDriver webDriver,
                                                 final String objLocator) {
        return verifyElementDisplayed(webDriverWait, webDriver, objLocator, ApplicationConstants.defaultTimeout,
                ApplicationConstants.defaultPollingFrequency);
    }

    public static boolean verifyElementDisplayed(final WebDriverWait webDriverWait, final WebDriver webDriver,
                                                 final String objLocator, final long timeout,
                                                 final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());
        WebElement element;
        try {
            element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);

            if (null != element) {
                webDriverWait.withTimeout(Duration.ofMillis(timeout)).ignoring(StaleElementReferenceException.class)
                        .pollingEvery(Duration.ofMillis(pollingFrequency)).until(ExpectedConditions.visibilityOf(element));
            } else {
                LOGGER.error("Given element is not available on the page.Visibility verification is failed.");
                return false;
            }
        } catch (ElementNotVisibleException e) {
            LOGGER.error("Element with locator [" + objLocator + "] is not visible on the page");
            return false;
        } catch (TimeoutException t) {
            LOGGER.error("Element with locator [" + objLocator + "] is not available on the page in the given timeout ["
                    + timeout + "]");
            return false;
        } catch (NoSuchElementException n) {
            LOGGER.error("No such element with locator [" + objLocator + "] is located on the page");
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.error("Error in locator : " + objLocator);
            return false;
        } catch (StaleElementReferenceException e) {
            LOGGER.error(
                    "Stale element exception accoured while accessing the element for first time. Retrying to access " +
                            "it for one more time...");
            explicitWait(2000);
            element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
            LOGGER.debug(
                    "Element object is reinitialized for locator : " + objLocator + ". Trying to verify its display.");
        } catch (Exception e) {
            LOGGER.error("Some error occurred while locating element with locator [" + objLocator
                    + "] on the page with error : " + e.getMessage());
            return false;
        }

        return element.isDisplayed();
    }

    public static boolean verifyElementDisplayed(final WebDriverWait webDriverWait,
                                                 final WebElement element, final long timeout,
                                                 final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());
        try {
            webDriverWait.withTimeout(Duration.ofMillis(timeout)).ignoring(StaleElementReferenceException.class)
                    .pollingEvery(Duration.ofMillis(pollingFrequency)).until(ExpectedConditions.visibilityOf(element));
        } catch (ElementNotVisibleException e) {
            LOGGER.error("Element is not visible on the page");
            return false;
        } catch (TimeoutException t) {
            LOGGER.error("Element is not available on the page in the given timeout [" + timeout + "]");
            return false;
        } catch (NoSuchElementException n) {
            LOGGER.error("No such element is located on the page");
            return false;
        } catch (Exception e) {
            LOGGER.error("Some error occured while locating element on the page with error : " + e.getMessage());
            return false;
        }

        return element.isDisplayed();
    }

    public static boolean verifyElementEnabled(final WebDriverWait webDriverWait, final WebDriver webDriver,
                                               String objLocator) {
        return verifyElementEnabled(webDriverWait, webDriver, objLocator, ApplicationConstants.defaultTimeout,
                ApplicationConstants.defaultPollingFrequency);
    }

    public static boolean verifyElementEnabled(final WebDriverWait webDriverWait, final WebDriver webDriver,
                                               String objLocator, final long timeout, final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());
        WebElement element;
        try {
            element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);

            if (null != element) {
                webDriverWait.withTimeout(Duration.ofMillis(timeout)).ignoring(StaleElementReferenceException.class)
                        .pollingEvery(Duration.ofMillis(pollingFrequency)).until(ExpectedConditions.elementToBeClickable(element));
            } else {
                LOGGER.error("Given element is not available on the page.Enable verification is failed.");
                return false;
            }
        } catch (ElementNotVisibleException e) {
            LOGGER.error("Element with locator [" + objLocator + "] is not visible on the page");
            return false;
        } catch (TimeoutException t) {
            LOGGER.error("Element with locator [" + objLocator + "] is not available on the page in the given timeout ["
                    + timeout + "]");
            return false;
        } catch (NoSuchElementException n) {
            LOGGER.error("No such element with locator [" + objLocator + "] is located on the page");
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.error("Error in locator : " + objLocator);
            return false;
        } catch (StaleElementReferenceException e) {
            LOGGER.error(
                    "Stale element exception accoured while accessing the element for first time. Retrying to access " +
                            "it for one more time...");
            explicitWait(2000);
            element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
            LOGGER.debug(
                    "Element object is reinitialized for locator : " + objLocator + ". Trying to verify its display.");
        } catch (Exception e) {
            LOGGER.error("Some error occurred while locating element with locator [" + objLocator + "] on the page : "
                    + e.getMessage());
            return false;
        }
        return element.isEnabled();
    }

    public static boolean verifyMultipleElementEnabled(final WebDriverWait webDriverWait, final WebDriver webDriver,
                                                       final String objLocator, final int index, final long timeout,
                                                       final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());
        List<WebElement> elements;
        WebElement element;
        try {
            elements = getMultipleElementsAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
            if (null == elements || elements.isEmpty()) {
                throw new NoSuchElementException("Mutiple elements are not available.");
            } else if ((elements.size() - 1) < index) {
                throw new IndexOutOfBoundsException();
            }
            element = elements.get(index);
            webDriverWait.ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(elements.get(0)));
        } catch (ElementNotVisibleException e) {
            LOGGER.error("Element with locator [" + objLocator + "] is not visible on the page");
            return false;
        } catch (TimeoutException t) {
            LOGGER.error("Element with locator [" + objLocator + "] is not available on the page in the given timeout ["
                    + timeout + "]");
            return false;
        } catch (NoSuchElementException n) {
            LOGGER.error("No such elements with locator [" + objLocator + "] is located on the page");
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.error("Error in locator : " + objLocator);
            return false;
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("Locator [" + objLocator + "] with given index [" + index + "] is not available");
            return false;
        } catch (StaleElementReferenceException e) {
            LOGGER.error(
                    "Stale element exception accoured while accessing the element for first time. Retrying to access " +
                            "it for one more time...");
            explicitWait(2000);
            element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
            LOGGER.debug(
                    "Element object is reinitialized for locator : " + objLocator + ". Trying to verify its display.");
        } catch (Exception e) {
            LOGGER.error("Some error occurred while locating element with locator [" + objLocator + "] on the page : "
                    + e.getMessage());
            return false;
        }
        return element.isEnabled();
    }

    public static String getElementAttribute(final WebDriver webDriver, String objLocator, String attribute) {
        return getElementAttribute(webDriver, objLocator, attribute, ApplicationConstants.defaultTimeout,
                ApplicationConstants.defaultPollingFrequency);
    }

    public static String getElementAttribute(final WebDriver webDriver, String objLocator, String attribute,
                                             final long timeout, final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        WebElement element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
        LOGGER.debug("The value of attribute [" + attribute + "] is : " + element.getAttribute(attribute));
        return element.getAttribute(attribute);
    }

    public static void explicitWait(long milliSec) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        try {
            LOGGER.debug("Waiting for [" + milliSec + "] millisec before the next step.");
            Thread.sleep(milliSec);
        } catch (Exception e) {
            LOGGER.error("Error while doing explicit wait : " + e.getMessage());
        }
    }

    public static boolean verifyPresenceOfText(final WebDriver webDriver, String objLocator, String text,
                                               final long timeout, final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        WebElement elementLocator = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
        LOGGER.debug("The text present in the element is : " + elementLocator.getText());
        return elementLocator.getText().equals(text);
    }

    public static boolean verifyCheckBoxIsSelected(final WebDriver webDriver, String objLocator) {
        return verifyCheckBoxIsSelected(webDriver, objLocator, ApplicationConstants.defaultTimeout,
                ApplicationConstants.defaultPollingFrequency);
    }

    public static boolean verifyCheckBoxIsSelected(final WebDriver webDriver, String objLocator, final long timeout,
                                                   final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        WebElement element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
        if (element.isSelected()) {
            LOGGER.debug("Given checkbox is selected");
            return true;
        }
        return false;
    }

    public static boolean verifyMultipleCheckBoxIsSelected(final WebDriver webDriver, final String objLocator,
                                                           int index, final long timeout,
                                                           final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        List<WebElement> elements = getMultipleElementsAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
        if (elements.size() > 0 && elements.get(index).isSelected()) {
            LOGGER.debug("Given checkbox is selected");
            return true;
        }
        return false;
    }

    public static WebElement getElementAfterLoaded(final WebDriver webDriver, final String objLocator) {
        return getElementAfterLoaded(webDriver, objLocator, ApplicationConstants.defaultTimeout,
                ApplicationConstants.defaultPollingFrequency);
    }

    public static WebElement getElementAfterLoaded(final WebDriver webDriver, final String objLocator,
                                                   final long timeout, final long pollingFrequency) {
        final long t1 = System.currentTimeMillis();
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        By byValue = getLocator(objLocator);

        WebElement element = getLoadedElementAfterFluentWait(webDriver, byValue, timeout, pollingFrequency);
        if (null != element) {
            if (!isElementDisplayed(webDriver, byValue, timeout, pollingFrequency)) {
                LOGGER.debug("Element [" + objLocator + "] is loaded but not displayed yet.");
            } else {
                LOGGER.debug("Element [" + objLocator + "] is loaded and also displayed on the page.");
            }
        }
        final long t2 = System.currentTimeMillis();
        LOGGER.debug("Time taken in getElementAfterLoaded method : " + (t2 - t1));
        return element;
    }

    public static List<WebElement> getElementsAfterLoaded(final WebDriver webDriver, final String objLocator,
                                                          final long timeout, final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        By byValue = getLocator(objLocator);
        isElementDisplayed(webDriver, byValue, timeout, pollingFrequency);
        return webDriver.findElements(byValue);
    }

    public static WebElement getElementAfterLoadedImplicitly(final WebDriver webDriver, final String objLocator) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        By byValue = getLocator(objLocator);
        return webDriver.findElement(byValue);
    }

    public static WebElement waitAndgetElement(final WebDriver webDriver,
                                               final String objLocator, final long timeout) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());
        By byValue = getLocator(objLocator);

        explicitWait(timeout);

        return webDriver.findElement(byValue);
    }

    public static WebElement scrollBeforeWaitAndgetElement(final WebDriver webDriver, final String objLocator,
                                                           final long timeout, final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        WebElement element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
        scrollTab(webDriver, objLocator, 300, timeout);

        return element.findElement(getLocator(objLocator));
    }

    public static List<WebElement> getMultipleElementsAfterLoaded(final WebDriver webDriver, final String objLocator) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        By byValue = getLocator(objLocator);
        if (isElementDisplayed(webDriver, byValue, ApplicationConstants.defaultTimeout,
                ApplicationConstants.defaultPollingFrequency)) {
            return webDriver.findElements(byValue);
        } else {
            return new ArrayList<>();
        }
    }

    public static List<WebElement> getMultipleElementsAfterLoaded(final WebDriver webDriver, final String objLocator,
                                                                  final long timeout, final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        By byValue = getLocator(objLocator);
        List<WebElement> elements = getMultipleLoadedElementAfterFluentWait(webDriver, byValue, timeout,
                pollingFrequency);
        if (!(null == elements || elements.isEmpty())) {
            if (elements.get(0).isDisplayed()) {
                LOGGER.debug("Element [" + objLocator + "] is loaded but not displayed yet.");
            } else {
                LOGGER.debug("Element [" + objLocator + "] is loaded and also displayed on the page.");
            }
        } else {
            LOGGER.info("No elements loaded in multi elements check.");
        }
        return elements;
    }

    public static List<String> getMultipleElementTextAfterLoaded(final WebDriver webDriver, final String objLocator) {
        return getMultipleElementTextAfterLoaded(webDriver, objLocator, ApplicationConstants.defaultTimeout,
                ApplicationConstants.defaultPollingFrequency);
    }

    public static List<String> getMultipleElementTextAfterLoaded(final WebDriver webDriver, final String objLocator,
                                                                 final long timeout, final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        List<WebElement> elements = getMultipleElementsAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
        if (!elements.isEmpty()) {
            List<String> result = new ArrayList<>();
            for (WebElement element : elements) {
                result.add(element.getText());
            }
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    public static boolean isElementDisplayed(final WebDriver webDriver, final String objLocator) {

        return isElementDisplayed(webDriver, objLocator, ApplicationConstants.defaultTimeout,
                ApplicationConstants.defaultPollingFrequency);
    }

    public static boolean isElementDisplayed(final WebDriver webDriver, final String objLocator, final long timeout,
                                             final long pollingFrequency) {
        By byValue = getLocator(objLocator);
        return isElementDisplayed(webDriver, byValue, timeout, pollingFrequency);
    }

    private static boolean isElementDisplayed(final WebDriver webDriver, final By byValue, final long timeout,
                                              final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());
        WebElement element = isElementAvailable(webDriver, byValue, timeout, pollingFrequency);
        return element != null && element.isDisplayed();
    }

    public static boolean isElementClickable(final WebDriverWait webDriverWait,
                                             final String locator, final long timeout, long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        By byValue = getLocator(locator);
        try {
            explicitWait(1000);
            LOGGER.debug("Waiting for element [" + byValue.toString() + "] to be clickable. Timeout:" + timeout);
            return webDriverWait.withTimeout(Duration.ofMillis(timeout)).pollingEvery(Duration.ofMillis(pollingFrequency)).ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(byValue)).isEnabled();
        } catch (StaleElementReferenceException e) {
            LOGGER.error(
                    "Stale element exception occurred while accessing the element for first time. Retrying to access " +
                            "it for one more time...");
            explicitWait(2000);
            LOGGER.debug(
                    "Element object is reinitialized for By : " + byValue + ". Trying to verify if it is clickable.");
            return webDriverWait.withTimeout(Duration.ofMillis(timeout)).pollingEvery(Duration.ofMillis(pollingFrequency)).ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(byValue)).isEnabled();
        } catch (WebDriverException wx) {
            if (wx.getMessage().contains("not clickable at point")) {
                LOGGER.error("Element with By value as [" + byValue + "] is not clickable before the timeout ["
                        + (timeout / 1000) + "] Secs.");
            } else {
                LOGGER.error("Some error occurred while trying to click element with By value : [" + byValue
                        + "] before the timeout : [" + (timeout / 1000) + "] Secs.");
            }
            return false;
        } catch (Exception e) {
            LOGGER.error("Element with By value as [" + byValue + "] is not clickable before the timeout ["
                    + (timeout / 1000) + "] Secs");
            return false;
        }
    }

    public static boolean isElementClickable(final WebDriverWait webDriverWait,
                                             final String locator) {
        return isElementClickable(webDriverWait, locator, ApplicationConstants.defaultTimeout,
                ApplicationConstants.defaultPollingFrequency);
    }

    private static WebElement isElementAvailable(final WebDriver webDriver, final By byValue, final long timeout,
                                                 final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());
        WebElement element = null;
        try {
            element = getLoadedElementAfterFluentWait(webDriver, byValue, timeout, pollingFrequency);
        } catch (ElementNotVisibleException e) {
            LOGGER.debug("Element with locator [" + byValue + "] is not visible on the page");
        } catch (TimeoutException t) {
            LOGGER.debug("Element with locator [" + byValue + "] is not available on the page in the given timeout ["
                    + timeout + "]");
        } catch (NoSuchElementException n) {
            LOGGER.debug("No such element with locator [" + byValue + "] is located on the page");
        } catch (StaleElementReferenceException e) {
            LOGGER.error(
                    "Stale element exception accoured while accessing the element for first time. Retrying to access " +
                            "it for one more time...");
            explicitWait(2000);
            element = getLoadedElementAfterFluentWait(webDriver, byValue, timeout, pollingFrequency);
            LOGGER.debug("Element object is reinitialized for By : " + byValue + ". Trying to verify its display.");
        } catch (Exception e) {
            LOGGER.debug("Some error occurred while locating element with locator [" + byValue + "] on the page : "
                    + e.getMessage());
        }
        return element;
    }

    private static WebElement getLoadedElementAfterFluentWait(final WebDriver webDriver, final By byValue,
                                                              final long timeout, final long pollingFrequency) {
        final long t1 = System.currentTimeMillis();
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        LOGGER.debug("Waiting for element [" + byValue
                + "] in getLoadedElementAfterFluentWait method for the max timeout [" + timeout + "]");

        Wait<WebDriver> wait = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofMillis(timeout))
                .pollingEvery(Duration.ofMillis(pollingFrequency)).ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class).ignoring(StaleElementReferenceException.class)
                .ignoring(WebDriverException.class);
        ExpectedCondition<WebElement> condition = d -> {
            assert d != null;
            return d.findElement(byValue);
        };

        WebElement element = null;
        try {
            element = wait.until(condition);
        } catch (TimeoutException ex) {
            LOGGER.error("Element [" + byValue + "] is not available on the page");
        } finally {
            final long t2 = System.currentTimeMillis();
            LOGGER.debug("Time taken for element [" + byValue
                    + "] in getLoadedElementAfterFluentWait method for the max timeout [" + timeout + "] is : "
                    + (t2 - t1));
        }
        return element;
    }

    private static List<WebElement> getMultipleLoadedElementAfterFluentWait(final WebDriver webDriver, final By byValue,
                                                                            final long timeout,
                                                                            final long pollingFrequency) {
        final long t1 = System.currentTimeMillis();
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        LOGGER.debug("Waiting for element [" + byValue
                + "] in getLoadedElementAfterFluentWait method for the max timeout [" + timeout + "]");

        Wait<WebDriver> wait = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofMillis(timeout))
                .pollingEvery(Duration.ofMillis(pollingFrequency)).ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class).ignoring(StaleElementReferenceException.class)
                .ignoring(WebDriverException.class);
        ExpectedCondition<List<WebElement>> condition = d -> {
            assert d != null;
            return d.findElements(byValue);
        };

        List<WebElement> elements = null;
        try {
            elements = wait.until(condition);
            if (elements.size() > 1) {
                LOGGER.info("More than one elements loaded.");
            } else if (elements.size() == 1) {
                LOGGER.info("Single element is loaded.");
            } else {
                LOGGER.info("No element is loaded.");
            }
        } catch (TimeoutException ex) {
            LOGGER.error("Element [" + byValue + "] is not available on the page");
        } finally {
            final long t2 = System.currentTimeMillis();
            LOGGER.debug("Time taken for element [" + byValue
                    + "] in getLoadedElementAfterFluentWait method for the max timeout [" + timeout + "] is : "
                    + (t2 - t1));
        }
        return elements;
    }

    public static By getLocator(String objLocator) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        String[] str = objLocator.split("===");
        LOGGER.debug("Object Identifier " + str[0] + "\t Object Identifier Value " + str[1]);
        By byValue = null;
        //LOGGER.info(str[0].toUpperCase());
        switch (str[0].toUpperCase()) {
            case "ID":
                byValue = By.id(str[1]);
                break;
            case "XPATH":
                byValue = By.xpath(str[1]);
                break;
            case "NAME":
                byValue = By.name(str[1]);
                break;
            case "LINKTEXT":
                byValue = By.linkText(str[1]);
                break;
            case "CSS":
                byValue = By.cssSelector(str[1]);
                break;
            case "CLASSNAME":
                byValue = By.className(str[1]);
                break;
            case "TAGNAME":
                byValue = By.tagName(str[1]);
                break;
            default:
                AssertJUnit.fail("Invalid locator type [" + str[0] + "] is given.");
        }
        return byValue;
    }

    public static boolean explicitWaitForElement(final WebDriver webDriver, int milliSecond, String elementStatus,
                                                 String elementXpath, final long timeout, final long pollingFrequency) {
        long t1 = System.currentTimeMillis();
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());
        explicitWait(milliSecond);
        long iCount = 0;
        while (iCount < timeout) {
            if (instantElementCheck(webDriver, elementStatus, elementXpath)) {
                return true;
            } else {
                explicitWait(pollingFrequency);
            }
            iCount += pollingFrequency;
        }
        long t2 = System.currentTimeMillis();
        LOGGER.debug(
                "Time taken in explicit wait for checking the status [" + elementStatus + "] for element with timeout["
                        + timeout + "] and polling frequency[" + pollingFrequency + "] is: " + (t2 - t1));
        return false;
    }

    public static boolean instantElementCheck(final WebDriver webDriver, final String type, final By byValue) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        LOGGER.debug("Instant checking for [" + type + "] for element [" + byValue.toString() + "] on the page");
        WebElement element;
        try {
            element = webDriver.findElement(byValue);
            if (type.equalsIgnoreCase("Display")) {
                return element.isDisplayed();
            } else if (type.equalsIgnoreCase("Enable")) {
                return element.isEnabled();
            } else if (type.equalsIgnoreCase("Select")) {
                return element.isSelected();
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            LOGGER.error("Element [" + byValue.toString() + "] is not available on the page");
            return false;
        } catch (Exception e) {
            LOGGER.error("Some error occurred while instant checking for the element [" + byValue.toString() + "] : "
                    + e.getMessage());
            return false;
        }
    }

    public static boolean instantMultipleElementCheck(final WebDriver webDriver, final String type,
                                                      final String locator, int index) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());
        By byValue = getLocator(locator);

        LOGGER.debug("Instant checking for [" + type + "] for element [" + byValue.toString() + "] on the page");
        List<WebElement> elements;
        try {
            elements = webDriver.findElements(byValue);
            if (type.equalsIgnoreCase("Display")) {
                return elements.get(index).isDisplayed();
            } else if (type.equalsIgnoreCase("Enable")) {
                return elements.get(index).isEnabled();
            } else if (type.equalsIgnoreCase("Select")) {
                return elements.get(index).isSelected();
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            LOGGER.error("Element [" + byValue.toString() + "] is not available on the page");
            return false;
        } catch (Exception e) {
            LOGGER.error("Some error occurred while instant checking for the element [" + byValue.toString() + "] : "
                    + e.getMessage());
            return false;
        }
    }

    public static boolean instantElementCheckWithoutScroll(WebDriver webDriver, String type, By byValue) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        try {
            WebElement element = webDriver.findElement(byValue);
            if (type.equalsIgnoreCase("Display")) {
                return element.isDisplayed();
            } else if (type.equalsIgnoreCase("Enable")) {
                return element.isEnabled();
            } else if (type.equalsIgnoreCase("Select")) {
                return element.isSelected();
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static WebDriver setImplicitTime(final WebDriver webDriver, long timeInMillisec) {
        webDriver.manage().timeouts().implicitlyWait(timeInMillisec, TimeUnit.MILLISECONDS);
        return webDriver;
    }

    public static boolean instantElementCheck(final WebDriver webDriver, String type, String objLocator) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());
        By byValue = getLocator(objLocator);
        return instantElementCheck(webDriver, type, byValue);
    }

    public static boolean instantElementCheckAfterGivenWait(final WebDriver webDriver, final String type,
                                                            final String objLocator, final long waitTime) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());
        explicitWait(waitTime);
        By byValue = getLocator(objLocator);
        return instantElementCheck(webDriver, type, byValue);
    }

    public static boolean instantElementCheckWithoutScroll(final WebDriver webDriver, String type, String objLocator) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());
        By byValue = getLocator(objLocator);
        return instantElementCheckWithoutScroll(webDriver, type, byValue);
    }

    public static void saveFailedScreenshot(final WebDriver webDriver, String imageFileName, final Class<?> clazz) {
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.debug("Executing Test Step::" + methodName);

        imageFileName = clazz.getSimpleName() + "-" + imageFileName;
        // Taking and storing screenshot
        File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        File imageDir = new File(new File("").getAbsoluteFile() + "/target/surefire-reports/html/images");
        if (!imageDir.exists()) {
            if (imageDir.mkdirs()) {
                LogAndScreenShotUtil.log(webDriver, "Image folder is added successfully", clazz, methodName);
            }
        }
        File dest = new File(imageDir.getAbsolutePath() + "/" + imageFileName + ".png");
        try {
            FileUtils.copyFile(scrFile, dest);
            String path = "<img src=\"images/" + dest.getName() + "\" alt=\"Failed screenshot for " + imageFileName
                    + "\" />";
            Reporter.log(path);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while saving failed screenshot for " + imageFileName, e);
            CustomReport.reportError("Exception occurred while saving failed screenshot for " + imageFileName
                    + ". Exception is=" + e.getMessage());
        }
    }

    public static void saveFailedScreenshot(final WebDriver webDriver, String methodName) {
        // Taking and storing screenshot
        File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        File imageDir = new File(new File("").getAbsoluteFile() + "/target/surefire-reports/html/images");
        if (!imageDir.exists()) {
            if (imageDir.mkdirs()) {
                LogAndScreenShotUtil.log(webDriver, "Image folder is added successfully", SeleniumUtil.class,
                        methodName);
            }
        }
        File dest = new File(imageDir.getAbsolutePath() + "/" + methodName + ".png");
        try {
            FileUtils.copyFile(scrFile, dest);
            String path = "<img src=\"" + dest.getAbsolutePath() + "\" alt=\"Failed screenshot for " + methodName
                    + "\" />";
            Reporter.log(path);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while saving failed screenshot for " + methodName, e);
            CustomReport.reportError("Exception occurred while saving failed screenshot for " + methodName
                    + ". Exception is=" + e.getMessage());
        }
    }

    public static void setElementAttribute(final WebDriver webDriver, final String objLocator, final String value,
                                           final long timeout, final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        WebElement element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), value);
    }

    public static void setAttributeValue(final WebDriver webDriver, final String objLocator, final String value,
                                         final long timeout, final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        WebElement element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
        element.sendKeys(Keys.chord(Keys.CONTROL), value);
    }

    public static boolean verifyElementIsReadOnly(final WebDriver webDriver, final String objLocator,
                                                  final long timeout, final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        WebElement element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
        return element.isEnabled();
    }

    public static void instantClick(final WebDriver webDriver, String objLocator, final long timeout)
            throws ElementNotVisibleException {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        scrollTab(webDriver, objLocator, 300, timeout);

        WebElement element = webDriver.findElement(getLocator(objLocator));
        element.click();
        LOGGER.debug("The given element is clicked instantly without wait");
    }

    public static boolean isSorted(String sortingType, List<String> list) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        boolean sorted = true;
        if (sortingType.equalsIgnoreCase("Ascending")) {
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i - 1).compareTo(list.get(i)) != 0) {
                    if (list.get(i - 1).compareTo(list.get(i)) > 0) {
                        sorted = false;
                    }
                }
            }
        } else if (sortingType.equalsIgnoreCase("Descending")) {
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i - 1).compareTo(list.get(i)) != 0) {
                    if (list.get(i - 1).compareTo(list.get(i)) < 0) {
                        sorted = false;
                    }
                }
            }
        }
        return sorted;
    }

    public static boolean checkIfClickable(final WebDriverWait webDriverWait, final WebDriver webDriver,
                                           final String objLocator, final long timeout, final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        int i = 0;
        boolean flg = false;
        while (i < 2) {
            try {
                click(webDriverWait, webDriver, objLocator, timeout, pollingFrequency);
                flg = true;
                break;
            } catch (Exception e) {
                LOGGER.error("element not clickable");
                explicitWait(2000);
            }
            i++;
        }
        return flg;
    }

    public static boolean isElementSelected(final WebDriver webDriver, String objLocator) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        WebElement element = getElementAfterLoaded(webDriver, objLocator, ApplicationConstants.defaultTimeout,
                ApplicationConstants.defaultPollingFrequency);
        return element.isSelected();
    }

    public static boolean isElementSelected(final WebDriver webDriver, String objLocator, final long timeout,
                                            final long pollingFrequency) {
        LOGGER.debug("Executing Test Step::" + new Object() {
        }.getClass().getEnclosingMethod().getName());

        WebElement element = getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency);
        return element.isSelected();
    }

    public static void scrollTab(final WebDriver webDriver, String objLocator) {
        explicitWait(2000);
        JavascriptExecutor je = (JavascriptExecutor) webDriver;
        WebElement element = webDriver.findElement(getLocator(objLocator));
        je.executeScript("arguments[0].scrollIntoView(true);", element);
        explicitWait(2000);
    }

    public static void scrollTab(final WebDriver webDriver, String objLocator, int milliSec, final long timeout) {
        final long t1 = System.currentTimeMillis();
        explicitWait(milliSec);
        JavascriptExecutor je = (JavascriptExecutor) webDriver;
        WebElement element = webDriver.findElement(getLocator(objLocator));
        long i = 0;
        while (i < timeout) {
            try {
                je.executeScript("arguments[0].scrollIntoView(true);", element);
                break;
            } catch (StaleElementReferenceException ex) {
                explicitWait(50);
                i = i + 50;
            }
        }
        explicitWait(milliSec);
        final long t2 = System.currentTimeMillis();
        LOGGER.debug("Time taken in scrollTab method : " + (t2 - t1));
    }

    public static void scrollTab(final WebDriver webDriver, String objLocator, int index, int milliSec,
                                 final long timeout) {
        final long t1 = System.currentTimeMillis();
        explicitWait(milliSec);
        JavascriptExecutor je = (JavascriptExecutor) webDriver;
        List<WebElement> findElements = webDriver.findElements(getLocator(objLocator));
        WebElement element = null;
        try {
            if (!(null == findElements || findElements.isEmpty())) {
                element = findElements.get(index);
            } else {
                LOGGER.info("Element not found for scroll");
            }
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("Elements loaded but given index [" + index
                    + "] element is not available i.e. lesser elements are available.");
        }
        long i = 0;
        while (i < timeout) {
            try {
                je.executeScript("arguments[0].scrollIntoView(true);", element);
                break;
            } catch (StaleElementReferenceException ex) {
                explicitWait(50);
                i = i + 50;
            }
        }
        explicitWait(milliSec);
        final long t2 = System.currentTimeMillis();
        LOGGER.debug("Time taken in scrollTab method : " + (t2 - t1));
    }

    public static void scrollTab(final WebDriver webDriver, By byObjLocator, int milliSec) {
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.debug("Executing Test Step::" + methodName);

        explicitWait(milliSec);
        JavascriptExecutor je = (JavascriptExecutor) webDriver;
        WebElement element = null;
        try {
            element = webDriver.findElement(byObjLocator);
        } catch (TimeoutException e) {
            LogAndScreenShotUtil.logErrorWithoutAssert(webDriver,
                    "Error occurred while scrolling : " + e.getMessage(), e, SeleniumUtil.class, methodName);
        }
        je.executeScript("arguments[0].scrollIntoView(true);", element);
        explicitWait(milliSec);
    }

    public static void scrollTab(final WebDriver webDriver, WebElement element, int milliSec) {
        explicitWait(milliSec);
        JavascriptExecutor je = (JavascriptExecutor) webDriver;
        je.executeScript("arguments[0].scrollIntoView(true);", element);
        explicitWait(milliSec);
    }

    public static void scrollTabAction(WebDriver webDriver, String locator, int waitTimeInMilliSec) throws InterruptedException {
        Thread.sleep(waitTimeInMilliSec);
        Actions actions = new Actions(webDriver);
        actions.moveToElement(getElementAfterLoaded(webDriver, locator)).build().perform();
        Thread.sleep(waitTimeInMilliSec);
    }

    public static void scrollTabActionLowerBoundary(WebDriver webDriver, String locator, int waitTimeInMilliSec) throws InterruptedException {
        Thread.sleep(waitTimeInMilliSec);
        Actions actions = new Actions(webDriver);
        actions.moveToElement(getElementAfterLoaded(webDriver, locator)).build().perform();
        actions.moveByOffset(0, 15).build().perform();
        Thread.sleep(waitTimeInMilliSec);
    }

    public static void rightClick(final WebDriver webDriver, String objLocator, final long timeout,
                                  final long pollingFrequency) {
        final long t1 = System.currentTimeMillis();
        fluentWait(getElementAfterLoaded(webDriver, objLocator, timeout, pollingFrequency));
        WebElement element = webDriver.findElement(getLocator(objLocator));
        try {
            final Actions action = new Actions(webDriver);
            action.contextClick(element).perform();
            LOGGER.debug("Sucessfully Right clicked on the element");
        } catch (StaleElementReferenceException e) {
            LOGGER.debug("Element is not attached to the page document " + e.getMessage());
        } catch (NoSuchElementException e) {
            LOGGER.debug("Element " + element + " was not found in DOM " + e.getMessage());
        } catch (Exception e) {
            LOGGER.debug("Element " + element + " was not clickable " + e.getMessage());
        } finally {
            final long t2 = System.currentTimeMillis();
            LOGGER.debug("Time taken in rightClick method : " + (t2 - t1));
        }
    }

    public static boolean waitForElementDisappear(final WebDriver webDriver, long pollingFrequency, long timeout,
                                                  String locator, String checkType) {
        for (long i = 0; i < timeout; i++) {
            try {
                if (!instantElementCheck(webDriver, checkType, locator)) {
                    LOGGER.debug("Element no longer " + checkType);
                    return true;
                }
            } catch (Exception e) {
                LOGGER.debug("Element no longer " + checkType);
                return true;
            }
            LOGGER.debug("Element with locator [" + locator + "] is still " + checkType);
            explicitWait(pollingFrequency);
            i = i + pollingFrequency;
        }
        return false;
    }

    public static boolean waitForElementDisappear(final WebDriver webDriver, String locator, String checkType,
                                                  final long timeout, final long pollingFrequency) {
        return waitForElementDisappear(webDriver, pollingFrequency, timeout, locator, checkType);
    }

    public static int randomCountGenerator(final int count) {
        Random random = new Random();
        int randomRow = random.nextInt(count);
        if (randomRow == 0) {
            randomRow = 1;
        }
        return randomRow;
    }

    public static String getRandomNumber(final int digCount) {
        StringBuilder sb = new StringBuilder(digCount);
        final Random rnd = new Random();
        for (int i = 0; i < digCount; i++)
            sb.append((char) ('0' + rnd.nextInt(10)));
        return sb.toString();
    }

    public static void clickF11(final long milliSec) {
        Robot robot;
        try {
            robot = new Robot();
            robot.setAutoDelay(50);
            robot.keyPress(KeyEvent.VK_F11);
            explicitWait(milliSec);
        } catch (AWTException e) {
            AssertJUnit.fail("Error occurred while clicking on the F11 key : " + e.getMessage());
        }
    }

    /**
     * Through this method we can wait until element is not clickable
     * <p>
     * Current driver instance
     *
     * @param xpath xpath of the element
     */
    public static WebElement waitUntilElementClickable(final WebDriverWait webDriverWait, final WebDriver webDriver,
                                                       final String xpath, final long timeout) {
        explicitWait(ApplicationConstants.WAIT_MINIMUM);
        scrollTab(webDriver, "xpath===" + xpath, 300, timeout);
        return webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    public static boolean deleteFile(String folderPath, String deleteCondition, String key) {
        File folder = new File(folderPath);
        File[] listFiles = folder.listFiles();
        if (null == listFiles) {
            AssertJUnit.fail("Did not found any list of files in the folder at location : " + folderPath);
        }
        boolean result = false;
        try {
            for (File file : listFiles) {

                switch (deleteCondition.toUpperCase()) {
                    case "EQUAL":
                        if (file.getName().equals(key)) {
                            result = file.delete();
                        }
                        break;
                    case "EQUAL_IGNORE_CASE":
                        if (file.getName().equalsIgnoreCase(key)) {
                            result = file.delete();
                        }
                        break;
                    case "STARTS_WITH":
                        if (file.getName().startsWith(key)) {
                            result = file.delete();
                        }
                        break;
                    case "ENDS_WITH":
                        if (file.getName().endsWith(key)) {
                            result = file.delete();
                        }
                        break;
                    case "CONTAINS":
                        if (file.getName().contains(key)) {
                            result = file.delete();
                        }
                        break;
                    default:
                        AssertJUnit.fail(
                                "Invalid delete Condition [" + deleteCondition + "] is given. File cannot be deleted.");
                }
            }
        } catch (Exception e) {
            result = false;
            AssertJUnit.fail(" Some error occurred while deleting file " + e.getMessage());
        }
        return result;
    }

    public static boolean isFileExist(String folderPath, String searchCondition, String key) {
        File folder = new File(folderPath);
        File[] listFiles = folder.listFiles();
        if (null == listFiles) {
            AssertJUnit.fail("Did not found any list of files in the folder at location : " + folderPath);
        }
        boolean result = false;

        for (File file : listFiles) {
            switch (searchCondition.toUpperCase()) {
                case "EQUAL":
                    if (file.getName().equals(key)) {
                        result = true;
                    }
                    break;
                case "EQUAL_IGNORE_CASE":
                    if (file.getName().equalsIgnoreCase(key)) {
                        result = true;
                    }
                    break;
                case "STARTS_WITH":
                    if (file.getName().startsWith(key)) {
                        result = true;
                    }
                    break;
                case "ENDS_WITH":
                    if (file.getName().endsWith(key)) {
                        result = true;
                    }
                    break;
                case "CONTAINS":
                    if (file.getName().contains(key)) {
                        result = true;
                    }
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    public static String getFileName(String folderPath, String searchCondition, String key) {
        File folder = new File(folderPath);
        File[] listFiles = folder.listFiles();
        if (null == listFiles) {
            AssertJUnit.fail("Did not found any list of files in the folder at location : " + folderPath);
        }
        String result = "";

        for (File file : listFiles) {
            switch (searchCondition.toUpperCase()) {
                case "EQUAL":
                    if (file.getName().equals(key)) {
                        result = file.getName();
                    }
                    break;
                case "EQUAL_IGNORE_CASE":
                    if (file.getName().equalsIgnoreCase(key)) {
                        result = file.getName();
                    }
                    break;
                case "STARTS_WITH":
                    if (file.getName().startsWith(key)) {
                        result = file.getName();
                    }
                    break;
                case "ENDS_WITH":
                    if (file.getName().endsWith(key)) {
                        result = file.getName();
                    }
                    break;
                case "CONTAINS":
                    if (file.getName().contains(key)) {
                        result = file.getName();
                    }
                    break;
                default:
                    AssertJUnit.fail("Invalid " + searchCondition);
                    break;
            }
        }
        return result;
    }

    /**
     * login in dorado application through this method
     *
     * @param webDriver Current web driver instance.
     * @param prop      Loaded property file instance.
     */
    public static void login(final WebDriver webDriver, final Properties prop, final String userName,
                             final String password) {
        explicitWait(ApplicationConstants.WAIT_NORMAL);
        checkInstantVisibilty(webDriver, prop.getProperty("user.name.xpath"));
        webDriver.findElement(By.xpath(prop.getProperty("user.name.xpath"))).sendKeys(userName);
        webDriver.findElement(By.xpath(prop.getProperty("password.xpath"))).sendKeys(password);
        webDriver.findElement(By.xpath(prop.getProperty("login.button.xpath"))).sendKeys(Keys.RETURN);
    }

    /**
     * Through this method we can wait until document is not load.
     *
     * @param webElement Web Element instance which need to wait.
     */
    public static WebElement fluentWait(final WebElement webElement) {
        final long t1 = System.currentTimeMillis();
        WebElement element = new FluentWait<>(webElement).withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(100)).ignoring(ElementNotVisibleException.class)
                .ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
                .ignoring(WebDriverException.class).until((Function<WebElement, WebElement>) element1 -> webElement);
        final long t2 = System.currentTimeMillis();
        LOGGER.debug("Time taken in fluentWait method : " + (t2 - t1));
        return element;
    }

    /**
     * Check If element is visible or not
     *
     * @param objLocator xpath of the element
     * @param webDriver  curent web driver instance
     */
    public static boolean checkInstantVisibilty(final WebDriver webDriver, final String objLocator) {
        boolean flag = false;
        String tempLocator = objLocator;
        if (!tempLocator.contains("===")) {
            tempLocator = "xpath===" + tempLocator;
        }
        By locator = getLocator(tempLocator);
        int count = 0;
        while (count < 10) {
            try {
                webDriver.findElement(locator);
                flag = true;
                break;
            } catch (Exception ex) {
                explicitWait(ApplicationConstants.WAIT_NORMAL);
                count++;
            }
        }
        LOGGER.debug("Element located  :: " + flag);
        return flag;
    }

    /**
     * Check If element is clickable or not
     *
     * @param objLocator Object locator
     * @param webDriver  Current web driver instance
     */
    public static boolean checkInstantClickable(final WebDriver webDriver, final String objLocator) {
        int count = 0;
        String tempLocator = objLocator;
        if (!tempLocator.contains("===")) {
            tempLocator = "xpath===" + tempLocator;
        }
        By locator = getLocator(tempLocator);
        while (count < 5) {
            try {
                if (webDriver.findElement(locator).isEnabled()) {
                    return true;
                }
            } catch (Exception ex) {
                explicitWait(ApplicationConstants.WAIT_MINIMUM);
                count++;
            }
        }
        return false;
    }

    public static LocalDateTime findDateBeforeDays(int numberOfDays) {
        return LocalDateTime.now().minusDays(numberOfDays);
    }

    public static String getDateString(LocalDateTime date) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        String sDate = date.getMonthValue() + "/" + date.getDayOfMonth() + "/" + date.getYear();
        LOGGER.debug("Date String is: " + methodName);
        return sDate;
    }

    public static int multipleResourceSelectInsideTable(final WebDriver webDriver, final String xpath,
                                                        final long timeout, final long pollingFrequency) {
        List<WebElement> tableRows = getMultipleElementsAfterLoaded(webDriver, xpath, timeout, pollingFrequency);
        Actions builder = new Actions(webDriver);
        explicitWait(ApplicationConstants.WAIT_NORMAL);
        if (tableRows.size() == 2) {
            builder.click(tableRows.get(0)).keyDown(Keys.CONTROL).click(tableRows.get(1)).keyUp(Keys.CONTROL).build()
                    .perform();
        } else if (tableRows.size() == 3) {
            builder.click(tableRows.get(0)).keyDown(Keys.CONTROL).click(tableRows.get(1)).click(tableRows.get(2))
                    .keyUp(Keys.CONTROL).build().perform();
        } else if (tableRows.size() >= 4) {
            builder.click(tableRows.get(0)).keyDown(Keys.CONTROL).click(tableRows.get(1)).click(tableRows.get(2))
                    .click(tableRows.get(3)).keyUp(Keys.CONTROL).build().perform();
        }
        explicitWait(2000);
        // returning number of rows selected
        return tableRows.size();
    }

    public static void mouseOver(final WebDriver webDriver, final String locator, final long timeout,
                                 final long pollingFrequency) {
        Actions action = new Actions(webDriver);

        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.debug("Executing Test Step::" + methodName);
        CustomReport.reporter("Current method is ", methodName);

        CustomReport.reporter("Clicking on ", locator);
        WebElement element = getElementAfterLoaded(webDriver, locator, timeout, pollingFrequency);
        action.moveToElement(element).build().perform();
    }

    public static void spinnerWait(final WebDriverWait webDriverWait, final WebDriver webDriver, final String locator) {
        spinnerWait(webDriverWait, webDriver, locator, ApplicationConstants.defaultTimeout,
                ApplicationConstants.defaultPollingFrequency);
    }

    public static void spinnerWait(final WebDriverWait webDriverWait, final WebDriver webDriver, final String locator,
                                   final long timeout, final long pollingFrequency) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.debug("Executing Test Step::" + methodName);
        CustomReport.reporter("Current method is", methodName);
        // Wait for spinner display as it take few seconds to appear on the page after
        // the button is clicked
        long t1 = System.currentTimeMillis();
        verifyElementDisplayed(webDriverWait, webDriver, locator, 20000, 1000);
        long t2 = System.currentTimeMillis();
        LOGGER.debug("******** Spinner visible time ***********" + ((t2 - t1) / 1000) + " Secs");
        // Wait for spinner close
        if (!waitForElementDisappear(webDriver, locator, ApplicationConstants.TYPE_DISPLAY, timeout,
                pollingFrequency)) {
            LogAndScreenShotUtil.logError(webDriver,
                    "Error occurred as spinner is still displayed on the UI. Please verify screenshot. Test cannot " +
                            "Proceed.",
                    null, SeleniumUtil.class, methodName);
        }
        long t3 = System.currentTimeMillis();
        LOGGER.debug("######### Spinner visible time ##########" + ((t3 - t2) / 1000) + " Secs");
    }

    public static void spinnerWaitForSpecificTime(final WebDriverWait webDriverWait, final WebDriver webDriver,
                                                  final String locator,
                                                  final long timeout, final long pollingFrequency) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.debug("Executing Test Step::" + methodName);
        CustomReport.reporter("Current method is", methodName);
        // Wait for spinner display as it take few seconds to appear on the page after
        // the button is clicked
        long t1 = System.currentTimeMillis();
        verifyElementDisplayed(webDriverWait, webDriver, locator, timeout, pollingFrequency);
        long t2 = System.currentTimeMillis();
        LOGGER.debug("******** Spinner visible time ***********" + ((t2 - t1) / 1000) + " Secs");
        // Wait for spinner close
        if (!waitForElementDisappear(webDriver, locator, ApplicationConstants.TYPE_DISPLAY, timeout,
                pollingFrequency)) {
            LogAndScreenShotUtil.logError(webDriver,
                    "Error occurred as spinner is still displayed on the UI. Please verify screenshot. Test cannot " +
                            "Proceed.",
                    null, SeleniumUtil.class, methodName);
        }
        long t3 = System.currentTimeMillis();
        LOGGER.debug("######### Spinner visible time ##########" + ((t3 - t2) / 1000) + " Secs");
    }

    public static void jsClick(final WebDriver webDriver, final String locator,
                               final long timeout, final long pollingFrequency) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.debug("Executing Test Step::" + methodName);
        CustomReport.reporter("Current method is", methodName);
        CustomReport.reporter("Clicking on ", "" + locator);

        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        WebElement element = getElementAfterLoaded(webDriver, locator, timeout, pollingFrequency);
        jse.executeScript("arguments[0].click();", element);

        CustomReport.reporter("Clicked on ", "" + locator);
        LOGGER.debug("The Given Element is clicked");
    }

    public static void jsClick(final WebDriver webDriver, final WebElement element) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.debug("Executing Test Step::" + methodName);
        CustomReport.reporter("Current method is", methodName);

        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("arguments[0].click();", element);

        CustomReport.reporter("Clicked on given element");
        LOGGER.debug("The Given Element is clicked");
    }

    public static void pressEnterKey(final WebDriver webDriver, final String locator, final long timeout,
                                     final long pollingFrequency) {
        WebElement element = getElementAfterLoaded(webDriver, locator, timeout, pollingFrequency);
        element.sendKeys(Keys.ENTER);
    }

    public static void pressEscape(final WebDriver webDriver) {
        webDriver.findElement(By.xpath("//body")).sendKeys(Keys.ESCAPE);
    }

    public static void refreshBrowser(final WebDriver webDriver) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.debug("Executing Test Step::" + methodName);
        CustomReport.reporter("Current method is ", methodName);

        webDriver.navigate().refresh();
    }

    public static void waitForElementPresence(final WebDriverWait webDriverWait,
                                              final String locator) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.debug("Executing Test Step::" + methodName);

        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(getLocator(locator)));
    }

    public static void waitForElementToBeClickable(final WebDriverWait webDriverWait,
                                                   final String locator) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.debug("Executing Test Step::" + methodName);

        webDriverWait.until(ExpectedConditions.elementToBeClickable(getLocator(locator)));
    }

    public static Dimension getDimension(WebDriver webDriver, String locator) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.info("Executing Test Step::" + methodName);
        CustomReport.reporter("Current method is ", methodName);

        WebElement element = getElementAfterLoaded(webDriver, locator);
        return element.getSize();
    }

    public static Document getHtmlDOM(WebDriver webDriver, String locator) {
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.info("Executing Test Step::" + methodName);
        CustomReport.reporter("Current method is ", methodName);

        String attr = getElementAttribute(webDriver, locator, "outerHTML");
        LOGGER.info("*****HTML Fragment*****");
        LOGGER.info(attr);
        return Jsoup.parseBodyFragment(attr);
    }

    public static List<String> getSelectedValuesFromHtmlDOM(Document dom, String cssQuery) {
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        LOGGER.info("Executing Test Step::" + methodName);
        CustomReport.reporter("Current method is ", methodName);

        List<String> toReturn = new ArrayList<>();
        Elements elems = dom.select(cssQuery);
        for (Element ele : elems) {
            String val = ele.text();
            if (StringUtils.isNotBlank(val)) {
                toReturn.add(val);
            }
        }
        return toReturn;
    }

    public static void uncheckMultipleElements(WebDriver webDriver, String locator) {
        try {
            String methodName = new Object() {
            }.getClass().getEnclosingMethod().getName();
            LOGGER.info("Executing Test Step::" + methodName);
            CustomReport.reporter("Current method is ", methodName);

            List<WebElement> elements = webDriver.findElements(getLocator(locator));
            for (WebElement el : elements) {
                if (el.isSelected()) {
                    el.click();
                }
            }
        } catch (Exception e) {
            LOGGER.info("failed to check all checkbox");
        }
    }


    public static void checkMultipleElements(WebDriver webDriver, String locator) {
        try {
            String methodName = new Object() {
            }.getClass().getEnclosingMethod().getName();
            LOGGER.info("Executing Test Step::" + methodName);
            CustomReport.reporter("Current method is ", methodName);

            List<WebElement> elements = webDriver.findElements(getLocator(locator));
            for (WebElement el : elements) {
                if (!el.isSelected()) {
                    el.click();
                }
            }
        } catch (Exception e) {
            LOGGER.info("failed to check all checkbox");
        }
    }

    public static void switchTabs(WebDriver webDriver) {
        try {
            String methodName = new Object() {
            }.getClass().getEnclosingMethod().getName();
            LOGGER.info("Executing Test Step::" + methodName);
            CustomReport.reporter("Current method is ", methodName);

            List<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(1));

        } catch (Exception e) {
            LOGGER.info("Failed to Switch Tabs");
        }
    }

    public static void closeTab(WebDriver webDriver) {
        try {
            String methodName = new Object() {
            }.getClass().getEnclosingMethod().getName();
            LOGGER.info("Executing Test Step::" + methodName);
            CustomReport.reporter("Current method is ", methodName);

            List<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.close();
            webDriver.switchTo().window(tabs.get(0));

        } catch (Exception e) {
            LOGGER.info("Failed to Close Tabs");
        }
    }

}
