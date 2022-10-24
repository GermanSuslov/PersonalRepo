package org.ru.exchangerates.domain;

import java.util.ArrayList;

public interface Validator {
    boolean validated();
    PredictionRequest getPredictionRequest();
}
