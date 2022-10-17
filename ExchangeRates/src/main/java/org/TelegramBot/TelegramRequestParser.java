package org.TelegramBot;

import org.Exchange.RequestParser;

import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Проверяет валидность входных аргументов из телеграм чата
 */
public class TelegramRequestParser extends RequestParser {
    private BufferedReader input;
    private String predictionAlg;
    private String instruction = "Введите запрос по типу : \n" +
            "rate EURO/TRY/USD/AMD/BGN -date tomorrow/dd.MM.yyyy -alg mist/moon/linereg \n" +
            "или \n" +
            "rate EURO/TRY/USD/AMD/BGN -period week/month -alg mist/moon/linereg -output list/graph";
    private ArrayList<String> currencyInputList = new ArrayList<>();
    private String predictionType;
    private String predictionDate;
    private String outputType = "LIST";

    public TelegramRequestParser(BufferedReader input) {
        super(input);
        this.input = input;
    }

    public String parseWithResult() {
        try {
            String[] requestArgs = input
                    .readLine()
                    .split(" ");
            for (int i = 0; i < requestArgs.length; i++) {
                requestArgs[i] = requestArgs[i].toUpperCase();
            }
            if (!isValid(requestArgs)) {
                return "Ошибка! " + instruction;
            }
            if (requestArgs[1].contains(",")) {
                String[] graphLine = requestArgs[1].split(",");
                for (String cur : graphLine) {
                    currencyInputList.add(cur);
                }
            } else currencyInputList.add(requestArgs[1]);
            initiateArgs(requestArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Successes";
    }

    private void initiateArgs(String[] requestArgs) {
        predictionType = requestArgs[2];
        predictionDate = requestArgs[3];
        predictionAlg = requestArgs[5];
        if (predictionType.equals("-PERIOD")) {
            outputType = requestArgs[7];
        }
    }

    @Override
    public String getPredictionAlg() {
        return predictionAlg;
    }

    @Override
    public ArrayList<String> getCurrencyInputList() {
        return currencyInputList;
    }

    @Override
    public String getPredictionType() {
        return predictionType;
    }

    @Override
    public String getPredictionDate() {
        return predictionDate;
    }

    @Override
    public String getOutputType() {
        return outputType;
    }
}
