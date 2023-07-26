package org.example.ui.utils;

import org.example.ui.data.ConfigData;

import java.util.List;

public class DataHelper {
    private static final String CONFIG_DATA_PATH = "src/test/resources/config-data.json";
    private static final String TEST_DATA_PATH = "src/test/resources/test-data.json";
    private static final ConfigData configData = ConfigData.getConfigData(CONFIG_DATA_PATH);

    public static String getStartUrl() {
        return configData.getStartUrl();
    }
    public static Integer getWaitTime() {
        return configData.getWaitTime();
    }
    public static String getBrowserType() {
        return configData.getBrowserType();
    }
    public static List<String> getBrowserOptions() {
        return configData.getDriverOptions();
    }
    public static String getBrowserSize() {
        return configData.getBrowserSize();
    }
}
