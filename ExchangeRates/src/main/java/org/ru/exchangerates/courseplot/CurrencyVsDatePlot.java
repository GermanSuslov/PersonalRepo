package org.ru.exchangerates.courseplot;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;


public class CurrencyVsDatePlot {

    public static JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "График прогноза курса валют",
                null, "Курс валют, руб", dataset);

        String fontName = "Palatino";
        chart.getTitle().setFont(new Font(fontName, Font.BOLD, 18));

        chartRenderer(chart, fontName);

        return chart;

    }

    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return the dataset.
     */

    public static XYDataset createDataset(ArrayList<GraphArgs> graphArgsList) {
        HashSet<TimeSeries> seriesSet = new HashSet<>();
        for(GraphArgs graphArgs : graphArgsList) {
            TimeSeries ts = new TimeSeries<>(graphArgs.getCurrency());
            for (int i = 0; i < graphArgs.getCourseList().size(); i++) {
                ts.add(getRegularTimePeriod(graphArgs.getDateList(), i), graphArgs.getCourseList().get(i));
            }
            seriesSet.add(ts);
        }

        TimeSeriesCollection dataset = getTimeSeriesCollection(seriesSet);

        return dataset;
    }

    private static TimeSeriesCollection getTimeSeriesCollection(Set<TimeSeries> seriesSet) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for (TimeSeries series : seriesSet) {
            if(!series.isEmpty()) {
                dataset.addSeries(series);
            }
        }
        return dataset;
    }

    private static RegularTimePeriod getRegularTimePeriod(ArrayList<LocalDate> date, int i) {
        int day = date.get(i).getDayOfMonth();
        int month = date.get(i).getMonthValue();
        int year = date.get(i).getYear();
        RegularTimePeriod period = new Day(day, month, year);
        return period;
    }

    private static void chartRenderer(JFreeChart chart, String fontName) {
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(false);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setLabelFont(new Font(fontName, Font.BOLD, 14));
        plot.getDomainAxis().setTickLabelFont(new Font(fontName, Font.PLAIN, 12));
        plot.getRangeAxis().setLabelFont(new Font(fontName, Font.BOLD, 14));
        plot.getRangeAxis().setTickLabelFont(new Font(fontName, Font.PLAIN, 12));
        plot.setRangeGridlinePaint(Color.GRAY);
        plot.setDomainGridlinePaint(Color.GRAY);
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setDefaultShapesVisible(false);
            renderer.setDrawSeriesLineAsPath(true);
            renderer.setAutoPopulateSeriesStroke(false);
            renderer.setDefaultStroke(new BasicStroke(3.0f,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL), false);
            renderer.setSeriesPaint(0, Color.RED);
            renderer.setSeriesPaint(1, new Color(24, 123, 58));
            renderer.setSeriesPaint(2, new Color(149, 201, 136));
            renderer.setSeriesPaint(3, new Color(1, 62, 29));
            renderer.setSeriesPaint(4, new Color(81, 176, 86));
            renderer.setSeriesPaint(5, new Color(0, 55, 122));
            renderer.setSeriesPaint(6, new Color(0, 92, 165));
        }
    }
}
