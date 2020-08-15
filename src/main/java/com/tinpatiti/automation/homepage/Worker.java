package com.tinpatiti.automation.homepage;

import com.tinpatiti.automation.base.InitializeResource;
import com.tinpatiti.automation.common.ApplicationUtilities;
import com.tinpatiti.automation.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.io.FileNotFoundException;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.File;
import java.util.concurrent.TimeUnit;


public class Worker extends InitializeResource {

    private final Logger LOGGER = Logger.getLogger(Worker.class);
    private String testCaseName;
    private ApplicationUtilities applicationUtilities;

    public static void takeSnapShot(WebDriver driver, String fileWithPath) throws Exception {
        TakesScreenshot scrShot = (TakesScreenshot) driver;
        //Take a screenshot
        File scrFile = scrShot.getScreenshotAs(OutputType.FILE);
        //create a file
        File DestFile = new File(fileWithPath);
        //copy file
        FileUtils.copyFile(scrFile, DestFile);
    }

    public static String cardReader(String src) {
        int startIndex = src.lastIndexOf("/");
        int endIndex = src.lastIndexOf(".");

        String cardCode = src.substring(startIndex + 1, endIndex);
        int breakPoint = cardCode.length() / 2;

        String prefix = cardCode.substring(0, breakPoint);
        String postfix = null;
        //System.out.println(cardCode.substring(breakPoint, cardCode.length()));

        switch (cardCode.substring(breakPoint, cardCode.length())) {
            case "CC":
                postfix = "clubs";
                break;
            case "DD":
                postfix = "hearts";
                break;
            case "HH":
                postfix = "spades";
                break;
            case "SS":
                postfix = "diamonds";
                break;

        }
        return prefix + " of " + postfix;
    }

    public static void openResults(WebDriver driver, WebDriverWait driverWait, String locator, Logger LOGGER) throws InterruptedException {
        LOGGER.info("Entered in the openResults function");
        driver.get("http://tenexch.com/casinoresult/2020teenpatti");
        Thread.sleep(10000);
        try{
            SeleniumUtil.waitForElementPresence(driverWait, locator);
        }catch(Exception e){
            openResults(driver, driverWait, locator, LOGGER);
        }
        Thread.sleep(5000);
        SeleniumUtil.click(driver, driverWait, locator);
        //SeleniumUtil.waitForElementPresence(driverWait, "xpath===//div[@class='col br1 text-center']//img[1]");
        try{
            SeleniumUtil.waitForElementPresence(driverWait, "xpath===//div[@class='col br1 text-center']//img[1]");
        }catch (Exception e){
            openResults(driver, driverWait, locator, LOGGER);
        }
    }
    public static String getRoundID(WebDriver driver, WebDriverWait driverWait, String locator) {
//        try{
//            SeleniumUtil.waitForElementPresence(driverWait, locator);
//        }catch(Exception e){
//            driver.get("http://tenexch.com/teenpatti/twentytwenty");
//            SeleniumUtil.explicitWait(8000);
//            SeleniumUtil.click(driver, driverWait, locator);
//        }
        String text = null;
        int startIndex = 0;
        try{
            text = SeleniumUtil.getElementText(driver, locator);
            System.out.println(text);
            startIndex = text.lastIndexOf(":");
        }catch(Exception e){
            getRoundID(driver, driverWait, locator);
        }

        return text.substring(startIndex + 2, text.length());

    }

