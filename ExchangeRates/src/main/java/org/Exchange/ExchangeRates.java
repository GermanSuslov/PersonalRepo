package org.Exchange;

import org.Plot.CurrencyVsDatePlot;
import org.TelegramBot.TelegramRequestParser;
import org.jfree.chart.JFreeChart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ExchangeRates {
    public static void main(String[] args) {
        RequestParser parser = new RequestParser(new BufferedReader(new InputStreamReader((System.in))));
        Prediction prediction = getPrediction(parser);
        ArrayList<String> predictionResult = prediction.predictionToList();
        if (prediction.getOutputType().equals("LIST")) {
            for (String oneDayPrediction : predictionResult) {
                System.out.println(oneDayPrediction);
            }
        } else {
            getGraph(prediction);
        }
    }

    public static Prediction getPrediction(RequestParser parser) {
        Prediction prediction = null;
        if (!(parser instanceof TelegramRequestParser)) {
            parser.parse();
        }
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
        return prediction;
    }

    public static void getGraph(Prediction prediction) {
        JFreeChart chart = CurrencyVsDatePlot.createChart(
                CurrencyVsDatePlot.createDataset(
                        prediction.getCourseList(),
                        prediction.getCurrencyList(),
                        prediction.getDateList()));

        try {
            draw(chart);
        } catch (IOException e) {
            System.out.println("Не удалось создать изображение");
        }
    }

    public static void draw(JFreeChart chart) throws IOException {
        BufferedImage image = new BufferedImage(1600, 1200,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION, true);
        Rectangle r = new Rectangle(0, 0, 1600, 1200);
        chart.draw(g2, r);
        ImageIO.write(image, "png", new File("src/main/resources/CurrencyVsDatePlot.png"));
    }
}