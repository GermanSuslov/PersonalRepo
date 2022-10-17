package org.TelegramBot;

import org.Exchange.ExchangeRates;
import org.Exchange.Prediction;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.util.ArrayList;


public final class Bot extends TelegramLongPollingBot {
    private SendMessage messageSuccess = new SendMessage();
    private SendMessage messageFail = new SendMessage();
    private SendMessage listOutput = new SendMessage();

    @Override
    public String getBotUsername() {
        return "ExchangeRates_TelegramBot";
    }

    @Override
    public String getBotToken() {
        return "5786769685:AAELRq6IpYfWXCh4mIFntNep2w138bjj1TY";
    }

    /**
     * Обаботка входящих сообщений
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            messageSuccess.setChatId(chatId);
            messageFail.setChatId(chatId);
            listOutput.setChatId(chatId);
            BufferedReader reader = new BufferedReader(new StringReader(message_text));
            TelegramRequestParser parser = new TelegramRequestParser(reader);
            String parsingResult = parser.parseWithResult();
            if (parser.getOutputType().equals("LIST")) {
                sendList(parser, parsingResult);
            } else {
                sendGraph(chatId, parser);
            }
        }
    }

    private void sendGraph(Long chatId, TelegramRequestParser parser) {
        Prediction prediction = ExchangeRates.getPrediction(parser);
        prediction.predictionToList();
        ExchangeRates.getGraph(prediction);

        InputFile pic = new InputFile(getClass().getResourceAsStream("/CurrencyVsDatePlot.png"), "Plot.png");
        SendPhoto plot = new SendPhoto(chatId.toString(), pic);
        try {
            execute(plot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendList(TelegramRequestParser parser, String parsingResult) {
        try {
            if (parsingResult.equals("Successes")) {
                Prediction prediction = ExchangeRates.getPrediction(parser);
                ArrayList<String> predictionResult = prediction.predictionToList();
                String resultMessage = "";
                for (String dayPrediction : predictionResult) {
                    resultMessage += dayPrediction + "\n";
                }
                listOutput.setText(resultMessage);
                execute(listOutput);
            } else {
                messageFail.setText(parsingResult);
                execute(messageFail);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
