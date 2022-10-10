package org.Exchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RequestBuilder {
    private BufferedReader input;

    private String predictionAlg;

    private String currency;
    private String predictionType;
    private String predictionDate;
    private String outputType;
    private final Set<String> currencies = new HashSet<>(Arrays.asList("TRY", "USD", "EURO", "AMD", "BGN"));

    public String getCurrency() {
        return currency;
    }

    public String getPredictionType() {
        return predictionType;
    }

    public String getPredictionDate() {
        return predictionDate;
    }

    public String getPredictionAlg() {
        return predictionAlg;
    }

    public String getOutputType() {
        return outputType;
    }

    public RequestBuilder(BufferedReader input) {
        this.input = input;
    }

    public void build() {
        try {
            boolean checked = false;
            while (!checked) {
                String[] requestArgs = input
                        .readLine()
                        .split(" ");
                for (int i = 0; i < requestArgs.length; i++) {
                    requestArgs[i] = requestArgs[i].toUpperCase();
                }
                if (!isValid(requestArgs)) {
                    System.out.println("Ошибка! Введите запрос по типу : rate EURO/TRY/USD tomorrow/week");
                    continue;
                }
                input.close();
                currency = requestArgs[1];
                predictionType = requestArgs[2];
                predictionDate = requestArgs[3];
                predictionAlg = requestArgs[5];
                if(predictionType.equals("-PERIOD")) {
                    outputType = requestArgs[7];
                }
                checked = true;
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения запроса");
        }

    }

    public boolean isValid(String[] requestArgs) {

        if (!requestArgs[0].equals("RATE")) {
            return false;
        }
        if (!currencies.contains(requestArgs[1])) {
            return false;
        }
        if (predictionTypeInvalid(requestArgs)) {
            return false;
        }
        if (!requestArgs[4].equals("-ALG")) {
            switch (requestArgs[5]) {
                case "MIST":
                case "LINEREG":
                case "MOON":
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    private static boolean predictionTypeInvalid(String[] requestArgs) {
        switch (requestArgs[2]) {
            case "-DATE":
                if (!requestArgs[3].matches("^\\d{2}.\\d{2}.\\d{4}$")) {
                    if (!requestArgs[3].equals("TOMORROW")) {
                        return true;
                    }
                }
                if (requestArgs.length != 6) {
                    return true;
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
}
