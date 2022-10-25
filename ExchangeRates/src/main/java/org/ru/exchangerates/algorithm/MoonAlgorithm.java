package org.ru.exchangerates.algorithm;

import lombok.Getter;
import org.ru.exchangerates.courseplot.GraphArgs;
import org.ru.exchangerates.domain.PredictionRequest;
import org.ru.exchangerates.repository.ResourceReader;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.stream.Collectors;

@Getter
public class MoonAlgorithm implements Algorithm {
    private final ArrayList<Double> courseList = new ArrayList<>();
    private final ArrayList<String> currencyList = new ArrayList<>();
    private final ArrayList<LocalDate> dateList = new ArrayList<>();
    private LocalDate inputRequestDate;
    private final ArrayList<String> result = new ArrayList<>();
    private final PredictionRequest predictionRequest;
    private final ArrayList<GraphArgs> graphArgsList = new ArrayList<>();

    public MoonAlgorithm(PredictionRequest predictionRequest) {
        this.predictionRequest = predictionRequest;
        new HashSet<>(predictionRequest.getCurrencyInputList())
                .forEach(cur -> graphArgsList.add(new GraphArgs(cur)));
    }

    public void initiateResultList(LocalDate requestDate) {
        if (predictionRequest.getPredictionAlg().equals("MOON")) {
            this.inputRequestDate = requestDate;
        }
        for (int i = 0; i < predictionRequest.getCurrencyInputList().size(); i++) {
            String fileName = "/" + predictionRequest.getCurrencyInputList().get(i) + ".csv";
            requestDate = requestDate.minusYears(1);
            ResourceReader reader = new ResourceReader(fileName);
            boolean courseFound = false;
            while (!courseFound) {
                reader.read();
                LocalDate parsedDate = reader.getParsedDate();
                BigDecimal parsedCourse = reader.getParsedCourse();
                if (parsedDate.equals(requestDate)) {
                    if (predictionRequest.getOutputType().equals("GRAPH")) {
                        initiateGraphArgs(predictionRequest.getCurrencyInputList().get(i),
                                inputRequestDate,
                                parsedCourse);
                    }
                    result.add(getStringDate(inputRequestDate) + " - " + parsedCourse);
                    courseFound = true;
                    reader.close();
                } else if (ChronoUnit.DAYS.between(requestDate, parsedDate) < 0) {
                    if (predictionRequest.getOutputType().equals("GRAPH")) {
                        initiateGraphArgs(predictionRequest.getCurrencyInputList().get(i),
                                inputRequestDate,
                                parsedCourse);
                    }
                    result.add(getStringDate(inputRequestDate) + " - " + parsedCourse);
                    courseFound = true;
                    reader.close();
                }
            }
        }
    }

    public void setInputRequestDate(LocalDate inputRequestDate) {
        this.inputRequestDate = inputRequestDate;
    }

    protected void initiateGraphArgs(String currency, LocalDate requestDate, BigDecimal parsedCourse) {
        graphArgsList.stream().forEach(graphArgs -> {
            if (graphArgs.getCurrency().equals(currency)) {
                graphArgs.addDateAndCourse(requestDate, parsedCourse);
            }
        });
    }

    protected String getStringDate(LocalDate resultDate) {
        String dayOfWeek = resultDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, new Locale("ru"));
        dayOfWeek = dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1);
        return dayOfWeek + " " + resultDate.format(formatter);
    }
}
