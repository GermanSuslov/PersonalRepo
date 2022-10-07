package org.Exchange;

import java.util.ArrayList;
import java.util.Scanner;

public class ExchangeRates {
    public static void main(String[] args) {
        System.out.println("Введите запрос по типу : rate EURO/TRY/USD/AMD/BGN tomorrow/week");
        Scanner input = new Scanner(System.in);
        boolean checked = false;
        String currency = "";
        String predictionType = "";
        while (!checked) {
            String[] requestArgs = input
                    .nextLine()
                    .split(" ");
            for (int i = 0; i < requestArgs.length; i++) {
                requestArgs[i] = requestArgs[i].toUpperCase();
            }
            if (!isValid(requestArgs)) {
                System.out.println("Ошибка! Введите запрос по типу : rate EURO/TRY/USD tomorrow/week");
                continue;
            }
            currency = requestArgs[1];
            predictionType = requestArgs[2];
            checked = true;
        }
        input.close();

        Prediction prediction = new Prediction(currency, predictionType);
        ArrayList<String> predictionResult = prediction.getPrediction();
        if (predictionResult.size() == 0) {
            System.out.println("Ошибка чтения данных");
        } else for (String oneDayPrediction : predictionResult) {
            System.out.println(oneDayPrediction);
        }
    }

    public static boolean isValid(String[] requestArgs) {
        if (!requestArgs[0].equals("RATE")) {
            return false;
        }
        if (!requestArgs[1].equals("TRY")
                && !requestArgs[1].equals("USD")
                && !requestArgs[1].equals("EURO")
                && !requestArgs[1].equals("AMD")
                && !requestArgs[1].equals("BGN")) {
            return false;
        }
        if (!requestArgs[2].equals("WEEK") && !requestArgs[2].equals("TOMORROW")) {
            return false;
        }
        if (requestArgs.length != 3) {
            return false;
        }
        return true;
    }
}