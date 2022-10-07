package org.Exchange;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class Prediction {
    private final String currency;
    private final String predictionType;
    private ArrayList<String> result = new ArrayList<>();
    private ArrayList<Double> courseList = new ArrayList<>();
    private LocalDate date;

    /**
     * Создает запрос на прогноз
     *
     * @param currency       вид валюты
     * @param predictionType тип прогноза
     */
    public Prediction(String currency, String predictionType) {
        this.currency = currency;
        this.predictionType = predictionType;
        this.date = LocalDate.now();
    }

    public String getPredictionType() {
        return predictionType;
    }

    /**
     * Возвращает список с результатом прогноза
     */
    public ArrayList<String> getPrediction() {
        if (this.getPredictionType().equals("TOMORROW")) {
            tomorrowPrediction();
        }
        if (this.getPredictionType().equals("WEEK")) {
            weekPrediction();
        }
        return result;
    }

    private void tomorrowPrediction() {
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
            String check  = reader.readLine();
            int meanDimension = 5;
            for (int i = 0; i < meanDimension; i++) {
                String[] line = reader.readLine().split(";");
                double course = Double.parseDouble(line[2]) / Integer.parseInt(line[0]);
                courseList.add(course);
            }
        } catch (Exception e) {
            System.out.println("Ошибка чтения файла");
        }
    }

    private String getNextDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        date = date.plusDays(1);
        String dayOfWeek = date
                .getDayOfWeek()
                .getDisplayName(TextStyle.SHORT, new Locale("ru"));
        dayOfWeek = dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1);
        return dayOfWeek + " " + date.format(formatter);
    }
}
