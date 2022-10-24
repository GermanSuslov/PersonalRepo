package org.ru.exchangerates.domain;

import org.ru.exchangerates.algorithm.LineRegAlgorithm;
import org.ru.exchangerates.algorithm.MistAlgorithm;
import org.ru.exchangerates.algorithm.MoonAlgorithm;

import java.time.LocalDate;

public class DatePrediction implements Prediction {
    private PredictionRequest predictionRequest;
    private PredictionResult predictionResult;

    public DatePrediction(PredictionRequest predictionRequest, PredictionResult predictionResult) {
        this.predictionRequest = predictionRequest;
        this.predictionResult = predictionResult;
    }

    public void predict() {
        LocalDate requestDate = LocalDate.parse(predictionRequest.getPredictionDate(), formatter);
        switch (predictionRequest.getPredictionAlg()) {
            case "MIST":
                MoonAlgorithm moonForMist = new MoonAlgorithm(predictionRequest);
                MistAlgorithm mistAlgorithm = new MistAlgorithm(moonForMist);
                mistAlgorithm.initiateResultList(requestDate);
                predictionResult.setResult(moonForMist.getResult());
                break;
            case "MOON":
                MoonAlgorithm moonAlgorithm = new MoonAlgorithm(predictionRequest);
                moonAlgorithm.initiateResultList(requestDate);
                predictionResult.setResult(moonAlgorithm.getResult());
                break;
            case "LINEREG":
                LineRegAlgorithm lineRegAlgorithm = new LineRegAlgorithm(predictionRequest);
                lineRegAlgorithm.initiateResultList(requestDate);
                predictionResult.setResult(lineRegAlgorithm.getResult());
                break;

        }
    }
}
