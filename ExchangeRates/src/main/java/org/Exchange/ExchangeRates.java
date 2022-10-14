package org.Exchange;


import org.Plot.CurrencyVsDatePlot;
import org.jfree.chart.JFreeChart;
import org.jfree.svg.SVGGraphics2D;
import org.jfree.svg.SVGUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ExchangeRates {
    public static void main(String[] args) {
        RequestParser parser = new RequestParser(new BufferedReader(new InputStreamReader((System.in))));
        Prediction prediction = null;
        parser.parse();
        ArrayList<String> currency = parser.getCurrencyInputList();
        String predictionType = parser.getPredictionType();
        String predictionDate = parser.getPredictionDate();
        String predictionAlg = parser.getPredictionAlg();
        String outputType = parser.getOutputType();
        if (predictionType.equals("-DATE")) {
            prediction = new Prediction(currency, predictionType, predictionDate, predictionAlg);

        } else {
            prediction = new Prediction(currency, predictionType, predictionDate, predictionAlg, outputType);
        }
        ArrayList<String> predictionResult = prediction.getPrediction();
        if (prediction.getOutputType().equals("LIST")) {
            for (String oneDayPrediction : predictionResult) {
                System.out.println(oneDayPrediction);
            }
        } else {
            JFreeChart chart = CurrencyVsDatePlot.createChart(
                    CurrencyVsDatePlot.createDataset(
                            prediction.getCourseList(),
                            prediction.getCurrencyList(),
                            prediction.getDateList()));
            SVGGraphics2D g2 = new SVGGraphics2D(600, 400);
            g2.setRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION, true);
            Rectangle r = new Rectangle(0, 0, 600, 400);
            chart.draw(g2, r);
            File f = new File("CurrencyVsDatePlot.svg");
            try {
                SVGUtils.writeToSVG(f, g2.getSVGElement());
            } catch (IOException e) {
                System.out.println("Не удалось сохранить изображение");
            }
        }
    }
}