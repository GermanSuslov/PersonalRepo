package org.ru.exchangerates.algorithm;

import lombok.Getter;
import org.ru.exchangerates.domain.PredictionRequest;
import org.ru.exchangerates.repository.ResourceReader;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LineRegAlgorithm extends MoonAlgorithm {
    @Getter
    private final ArrayList<String> result = new ArrayList<>();
    private final PredictionRequest predictionRequest;

    public LineRegAlgorithm(PredictionRequest predictionRequest) {
        super(predictionRequest);
        this.predictionRequest = predictionRequest;
    }

    @Override
    public void initiateResultList(LocalDate requestDate) {
        for (int i = 0; i < predictionRequest.getCurrencyInputList().size(); i++) {
            String fileName = "/" + predictionRequest.getCurrencyInputList().get(i) + ".csv";
            List<Double> x = new ArrayList<>();
            List<Double> y = new ArrayList<>();
            ResourceReader reader = new ResourceReader(fileName);
            for (int day = 0; day < 31; day++) {
                reader.read();
                long parsedDate = reader.getParsedDate().toEpochDay();
                BigDecimal parsedCourse = reader.getParsedCourse();
                x.add((double) parsedDate);
                y.add(parsedCourse.doubleValue());
            }
            Double[] xArray = x.toArray(new Double[0]);
            Double[] yArray = y.toArray(new Double[0]);
            LinearRegression lineReg = new LinearRegression(xArray, yArray);
            BigDecimal lineRegPredict = BigDecimal.valueOf(lineReg.predict(requestDate.toEpochDay()));
            lineRegPredict = lineRegPredict.setScale(2, RoundingMode.HALF_UP);
            String formattedString = getStringDate(requestDate);
            if (predictionRequest.getOutputType().equals("GRAPH")) {
                initiateGraphArgs(predictionRequest.getCurrencyInputList().get(i),
                        requestDate,
                        lineRegPredict);
            }
            if (lineRegPredict.doubleValue() <= 0) {
                result.add("Невозможно предсказать курс на - " + formattedString);
            } else result.add(formattedString + " - " + lineRegPredict);
        }
    }
}

