package org.ru.exchangerates.domain;

import org.ru.exchangerates.courseplot.GraphPrediction;

import java.util.ArrayList;

public class ConsoleResultPrinter {
    private final PredictionRequest predictionRequest;
    private final PredictionResult predictionResult;

    public ConsoleResultPrinter(PredictionRequest predictionRequest, PredictionResult predictionResult) {
        this.predictionRequest = predictionRequest;
        this.predictionResult = predictionResult;
    }

    public void printResult() {
        if (predictionRequest.getOutputType().equals("LIST")) {
            ArrayList<String> predictionResultList = predictionResult.predictionToList();
            for (String oneDayPrediction : predictionResultList) {
                System.out.println(oneDayPrediction);
            }
        } else {
            GraphPrediction prediction = new GraphPrediction();
            prediction.createGraphFile(predictionResult);
        }
    }
}
