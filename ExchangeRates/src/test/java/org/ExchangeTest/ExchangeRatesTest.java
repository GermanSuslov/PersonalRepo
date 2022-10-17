package org.ExchangeTest;

import org.Exchange.ExchangeRates;
import org.Exchange.Prediction;
import org.Exchange.RequestParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ExchangeRatesTest {
    private static final double DELTA = 1e-15;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Test
    public void correctCourseDateTest() {
        try {
            System.setOut(new PrintStream("garbage"));
        } catch (FileNotFoundException e) {
        }

        ArrayList<String> predictionResult = getPredictionForTest("Rate usd -date tomorrow -alg moon");
        String[] result = predictionResult.get(predictionResult.size() - 1).split(" ");
        Double expectedUsdTomorrow = 71.24;
        Double actualUsdTomorrow = Double.parseDouble(result[result.length - 1]);
        Assert.assertEquals(expectedUsdTomorrow, actualUsdTomorrow, DELTA);

        ArrayList<String> predictionResult2 = getPredictionForTest("Rate bgn -date 20.11.2021 -alg linereg");
        String[] result2 = predictionResult2.get(predictionResult2.size() - 1).split(" ");
        Double expectedBgnDateCourse = 46.63;
        Double actualBgnDateCourse = Double.parseDouble(result2[result2.length - 1]);
        Assert.assertEquals(expectedBgnDateCourse, actualBgnDateCourse, DELTA);
        String expectedDate = "20.11.2021";
        String actualDate = result2[1];
        Assert.assertEquals(expectedDate, actualDate);
    }

    @Test
    public void correctCoursePeriodTest() {
        try {
            System.setOut(new PrintStream("garbage"));
        } catch (FileNotFoundException e) {
        }

        int testedLineMonth = 3;
        ArrayList<String> predictionMonthResult = getPredictionForTest("Rate amd -period month -ALG moon -output list");
        Assert.assertEquals(30, predictionMonthResult.size());
        String[] prediction = predictionMonthResult.get(testedLineMonth).split(" ");
        Double expectedAmdPeriodCourse = 0.15;
        Double actualAmdPeriodCourse = Double.parseDouble(prediction[prediction.length - 1]);
        Assert.assertEquals(expectedAmdPeriodCourse, actualAmdPeriodCourse, DELTA);
        String expectedDate = LocalDate.now().plusDays(testedLineMonth + 1).format(formatter);
        String actualDate = prediction[1];
        Assert.assertEquals(expectedDate, actualDate);

        int testedLineWeek = 2;
        ArrayList<String> predictionWeekResult = getPredictionForTest("Rate EURO -period week -alg mist -output list");
        Assert.assertEquals(7, predictionWeekResult.size());
        String[] predictionWeek = predictionWeekResult.get(testedLineWeek).split(" ");
        String expectedWeekDate = LocalDate.now().plusDays(testedLineWeek + 1).format(formatter);
        String actualWeekDate = predictionWeek[1];
        Assert.assertEquals(expectedWeekDate, actualWeekDate);

    }

    private static ArrayList<String> getPredictionForTest(String request) {
        RequestParser parser = new RequestParser(new BufferedReader(new StringReader(request)));
        Prediction prediction = ExchangeRates.getPrediction(parser);
        ArrayList<String> predictionResult = prediction.predictionToList();
        return predictionResult;
    }


}
