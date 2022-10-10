package org.Exchange;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ExchangeRates {
    public static void main(String[] args) {
        System.out.println("Введите запрос по типу : rate EURO/TRY/USD/AMD/BGN tomorrow/week");
        RequestBuilder builder = new RequestBuilder(new BufferedReader(new InputStreamReader((System.in))));
        Prediction prediction = null;
        builder.build();
        String currency = builder.getCurrency();
        String predictionType = builder.getPredictionType();
        String predictionDate = builder.getPredictionDate();
        String predictionAlg = builder.getPredictionAlg();
        String outputType = builder.getOutputType();
        if(predictionType.equals("-DATE")) {
            prediction = new Prediction(currency, predictionType, predictionDate, predictionAlg);

        }
        else {
            prediction = new Prediction(currency, predictionType, predictionDate, predictionAlg, outputType);
        }

        ArrayList<String> predictionResult = prediction.getPrediction();
        if (predictionResult.size() == 0) {
            System.out.println("Ошибка чтения данных");
        } else for (String oneDayPrediction : predictionResult) {
            System.out.println(oneDayPrediction);
        }
    }

}