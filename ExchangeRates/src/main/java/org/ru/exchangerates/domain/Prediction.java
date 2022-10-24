package org.ru.exchangerates.domain;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public interface Prediction {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    void predict();
}
