package org.ru.exchangerates.courseplot;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
@Getter
public class GraphArgs {
    private String currency = "";
    private final ArrayList<LocalDate> dateList = new ArrayList<>();
    private final ArrayList<BigDecimal> courseList = new ArrayList<>();

    public GraphArgs(String currency) {
        this.currency = currency;
    }

    public void addDateAndCourse(LocalDate date, BigDecimal course) {
        this.dateList.add(date);
        this.courseList.add(course);
    }
}
