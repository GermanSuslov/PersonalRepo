package org.Exchange;

import org.jfree.chart.plot.XYPlot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Prediction {
    private final String predictionType;
    private String predictionDate;
    private final String predictionAlg;
    private final String outputType;
    private LocalDate currentDate;
    private final String currency;
    private ArrayList<String> result = new ArrayList<>();

    private ArrayList<Double> courseList = new ArrayList<>();

    private ArrayList<String> currencyList = new ArrayList<>();
    private ArrayList<LocalDate> dateList = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    /**
     * Создает запрос на прогноз
     *
     * @param currency       вид валюты
     * @param predictionType тип прогноза
     */
    public Prediction(String currency, String predictionType, String predictionDate, String predictionAlg, String outputType) {
        this.currency = currency;
        this.predictionType = predictionType;
        this.predictionDate = predictionDate;
        this.predictionAlg = predictionAlg;
        this.outputType = outputType;
        this.currentDate = LocalDate.now();
    }

    public Prediction(String currency, String predictionType, String predictionDate, String predictionAlg) {
        this.currency = currency;
        this.predictionType = predictionType;
        this.predictionDate = predictionDate;
        this.predictionAlg = predictionAlg;
        this.outputType = "LIST";
        this.currentDate = LocalDate.now();
    }

    public ArrayList<Double> getCourseList() {
        return courseList;
    }

    public ArrayList<String> getCurrencyList() {
        return currencyList;
    }

    public ArrayList<LocalDate> getDateList() {
        return dateList;
    }

    public String getPredictionType() {
        return predictionType;
    }

    public String getOutputType() {
        return outputType;
    }

    /**
     * Возвращает список с результатом прогноза
     */
    public ArrayList<String> getPrediction() {
        switch (this.getPredictionType()) {
            case "-DATE":
                if (this.predictionDate.matches("^\\d{2}.\\d{2}.\\d{4}$")) {
                    datePrediction();
                } else {
                    LocalDate tomorrowDate = LocalDate.now().plusDays(1);
                    predictionDate = tomorrowDate.format(formatter);
                    datePrediction();
                }
            case "-PERIOD":
                if (this.predictionDate.equals("WEEK")) {
                    periodPrediction(7);
                }
                if(this.predictionDate.equals("MONTH")) {
                    periodPrediction(30);
                }
        }
        return result;
    }

    private void periodPrediction(int daysCount) {
        switch (predictionAlg) {
            case "MIST":
                for (int day = 0; day < daysCount; day++) {
                    mistAlgorithm(currentDate);
                    currentDate = currentDate.plusDays(1);
                }
                break;
            case "MOON":
                for (int day = 0; day < daysCount; day++) {
                    moonAlgorithm(currentDate);
                    currentDate = currentDate.plusDays(1);
                }
                break;
            case "LINEREG":
                for (int day = 0; day < daysCount; day++) {
                    lineRegAlgorithm(currentDate);
                    currentDate = currentDate.plusDays(1);
                }
                break;
        }
    }

    private void datePrediction() {
        LocalDate requestDate = LocalDate.parse(predictionDate, formatter);
        switch (predictionAlg) {
            case "MIST":
                mistAlgorithm(requestDate);
                break;
            case "MOON":
                moonAlgorithm(requestDate);
                break;
            case "LINEREG":
                lineRegAlgorithm(requestDate);
                break;

        }
    }

    private void lineRegAlgorithm(LocalDate requestDate) {
        String fileName = "/" + currency + ".csv";
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        try (InputStreamReader inputStr = new InputStreamReader(getClass().getResourceAsStream(fileName));
             BufferedReader reader = new BufferedReader(inputStr)) {
            String check = reader.readLine();
            for (int day = 0; day < 31; day++) {
                String[] line = reader.readLine().split(";");
                long parsedDate = LocalDate.parse(line[1], formatter).toEpochDay();
                BigDecimal parsedCourse = new BigDecimal(line[2]);
                BigDecimal nominal = new BigDecimal(line[0]);
                parsedCourse = parsedCourse.divide(nominal).setScale(2, RoundingMode.HALF_UP);
                x.add((double) parsedDate);
                y.add(parsedCourse.doubleValue());
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
            e.printStackTrace();
        }
        Double[] xArray = x.toArray(new Double[0]);
        Double[] yArray = y.toArray(new Double[0]);
        LinearRegression lineReg = new LinearRegression(xArray, yArray);
        BigDecimal lineRegPredict = BigDecimal.valueOf(lineReg.predict(requestDate.toEpochDay()))
                .setScale(2, RoundingMode.HALF_UP);
        String formattedString = getStringDate(requestDate);
        if(outputType.equals("GRAPH")) {
            initiateGraphArgs(requestDate, lineRegPredict);
        }
        if(lineRegPredict.doubleValue() <= 0) {
            result.add("Невозможно предсказать курс на - " + formattedString);
        }
        else result.add(formattedString + " - " + lineRegPredict);
    }

    private void moonAlgorithm(LocalDate requestDate) {
        if(outputType.equals("GRAPH")) {
            String fileName = "/" + currency + ".csv";
        }
        String fileName = "/" + currency + ".csv";
        LocalDate resultDate = requestDate.minusYears(1);
        try (InputStreamReader inputStr = new InputStreamReader(getClass().getResourceAsStream(fileName));
             BufferedReader reader = new BufferedReader(inputStr)) {
            String check = reader.readLine();
            boolean courseFound = false;
            while (!courseFound && reader.ready()) {
                String[] line = reader.readLine().split(";");
                LocalDate parsedDate = LocalDate.parse(line[1], formatter);
                BigDecimal parsedCourse = new BigDecimal(line[2]);
                BigDecimal nominal = new BigDecimal(line[0]);
                parsedCourse = parsedCourse.divide(nominal).setScale(2, RoundingMode.HALF_UP);
                if (parsedDate.equals(resultDate)) {
                    if(outputType.equals("GRAPH")) {
                        initiateGraphArgs(requestDate, parsedCourse);
                    }
                    result.add(getStringDate(currentDate) + " - " + parsedCourse);
                    courseFound = true;
                } else if (ChronoUnit.DAYS.between(resultDate, parsedDate) < 0) {
                    if(outputType.equals("GRAPH")) {
                        initiateGraphArgs(requestDate, parsedCourse);
                    }
                    result.add(getStringDate(currentDate) + " - " + parsedCourse);
                    courseFound = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка чтения файла");
            e.printStackTrace();
        }
    }

    private void initiateGraphArgs(LocalDate requestDate, BigDecimal parsedCourse) {
        courseList.add(parsedCourse.doubleValue());
        currencyList.add(currency);
        dateList.add(requestDate);
    }

    private void mistAlgorithm(LocalDate requestDate) {
        long dataBaseYears = 17;
        int yearToMinusMin = 1;
        long yearTomMinusMax = dataBaseYears - ChronoUnit.YEARS.between(requestDate, currentDate);
        int mistNumber = yearToMinusMin + (int) (Math.random() * yearTomMinusMax);
        LocalDate resultDate = requestDate.minusYears(mistNumber - 1);
        moonAlgorithm(resultDate);
    }

    private String getStringDate(LocalDate resultDate) {
        String dayOfWeek = resultDate
                .getDayOfWeek()
                .getDisplayName(TextStyle.SHORT, new Locale("ru"));
        dayOfWeek = dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1);
        return dayOfWeek + " " + resultDate.format(formatter);
    }
}
