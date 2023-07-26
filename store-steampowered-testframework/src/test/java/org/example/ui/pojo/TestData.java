package org.example.ui.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Getter
public class TestData {
    private static final Logger logger = LoggerFactory.getLogger(TestData.class);
    private String newsResultMessage;
    private Integer revisionYear;
    private List<String> languages;
    private List<Integer> gamePositions;
    private static TestData testData;

    private TestData() {
    }

    public static TestData getTestData(String testDataPath) {
        if (testData == null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                File jsonFile = new File(testDataPath);
                testData = objectMapper.readValue(jsonFile, TestData.class);
                return testData;
            } catch (IOException e) {
                logger.error("test-data file read error:\n" + e.getMessage());
            }
        }
        return testData;
    }
}