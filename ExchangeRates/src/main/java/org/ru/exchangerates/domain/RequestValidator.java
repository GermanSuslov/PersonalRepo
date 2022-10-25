package org.ru.exchangerates.domain;

import org.apache.log4j.Logger;
import org.ru.exchangerates.repository.Currencies;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Проверяет валидность входных аргументов из ридера
 */
public class RequestValidator implements Validator {
    private final Logger logger = Logger.getLogger(RequestValidator.class);
    private final String input;
    private final ValidatorCreator validatorCreator;
    private String predictionAlg;
    private final ArrayList<String> currenciesList = new ArrayList<>();
    private String predictionType;
    private String predictionDate;
    private String outputType;

    public RequestValidator(String input, ValidatorCreator validatorCreator) {
        this.input = input;
        this.validatorCreator = validatorCreator;
    }

    /**
     * Парсит входную строку
     */
    public boolean validated() {
        String[] requestArgs = input
                .split(" ");
        for (int i = 0; i < requestArgs.length; i++) {
            requestArgs[i] = requestArgs[i].toUpperCase();
        }
        if (!argsIsValid(requestArgs)) {
            if (!validatorCreator.isTelegramValidator()) {
                System.out.println("Ошибка! " + validatorCreator.instruction);
            }
            logger.info("Неправильный запрос");
            return false;
        }
        if (requestArgs[1].contains(",")) {
            String[] graphLine = requestArgs[1].split(",");
            currenciesList.addAll(Arrays.asList(graphLine));
        } else currenciesList.add(requestArgs[1]);
        predictionType = requestArgs[2];
        predictionDate = requestArgs[3];
        predictionAlg = requestArgs[5];
        if (predictionType.equals("-PERIOD")) {
            outputType = requestArgs[7];
        }
        return true;
    }

    public boolean argsIsValid(String[] requestArgs) {

        if (!requestArgs[0].equals("RATE")) {
            return false;
        }
        if (requestArgs[1].split(",").length == 1) {
            if (!containsCurrency(requestArgs[1])) {
                return false;
            }
        } else {
            for (String cur : requestArgs[1].split(",")) {
                if (!containsCurrency(cur)) {
                    return false;
                }
            }
        }
        if (predictionTypeInvalid(requestArgs)) {
            return false;
        }
        if (requestArgs[4].equals("-ALG")) {
            switch (requestArgs[5]) {
                case "MIST":
                case "LINEREG":
                case "MOON":
                    break;
                default:
                    return false;
            }
        } else return false;
        return true;
    }

    private boolean containsCurrency(String requestArg) {
        boolean containsCurrency = false;
        for (Currencies currency : Currencies.values()) {
            if (requestArg.equals(currency.toString())) {
                containsCurrency = true;
            }
        }
        return containsCurrency;
    }

    private boolean predictionTypeInvalid(String[] requestArgs) {
        if (requestArgs.length < 6) {
            return true;
        }
        switch (requestArgs[2]) {
            case "-DATE":
                if (requestArgs.length != 6) {
                    return true;
                }
                if (!requestArgs[3].matches("^\\d{2}.\\d{2}.\\d{4}$")) {
                    if (!requestArgs[3].equals("TOMORROW")) {
                        return true;
                    }
                }
                break;
            case "-PERIOD":
                if (requestArgs.length != 8) {
                    return true;
                }
                if (!requestArgs[3].equals("WEEK")) {
                    if (!requestArgs[3].equals("MONTH")) {
                        return true;
                    }
                }
                if (!requestArgs[6].equals("-OUTPUT")) {
                    return true;
                }
                if (!requestArgs[7].equals("GRAPH") && !requestArgs[7].equals("LIST")) {
                    return true;
                }
                break;
            default:
                return true;
        }
        return false;
    }

    public PredictionRequest getPredictionRequest() {
        PredictionRequest predictionRequest = null;
        if (predictionType.equals("-DATE")) {
            predictionRequest = new PredictionRequest(
                    currenciesList, predictionType, predictionDate, predictionAlg);

        } else {
            predictionRequest = new PredictionRequest(
                    currenciesList, predictionType, predictionDate, predictionAlg, outputType);
        }
        return predictionRequest;
    }
}
