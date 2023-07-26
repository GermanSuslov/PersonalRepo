package org.example.ui.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Getter
public class ConfigData {
    private static final Logger logger = LoggerFactory.getLogger(ConfigData.class);
    private Integer waitTime;
    private String startUrl;
    private String browserType;
    private List<String> driverOptions;
    private static ConfigData configData;

    private ConfigData() {
    }

    public static ConfigData getConfigData(String configDataPath) {
        if (configData == null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                File jsonFile = new File(configDataPath);
                configData = objectMapper.readValue(jsonFile, ConfigData.class);
                return configData;
            } catch (IOException e) {
                logger.error("config-data file read error:\n" + e.getMessage());
            }
        }
        return configData;
    }
}