package org.ru.exchangerates.courseplot;

import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.ru.exchangerates.domain.PredictionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

@Slf4j
public class GraphPrediction {
    Logger logger = LoggerFactory.getLogger(GraphPrediction.class);
    public void createGraph(PredictionResult predictionResult) {
        JFreeChart chart = getjFreeChart(predictionResult);
        long id = LocalTime.now().toSecondOfDay();
        try {
            ChartUtils.saveChartAsPNG(new File("src/main/resources/CVDPlot" + id + ".png"), chart, 1600, 1200);
        } catch (IOException e) {
            logger.debug("Не удалось сохранить изображение");
        }
    }

    public File getGraphFile(PredictionResult predictionResult) throws IOException {
        JFreeChart chart = getjFreeChart(predictionResult);
        long id = LocalTime.now().toSecondOfDay();
        File file;
        ChartUtils.saveChartAsPNG(file = new File("src/main/resources/CVDPlot" + id + ".png"), chart, 1600, 1200);
        return file;
    }

    private static JFreeChart getjFreeChart(PredictionResult predictionResult) {
        JFreeChart chart = CurrencyVsDatePlot.createChart(
                CurrencyVsDatePlot.createDataset(
                        predictionResult.getGraphArgsList()));
        return chart;
    }
}