    public static int getTableRow(String preRoundID, WebDriver driver, Logger LOGGER) throws InterruptedException {
        String reqRoundID = null;
        int tableIndex = 0;
        LOGGER.info("Entered in getTableRow function");
        Thread.sleep(10000);
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='casinoResultReport']/tbody/tr"));
        //List<WebElement> rows = driver.findElements(By.tagName("tr"));
        int count = rows.size();
        LOGGER.info("row Count: "+count);
        if (count>10){
            count = 11;
        }
        for(int i=1;i<count;i++){

            reqRoundID = SeleniumUtil.getElementText(driver,"xpath===//div[@class='table-responsive col-sm-12']//tr["+i+"]//td[1]");


            if(reqRoundID.isEmpty()){
                LOGGER.info("Entered in if(empty) block of getTableRow function");
                driver.get("http://tenexch.com/casinoresult/2020teenpatti");
                Thread.sleep(15000);
                getTableRow(preRoundID,driver,LOGGER);
            }
            LOGGER.info("reqRoundID: "+reqRoundID);
            if(preRoundID.equals(reqRoundID)){
                LOGGER.info("Entered in if(req) condition of getTableRow function");
                tableIndex =  i-1;
                break;
            }
            else{
                tableIndex = 1;
            }
        }
        return tableIndex;
    }
    public static String getRoundID(WebDriver driver, Logger LOGGER) throws InterruptedException, FileNotFoundException {
        LOGGER.info("Entered in getRoundID function");

        driver.get("http://tenexch.com/casinoresult/2020teenpatti");
        Thread.sleep(15000);
        File resultFile = new File("Results.txt");
        Scanner myReader = new Scanner(resultFile);
        String preRoundID = myReader.nextLine();
        LOGGER.info("prevRoundID:"+ preRoundID);
        String newRoundID = null;
        driver.navigate().refresh();

        int tableIndex = getTableRow(preRoundID, driver, LOGGER);
        LOGGER.info("TableIndex: "+tableIndex);
        if(tableIndex>0 && tableIndex<10){
            newRoundID = driver.findElement(By.xpath("//div[@class='table-responsive col-sm-12']//tr["+tableIndex+"]//td[1]")).getText();
            LOGGER.info(newRoundID);
            LOGGER.info("Entered in first if condition of getroundID");
            return newRoundID;
        }
        else if(tableIndex==10){
            newRoundID = driver.findElement(By.xpath("//div[@class='table-responsive col-sm-12']//tr[1]//td[1]")).getText();
            return newRoundID;
        }
        else{
            getRoundID(driver,LOGGER);
        }
        LOGGER.info(newRoundID);
        return newRoundID;
    }

    public boolean waitForTextToChange(WebElement element, String currentText) {
        return !element.getText().equals(currentText);
    }

    public static String readImage(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        System.out.println("Java script object created");
        String src = js.executeScript("return arguments[0].attributes['src'].value;", element).toString();
        System.out.println("image read");
        return src;
    }

    public static String getDate(String roundId) {
        if(roundId.equals("round_id")){
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();
            System.out.println(df.format(dateobj));
            return df.format(dateobj).substring(0,7);
        }
        String day = roundId.substring(2, 4);
        String month = roundId.substring(4, 6);
        String year = roundId.substring(0, 2);
        return day + "-" + month + "-" + year;

    }

    public static String getTime(String roundId) {
        if(roundId.equals("round_id")){
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();
            System.out.println(df.format(dateobj));
            return df.format(dateobj).substring(9,16);
        }
        String hour = roundId.substring(6, 8);
        String minute = roundId.substring(8, 10);
        String second = roundId.substring(10, 12);
        return hour + ":" + minute + ":" + second;
    }

    public void startProcess(DataFile writer, SendEmail notify) throws Exception {

        try {
            LOGGER.info("Entered in try block");
            UpdateDataSheet sheet = new UpdateDataSheet();

            int i = 0;
            String roundID = null;
            String[] cardsA = new String[3];
            String[] cardsB = new String[3];
            String date = null;
            String time = null;
            String winner = null;

            while (true) {
                LOGGER.info("Entered in While loop");
                //getWebDriver().get("http://tenexch.com/teenpatti/twentytwenty");
                //String parentWindowHandler =  getWebDriver().getWindowHandle(); // Store your parent window
                //String subWindowHandler = null;
                getWebDriver().get("http://tenexch.com/casinoresult/2020teenpatti");
                LOGGER.info("Opened Result page");
                roundID = getRoundID(getWebDriver(), LOGGER);
                System.out.println("roundId from getRoundID function"+roundID);
                date = getDate(roundID);
                System.out.println(date);

                time = getTime(roundID);
                System.out.println(time);
                Thread.sleep(4000);
                String resultLocator = "xpath===//span[contains(text(),'"+roundID+"')]";
                LOGGER.info(resultLocator);
                //Winner = getWinner();
                String WinnerLocator = resultLocator+ "/../../../following-sibling::td";
                winner = SeleniumUtil.getElementText(getWebDriver(), WinnerLocator);
                winner = winner.substring(6,8);
                LOGGER.info("Winner: ");
                System.out.println(winner);

                //SeleniumUtil.click(getWebDriverWait(), getWebDriver(),resultLocator);
                openResults(getWebDriver(), getWebDriverWait(), resultLocator, LOGGER);
                LOGGER.info("Results Opened");


                for (int j = 1; j <= 3; j++) {
                    LOGGER.info("Entered in first for loop");
                    SeleniumUtil.explicitWait(10000);


                    WebElement element = SeleniumUtil.getElementAfterLoaded(getWebDriver(),"xpath===//div[@class='col br1 text-center']//img[" + j + "]");
                    String src = readImage(getWebDriver(), element);

                    //SeleniumUtil.getElementAfterLoaded()

                    String cardName = cardReader(src);
                    System.out.println(cardName);
                    cardsA[j - 1] = cardName;
                    System.out.println(cardName);
                }
                for (int j = 1; j <= 3; j++) {
                    WebElement element = SeleniumUtil.getElementAfterLoaded(getWebDriver(),"xpath===//div[@class='col text-center']//img[" + j + "]");
                    String src = readImage(getWebDriver(), element);

                    String cardName = cardReader(src);
                    cardsB[j - 1] = cardName;
                    System.out.println(cardName);
                }
                takeSnapShot(getWebDriver(), "C:\\TINPATTI\\Screenshots\\Sc" + i + ".PNG");

                writer.writeData(roundID, date, time, winner, cardsA, cardsB);
                sheet.callSheets();
                //SeleniumUtil.click(getWebDriver(), getWebDriverWait(), getProps().getProperty("closeResultLocator"));

                //getWebDriver().switchTo().window(parentWindowHandler);  // switch back to parent window
                getWebDriver().navigate().refresh();
                //getWebDriverWait().until(waitForTextToChange(element, currentText));
                SeleniumUtil.explicitWait(5000);
                i++;
            }

        } catch (Exception e) {
            writer.writeException(e.getMessage());
            notify.sendEmail();
            startProcess(writer, notify);

//        notify.sendEmail();

            LogAndScreenShotUtil.logError(getWebDriver(),
                    "Exception in [" + testCaseName + "] during executing test case :: " + e.getMessage(), e,
                    getClass(), testCaseName);
        }
    }


    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        try {
            launchURL(new Object() {
            }.getClass().getName());
            applicationUtilities = new ApplicationUtilities();
            LOGGER.info("in beforeClass");
            userLogin(getUserName(), getPassword());

        } catch (Exception e) {
            LogAndScreenShotUtil.logError(getWebDriver(), "Error occurred in before class method ::" + e.getMessage()
                    , e,
                    getClass(), "Before_Class_" + System.currentTimeMillis());
        }
    }

@Test(description = "Worker :: Login and Collect Data", groups = "P1")
    public void worker() throws Exception {
    testCaseName = new Object() {
    }.getClass().getEnclosingMethod().getName();
    LOGGER.info("The TestCase Executing is : " + testCaseName);
    CustomReport.reporter("The TestCase Executing is : " + testCaseName);
    SendEmail notify = new SendEmail();
    DataFile writer = new DataFile();
    startProcess(writer, notify);

}
    @AfterClass(alwaysRun = true)
    public void afterClass() {
        // Close and quit the browser
        applicationUtilities.closeTheBrowser(getWebDriver());
    }
}
