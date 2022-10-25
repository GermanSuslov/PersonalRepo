package org.ru.exchangerates.domain;

import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.ru.exchangerates.courseplot.GraphPrediction;

import java.util.ArrayList;

@Log4j
public class StartConsoleApp {
    private static final Logger logger = Logger.getLogger(StartConsoleApp.class);

    public static void main(String[] args) {
        RequestValidator validator = new ValidatorCreator().createConsoleValidator();
        PredictionRequest predictionRequest = validator.getPredictionRequest();
        PredictionResult predictionResult = predictionRequest.predict();
        logger.info("Удалось предсказать");
        ConsoleResultPrinter resultPrinter = new ConsoleResultPrinter(predictionRequest, predictionResult);
        resultPrinter.printResult();
    }
}