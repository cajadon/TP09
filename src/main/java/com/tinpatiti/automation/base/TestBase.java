package com.tinpatiti.automation.base;

import com.tinpatiti.automation.constants.ApplicationConstants;
import com.tinpatiti.automation.util.ValueValidations;
import com.tinpatiti.util.ReadResources;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.ChromiumDriverManager;
import org.apache.commons.lang3.SystemUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestBase {
    private static final Logger LOGGER = Logger.getLogger(TestBase.class);

    public static final String STR_CHROME_HEADLESS = "chrome_headless";

    private WebDriver webDriver;
    private WebDriverWait webDriverWait;

    private ReadResources readResources;

    private Map<String, String> readConfigUrls;
    private Map<String, String> readConfigData;
    private Map<String, String> databaseConfigMap;

    public static long timeout = ApplicationConstants.defaultTimeout;
    public static long pollingFrequency = ApplicationConstants.defaultPollingFrequency;

    private String basePath;
    private String envName;
    private String userName;
    private String password;
    private String envUrl;
    private String dbUrl;
    private String dbUserName;
    private String dbPassword;
    public String browser;
    private String globalGPEC = null;
    private String globalADM = null;

    private Properties props = null;

    private static String osName;

    protected void initializeGlobalVariables() {
        envName = System.getProperty("envName");
        if (!ValueValidations.isValueValid(envName)) {
            envName = readConfigData.get("envName");
        }

        envUrl = readConfigUrls.get(envName);

        userName = System.getProperty("userName");
        if (!ValueValidations.isValueValid(userName)) {
            userName = readConfigData.get("userName");
        }

        password = System.getProperty("password");
        if (!ValueValidations.isValueValid(password)) {
            password = readConfigData.get("password");
        }

        browser = System.getProperty("browser");
        if (!ValueValidations.isValueValid(browser)) {
            browser = readConfigData.get("browser");
        }

        dbUrl = databaseConfigMap.get(envName);
        dbUserName = databaseConfigMap.get(envName + "UserName");
        dbPassword = databaseConfigMap.get(envName + "Password");

        String eol = System.getProperty("line.separator");
        LOGGER.info("System will initialized with following values :" + eol + "Environment : " + envName + eol
                + "Environment URL : " + envUrl + eol + "Username : " + userName + eol + "Password : " + password);
    }

    public WebDriver initializeWebDriver(String browser) {
        basePath = new File("").getAbsolutePath() + File.separator + ApplicationConstants.DOWNLOAD_FOLDER;
        String browserValue = browser.trim();
        LOGGER.info(basePath);
        try {
            File downloadDir = new File(basePath);
            if (!downloadDir.exists()) {
                if(downloadDir.mkdirs()){
                    LOGGER.info("Folder is created at ["+basePath+"] during initialization of web driver");
                }
            }
        } catch (Exception e) {
            LOGGER.error("Folder is not created during initialization of web driver");
        }
        if (browserValue.contains("Firefox")){
            webDriver = new FirefoxDriver();
            webDriver.manage().window().maximize();

        }
        if (browserValue.contains("chrome")) {
            if (!browserValue.equalsIgnoreCase("chrome_headless")) {
                if (ValueValidations.isValueValid(readConfigData.get("chromeDriverVersion"))) {
                    ChromeDriverManager.chromedriver().version(readConfigData.get("chromeDriverVersion")).setup();
                } else {
                    ChromeDriverManager.chromedriver().setup();
                }
            }
            ChromeOptions options = new ChromeOptions();
            HashMap<String, Object> chromePrefs = new HashMap<>();
            // chromePrefs.put("profile.default_content_settings.popups", 0);
            options.addArguments("--disable-popup-blocking");
            chromePrefs.put("download.default_directory", basePath);
            chromePrefs.put("safebrowsing.enabled", "true");
            // automation test window
            if (SystemUtils.IS_OS_LINUX) {
                options.addArguments("--ppapi-flash-path=" + readConfigData.get("flash_path"));
                options.addArguments("--ppapi-flash-version=" + readConfigData.get("flash_version"));
            }
            options.addArguments("--disable-extensions");
//            options.addArguments("--disable-infobars");
            options.addArguments("--safebrowsing-disable-download-protection");
            options.addArguments("--allow-outdated-plugins");
            options.addArguments("--start-maximized");
            options.addArguments("--disable-gpu");

            options.setExperimentalOption("useAutomationExtension", false);
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

            // Enable Flash
            chromePrefs.put("profile.default_content_setting_values.plugins", 1);
            chromePrefs.put("profile.content_settings.plugin_whitelist.adobe-flash-player", 1);
            chromePrefs.put("profile.content_settings.exceptions.plugins.*,*.per_resource.adobe-flash-player", 1);

            // Hide save credentials prompt
            chromePrefs.put("credentials_enable_service", false);
            chromePrefs.put("profile.password_manager_enabled", false);

            options.setExperimentalOption("prefs", chromePrefs);
            options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

            if (browserValue.equalsIgnoreCase("chrome_headless")) {
                if (ValueValidations.isValueValid(readConfigData.get("chromiumDriverVersion"))) {
                    ChromiumDriverManager.chromiumdriver().version(readConfigData.get("chromiumDriverVersion")).setup();
                } else {
                    ChromiumDriverManager.chromiumdriver().setup();
                }

                options.addArguments("--headless");
                options.addArguments("--window-size=1920x1080x16");
                options.setBinary("/usr/bin/chromium-browser");

                // Enable download in chrome_headless
                ChromeDriverService driverService = ChromeDriverService.createDefaultService();
                ChromeDriver driver = new ChromeDriver(driverService, options);
                Map<String, Object> commandParams = new HashMap<>();
                commandParams.put("cmd", "Page.setDownloadBehavior");
                Map<String, String> params = new HashMap<>();
                params.put("behavior", "allow");
                params.put("downloadPath", basePath);
                commandParams.put("params", params);
                ObjectMapper objectMapper = new ObjectMapper();
                HttpClient httpClient = HttpClientBuilder.create().build();
                try {
                    String command = objectMapper.writeValueAsString(commandParams);
                    String u = driverService.getUrl().toString() + "/session/" + driver.getSessionId() + "/chromium" +
							"/send_command";
                    HttpPost request = new HttpPost(u);
                    request.addHeader("content-type", "application/json");
                    request.setEntity(new StringEntity(command));
                    httpClient.execute(request);
                    LOGGER.info("Chrome headless is enabled with file download");
                } catch (IOException e) {
                    LOGGER.error("Chrome headless is not enabled with file download");
                }
                webDriver = driver;
            } else {
                webDriver = new ChromeDriver(options);
            }
            LOGGER.info("Initializing chrome webDriver");
        }
        return webDriver;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebDriverWait getWebDriverWait() {
        return webDriverWait;
    }

    public void setWebDriverWait(WebDriverWait webDriverWait) {
        this.webDriverWait = webDriverWait;
    }

    public ReadResources getReadResources() {
        return readResources;
    }

    public void setReadResources(ReadResources readResources) {
        this.readResources = readResources;
    }

    public Map<String, String> getReadConfigUrls() {
        return readConfigUrls;
    }

    public void setReadConfigUrls(Map<String, String> readConfigUrls) {
        this.readConfigUrls = readConfigUrls;
    }

    public Map<String, String> getReadConfigData() {
        return readConfigData;
    }

    public void setReadConfigData(Map<String, String> readConfigData) {
        this.readConfigData = readConfigData;
    }

    public static long getTimeout() {
        return timeout;
    }

    public static void setTimeout(long timeout) {
        TestBase.timeout = timeout;
    }

    public static long getPollingFrequency() {
        return pollingFrequency;
    }

    public static void setPollingFrequency(long pollingFrequency) {
        TestBase.pollingFrequency = pollingFrequency;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnvUrl() {
        return envUrl;
    }

    public void setEnvUrl(String envUrl) {
        this.envUrl = envUrl;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public static String getOsName() {
        return osName;
    }

    public static void setOsName(String osName) {
        TestBase.osName = osName;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getGlobalGPEC() {
        return globalGPEC;
    }

    public void setGlobalGPEC(String globalGPEC) {
        this.globalGPEC = globalGPEC;
    }

    public String getGlobalADM() {
        return globalADM;
    }

    public void setGlobalADM(String globalADM) {
        this.globalADM = globalADM;
    }

    public Map<String, String> getDatabaseConfigMap() {
        return databaseConfigMap;
    }

    public void setDatabaseConfigMap(Map<String, String> databaseConfigMap) {
        this.databaseConfigMap = databaseConfigMap;
    }

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }
}
