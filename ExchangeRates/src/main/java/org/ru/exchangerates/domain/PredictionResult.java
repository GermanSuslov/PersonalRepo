package org.ru.exchangerates.domain;

import lombok.Getter;
import lombok.Setter;
import org.ru.exchangerates.courseplot.GraphArgs;

import java.util.ArrayList;

@Getter
public class PredictionResult {
    @Setter
    private ArrayList<String> result;
    private ArrayList<GraphArgs> graphArgsList;

    public void setGraphResult(ArrayList<GraphArgs> graphArgsList) {
        this.graphArgsList = graphArgsList;
    }

    public ArrayList<String> predictionToList() {
        return result;
    }
}
