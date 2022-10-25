package org.ru.exchangerates.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.ru.exchangerates.domain.Prediction.formatter;

@Getter
public class PredictionRequest {
    private final String predictionType;
    private String predictionDate;
    private final String predictionAlg;
    private final String outputType;
    private final LocalDate currentDate;
    private final ArrayList<String> currencyInputList;

    /**
     * Создает запрос на прогноз
     *
     * @param currencyInputList вид валюты
     * @param predictionType    тип прогноза
     */
    public PredictionRequest(ArrayList<String> currencyInputList, String predictionType, String predictionDate, String predictionAlg, String outputType) {
        this.currencyInputList = currencyInputList;
        this.predictionType = predictionType;
        this.predictionDate = predictionDate;
        this.predictionAlg = predictionAlg;
        this.outputType = outputType;
        this.currentDate = LocalDate.now();
    }
    public PredictionRequest(ArrayList<String> currencyInputList, String predictionType, String predictionDate, String predictionAlg) {
        this.currencyInputList = currencyInputList;
        this.predictionType = predictionType;
        this.predictionDate = predictionDate;
        this.predictionAlg = predictionAlg;
        this.outputType = "LIST";
        this.currentDate = LocalDate.now();
    }
    public PredictionResult predict() {
        PredictionResult predictionResult = new PredictionResult();
        switch (this.getPredictionType()) {
            case "-DATE":
                if (this.predictionDate.matches("^\\d{2}.\\d{2}.\\d{4}$")) {
                    DatePrediction datePrediction = new DatePrediction(this, predictionResult);
                    datePrediction.predict();
                } else {
                    LocalDate tomorrowDate = LocalDate.now().plusDays(1);
                    predictionDate = tomorrowDate.format(formatter);
                    DatePrediction datePrediction = new DatePrediction(this, predictionResult);
                    datePrediction.predict();
                }
            case "-PERIOD":
                if (this.predictionDate.equals("WEEK")) {
                    PeriodPrediction periodPrediction = new PeriodPrediction(this, predictionResult, 7);
                    periodPrediction.predict();
                }
                if (this.predictionDate.equals("MONTH")) {
                    PeriodPrediction periodPrediction = new PeriodPrediction(this, predictionResult, 30);
                    periodPrediction.predict();
                }
        }
        return predictionResult;
    }
}
