package org.ExchangeTest;

import org.junit.Assert;
import org.junit.Test;
import org.ru.exchangerates.domain.PredictionRequest;
import org.ru.exchangerates.domain.PredictionResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ExchangeRatesTest {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private ArrayList<String> testCurrencies = new ArrayList<>();

    @Test
    public void smokeTest() {
        testCurrencies.add("USD");
        PredictionRequest predictionRequest = new PredictionRequest(
                testCurrencies, "-PERIOD", "MONTH", "MOON", "LIST");
        PredictionResult predictionResult = predictionRequest.predict();
        Assert.assertEquals(30, predictionResult.getResult().size());
    }

    @Test
    public void tomorrowMoonPredictionTest() {
        testCurrencies.add("TRY");
        PredictionRequest predictionRequest = new PredictionRequest(
                testCurrencies, "-DATE", "TOMORROW", "MOON");
        PredictionResult predictionResult = predictionRequest.predict();
        String tomorrowDate = predictionResult.getResult().get(0).split(" ")[1];
        String expectedDate = LocalDate.now().plusDays(1).format(formatter);
        Assert.assertTrue(tomorrowDate.equals(expectedDate));
    }

    @Test
    public void monthLineregPredictionTest() {
        testCurrencies.add("BGN");
        PredictionRequest predictionRequest = new PredictionRequest(
                testCurrencies, "-PERIOD", "MONTH", "LINEREG", "LIST");
        PredictionResult predictionResult = predictionRequest.predict();
        String weekDate = predictionResult.getResult().get(29).split(" ")[1];
        String expectedDate = LocalDate.now().plusDays(30).format(formatter);
        Assert.assertEquals(expectedDate, weekDate);
    }

    @Test
    public void weekMistPredictionTest() {
        testCurrencies.add("EURO");
        PredictionRequest predictionRequest = new PredictionRequest(
                testCurrencies, "-PERIOD", "WEEK", "MIST", "LIST");
        PredictionResult predictionResult = predictionRequest.predict();
        String weekDate = predictionResult.getResult().get(6).split(" ")[1];
        String expectedDate = LocalDate.now().plusDays(7).format(formatter);
        Assert.assertEquals(expectedDate, weekDate);
    }
}