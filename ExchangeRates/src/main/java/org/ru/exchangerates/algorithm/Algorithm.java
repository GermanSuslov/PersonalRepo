package org.ru.exchangerates.algorithm;

import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public interface Algorithm {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    void initiateResultList(LocalDate requestDate);
    ArrayList<Double> getCourseList();
    ArrayList<LocalDate> getDateList();
    ArrayList<String> getCurrencyList();
    ArrayList<String> getResult();
}
