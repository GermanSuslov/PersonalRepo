package org.ru.exchangerates.domain;

public interface Validator {
    boolean validated();

    PredictionRequest getPredictionRequest();
}
