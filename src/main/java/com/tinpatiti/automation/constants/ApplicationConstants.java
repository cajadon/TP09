package com.tinpatiti.automation.constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ApplicationConstants {

    public static final long defaultTimeout = 8000;
    public static final long defaultPollingFrequency = 600;

    public static final long updatedTimeout = 30000;
    public static final long updatedPollingFrequency = 800;

    public static final Integer WAIT_MINIMUM = 1000;
    public static final Integer WAIT_NORMAL = 2000;
    public static final Integer WAIT_MEDIUM = 3000;
    public static final Integer WAIT_HIGH = 5000;
    public static final Integer WAIT_MAX = 12000;
    public static final Integer WAIT_FOR_XML_UPDATE = 40000;
    public static final Integer WAIT_AFTER_CONFIG_PUSH_TO_CONTROLLER=120000;
    //
    public static final String TYPE_DISPLAY = "Display";
    public static final String TYPE_ENABLE = "Enable";

    public static final String DROPDOWN_BYVALUE = "value";
    public static final String DROPDOWN_BYVISIBLETEXT = "visibleText";
    public static final String DROPDOWN_BYINDEX = "index";

    public static final String DOWNLOAD_FOLDER = "files_download";

    public static final Properties XPATH_PROPS = loadConfigFiles();

    public static final Properties loadConfigFiles() {
        Properties props = new Properties();
        props = loadMenuOptionsConfigFile("xpaths.properties", props);
        props.putAll(loadMenuOptionsConfigFile("testData.properties", props));
        return props;
    }

    private static final Properties loadMenuOptionsConfigFile(final String resource, final Properties props) {
        InputStream input = ApplicationConstants.class.getClassLoader().getResourceAsStream(resource);
        try {
            if (input == null) {
                return null;
            }
            props.load(input);
            return props;
        } catch (Exception e) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
