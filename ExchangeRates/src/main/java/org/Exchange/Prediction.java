package org.Exchange;

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
    private String fileName;
    private ArrayList<String> result = new ArrayList<>();
    private ArrayList<Double> courseList = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Создает запрос на прогноз
     *
     * @param currency       вид валюты
     * @param predictionType тип прогноза
     */
    public Prediction(String currency, String predictionType, String predictionDate, String predictionAlg, String outputType) {
        this.fileName = "/" + currency + ".csv";
        this.predictionType = predictionType;
        this.predictionDate = predictionDate;
        this.predictionAlg = predictionAlg;
        this.outputType = outputType;
        this.currentDate = LocalDate.now();
    }

    public Prediction(String currency, String predictionType, String predictionDate, String predictionAlg) {
        this.fileName = "/" + currency + ".csv";
        this.predictionType = predictionType;
        this.predictionDate = predictionDate;
        this.predictionAlg = predictionAlg;
        this.outputType = "ROW";
        this.currentDate = LocalDate.now();
    }

    public String getPredictionType() {
        return predictionType;
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
        if(lineRegPredict.doubleValue() <= 0) {
            result.add("Невозможно предсказать курс на - " + formattedString);
        }
        else result.add(formattedString + " - " + lineRegPredict);
    }

    private void moonAlgorithm(LocalDate requestDate) {
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
                    result.add(getStringDate(currentDate) + " - " + parsedCourse);
                    courseFound = true;
                } else if (ChronoUnit.DAYS.between(resultDate, parsedDate) < 0) {
                    result.add(getStringDate(currentDate) + " - " + parsedCourse);
                    courseFound = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка чтения файла");
            e.printStackTrace();
        }
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

    /*private void tomorrowPrediction() {
        initiateCourseList();
        int meanDimension = 5;
        String tomorrowDate = getNextDate();
        double sumCourse = 0;
        for (Double course : courseList) {
            sumCourse += course;
        }
        double arithmeticalMean = Math.round(sumCourse / meanDimension * 100) / 100.0;
        if (sumCourse != 0) {
            result.add(tomorrowDate + " - " + arithmeticalMean);
        }
    }

    private void weekPrediction() {
        initiateCourseList();
        int daysInWeek = 7;
        int meanDimension = 5;
        for (int i = 0; i < daysInWeek; i++) {
            double sumCourse = 0;
            for (Double course : courseList) {
                sumCourse += course;
            }
            if (sumCourse == 0) break;
            double arithmeticalMean = Math.round(sumCourse / meanDimension * 100) / 100.0;
            courseList.add(0, arithmeticalMean);
            courseList.remove(courseList.size() - 1);
            String nextDate = getNextDate();
            result.add(nextDate + " - " + arithmeticalMean);
        }
    }

    private void initiateCourseList() {
        try {
            String fileName = "/" + currency + ".csv";
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName)));
            String check = reader.readLine();
            int meanDimension = 5;
            for (int i = 0; i < meanDimension; i++) {
                String[] line = reader.readLine().split(";");
                double course = Double.parseDouble(line[2]) / Integer.parseInt(line[0]);
                courseList.add(course);
            }
        } catch (Exception e) {
            System.out.println("Ошибка чтения файла");
        }
    }*/
/*    private String getNextDate() {
        currentDate = currentDate.plusDays(1);
        String dayOfWeek = currentDate
                .getDayOfWeek()
                .getDisplayName(TextStyle.SHORT, new Locale("ru"));
        dayOfWeek = dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1);
        return dayOfWeek + " " + currentDate.format(formatter);
    }
}*/
