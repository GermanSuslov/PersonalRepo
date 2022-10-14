package org.Plot;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.api.HorizontalAlignment;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;


public class CurrencyVsDatePlot {

    public static JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "International Coffee Organisation : Coffee Prices",
                null, "US cents/lb", dataset);

        String fontName = "Palatino";
        chart.getTitle().setFont(new Font(fontName, Font.BOLD, 18));
        chart.addSubtitle(new TextTitle(
                "Source: http://www.ico.org/historical/2010-19/PDF/HIST-PRICES.pdf",
                new Font(fontName, Font.PLAIN, 14)));

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
        chart.getLegend().setItemFont(new Font(fontName, Font.PLAIN, 14));
        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.getLegend().setHorizontalAlignment(HorizontalAlignment.CENTER);
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setDefaultShapesVisible(false);
            renderer.setDrawSeriesLineAsPath(true);
            // set the default stroke for all series
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

        return chart;

    }

    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return the dataset.
     */
    public static XYDataset createDataset(ArrayList<Double> course, ArrayList<String> currencies, ArrayList<LocalDate> date) {
        TimeSeries usdTs = new TimeSeries("Американский доллар");
        TimeSeries amdTs = new TimeSeries("Армянский драм");
        TimeSeries bgnTs = new TimeSeries("Болгарский лев");
        TimeSeries euroTs = new TimeSeries("Евро");
        TimeSeries tryTs = new TimeSeries("Турецкая лира");
        for (int i = 0; i < currencies.size(); i++) {
            switch (currencies.get(i)) {
                case "USD":
                    usdTs.add(getRegularTimePeriod(date, i), course.get(i));
                    break;
                case "AMD":
                    amdTs.add(getRegularTimePeriod(date, i), course.get(i));
                    break;
                case "BGN":
                    bgnTs.add(getRegularTimePeriod(date, i), course.get(i));
                    break;
                case "EURO":
                    euroTs.add(getRegularTimePeriod(date, i), course.get(i));
                    break;
                case "TRY":
                    tryTs.add(getRegularTimePeriod(date, i), course.get(i));
                    break;
            }
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        Set<TimeSeries> seriesSet = new HashSet<>();
        seriesSet.add(usdTs);
        seriesSet.add(amdTs);
        seriesSet.add(bgnTs);
        seriesSet.add(euroTs);
        seriesSet.add(tryTs);
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

/*    public CurrencyVsDatePlot(List<Double> course, List<String> currency, List<String> date) {
        super("Currency Vs Dates");
        JFreeChart lineChart = ChartFactory.createLineChart(
                "График валют",
                "Date",
                "Course",
                createDataset(course, currency, date),
                PlotOrientation.HORIZONTAL,
                true, true, false);
        XYPlot plotXY = (XYPlot) lineChart.getPlot();
        ChartPanel chartPanel = new ChartPanel(lineChart);

        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(List<Double> course, List<String> currency, List<String> date) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < course.size(); i++) {
            dataset.addValue(
                    course.get(i),
                    currency.get(i),
                    date.get(i));
        }
        return dataset;
    }*/
}
