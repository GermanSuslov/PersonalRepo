package org.ru.exchangerates.domain;

import org.ru.exchangerates.algorithm.Algorithm;
import org.ru.exchangerates.algorithm.LineRegAlgorithm;
import org.ru.exchangerates.algorithm.MistAlgorithm;
import org.ru.exchangerates.algorithm.MoonAlgorithm;

import java.time.LocalDate;

public class PeriodPrediction implements Prediction {
    private final PredictionRequest predictionRequest;
    private final PredictionResult predictionResult;
    private final int daysCount;

    public PeriodPrediction(PredictionRequest predictionRequest, PredictionResult predictionResult, int daysCount) {
        this.predictionRequest = predictionRequest;
        this.predictionResult = predictionResult;
        this.daysCount = daysCount;
    }

    public void predict() {
        LocalDate currentDate = predictionRequest.getCurrentDate().plusDays(1);
        switch (predictionRequest.getPredictionAlg()) {
            case "MIST":
                MoonAlgorithm moonForMist = new MoonAlgorithm(predictionRequest);
                MistAlgorithm mistAlgorithm = new MistAlgorithm(moonForMist);
                for (int day = 0; day < daysCount; day++) {
                    mistAlgorithm.initiateResultList(currentDate);
                    currentDate = currentDate.plusDays(1);
                }
                setAlgorithmResult(moonForMist);
                break;
            case "MOON":
                MoonAlgorithm moonAlgorithm = new MoonAlgorithm(predictionRequest);
                for (int day = 0; day < daysCount; day++) {
                    moonAlgorithm.initiateResultList(currentDate);
                    currentDate = currentDate.plusDays(1);
                }
                setAlgorithmResult(moonAlgorithm);
                break;
            case "LINEREG":
                LineRegAlgorithm lineRegAlgorithm = new LineRegAlgorithm(predictionRequest);
                for (int day = 0; day < daysCount; day++) {
                    lineRegAlgorithm.initiateResultList(currentDate);
                    currentDate = currentDate.plusDays(1);
                }
                setAlgorithmResult(lineRegAlgorithm);
                break;
        }
    }

    private void setAlgorithmResult(MoonAlgorithm algorithm) {
        predictionResult.setResult(algorithm.getResult());
        predictionResult.setGraphResult(algorithm.getGraphArgsList());
    }
}
