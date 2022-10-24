package org.ru.exchangerates.repository;

import org.apache.log4j.Logger;
import org.ru.exchangerates.domain.ValidatorCreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.ru.exchangerates.algorithm.Algorithm.formatter;

public class ResourceReader {
    private Logger logger = Logger.getLogger(ResourceReader.class);
    private LocalDate parsedDate;
    private BigDecimal parsedCourse;
    private final BufferedReader reader;

    public ResourceReader(String fileName) {
        InputStreamReader inputStr = new InputStreamReader(getClass().getResourceAsStream(fileName));
        this.reader = new BufferedReader(inputStr);
    }

    public void read() {
        try {
            if (reader.ready()) {
                String line = reader.readLine();
                if (line.split(";")[0].equals("nominal")) {
                    line = reader.readLine();
                }
                String[] lineArray = line.split(";");
                parsedDate = LocalDate.parse(lineArray[1], formatter);
                parsedCourse = new BigDecimal(lineArray[2]);
                BigDecimal nominal = new BigDecimal(lineArray[0]);
                parsedCourse = parsedCourse.divide(nominal).setScale(2, RoundingMode.HALF_UP);
            }
        } catch (IOException ex) {
            logger.error("Ошибка чтения ресурсов");
        }
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LocalDate getParsedDate() {
        return parsedDate;
    }

    public BigDecimal getParsedCourse() {
        return parsedCourse;
    }
}
