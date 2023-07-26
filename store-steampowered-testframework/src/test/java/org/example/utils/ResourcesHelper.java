package org.example.utils;

import org.example.ui.pojo.ConfigData;
import org.example.ui.pojo.TestData;

import java.util.List;

public class ResourcesHelper {
    private static final String CONFIG_DATA_PATH = "src/test/resources/config-data.json";
    private static final String TEST_DATA_PATH = "src/test/resources/test-data.json";

    private static ConfigData getConfigData() {
        return ConfigData.getConfigData(CONFIG_DATA_PATH);
    }

    private static TestData getTestData() {
        return TestData.getTestData(TEST_DATA_PATH);
    }

    public static List<String> getDriverOptions() {
        return getConfigData().getDriverOptions();
    }

    public static int getWaitTime() {
        return getConfigData().getWaitTime();
    }

    public static String getStartUrl() {
        return getConfigData().getStartUrl();
    }

    public static List<String> getLanguagesTestData() {
        return getTestData().getLanguages();
    }

    public static List<Integer> getGamePositionsTestData() {
        return getTestData().getGamePositions();
    }

    public static Integer getRevisionTestData() {
        return getTestData().getRevisionYear();
    }

    public static String getBrowserType() {
        return getConfigData().getBrowserType();
    }

    public static String getNewsResultMessage() {
        return getTestData().getNewsResultMessage();
    }
}