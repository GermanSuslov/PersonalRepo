package org.ru.exchangerates.algorithm;

import org.ru.exchangerates.domain.PredictionRequest;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class MistAlgorithm implements Algorithm {
    private final PredictionRequest predictionRequest;
    private final MoonAlgorithm moonAlgorithm;

    public MistAlgorithm(MoonAlgorithm moonAlgorithm) {
        this.predictionRequest = moonAlgorithm.getPredictionRequest();
        this.moonAlgorithm = moonAlgorithm;
    }

    public void initiateResultList(LocalDate requestDate) {
        moonAlgorithm.setInputRequestDate(requestDate);
        long dataBaseYears = 17;
        int yearToMinusMin = 1;
        long yearTomMinusMax = dataBaseYears - ChronoUnit.YEARS.between(requestDate, predictionRequest.getCurrentDate());
        int mistNumber = yearToMinusMin + (int) (Math.random() * yearTomMinusMax);
        LocalDate resultDate = requestDate.minusYears(mistNumber - 1);
        moonAlgorithm.initiateResultList(resultDate);
    }

    @Override
    public ArrayList<Double> getCourseList() {
        return moonAlgorithm.getCourseList();
    }

    @Override
    public ArrayList<LocalDate> getDateList() {
        return moonAlgorithm.getDateList();
    }

    @Override
    public ArrayList<String> getCurrencyList() {
        return moonAlgorithm.getCurrencyList();
    }

    @Override
    public ArrayList<String> getResult() {
        return moonAlgorithm.getResult();
    }
}
