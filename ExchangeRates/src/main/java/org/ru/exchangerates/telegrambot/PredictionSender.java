package org.ru.exchangerates.telegrambot;

import org.apache.log4j.Logger;
import org.ru.exchangerates.courseplot.GraphPrediction;
import org.ru.exchangerates.domain.PredictionRequest;
import org.ru.exchangerates.domain.PredictionResult;
import org.ru.exchangerates.domain.RequestValidator;
import org.ru.exchangerates.domain.ValidatorCreator;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;

public class PredictionSender {
    private final Logger logger = Logger.getLogger(PredictionSender.class);
    private final String message_text;
    private final Bot bot;
    private final Long chatId;

    public PredictionSender(String message_text, Long chatId, Bot bot) {

        this.message_text = message_text;
        this.bot = bot;
        this.chatId = chatId;
    }

    public void sendResult() {
        ValidatorCreator validatorCreator = new ValidatorCreator();
        RequestValidator validator = validatorCreator.createTelegramValidator(message_text);
        if (validator != null) {
            PredictionRequest predictionRequest = validator.getPredictionRequest();
            PredictionResult predictionResult = predictionRequest.predict();
            ArrayList<String> predictionResultList = predictionResult.predictionToList();
            if (predictionRequest.getOutputType().equals("LIST")) {
                sendList(predictionResultList, chatId);
            } else {
                sendGraph(predictionResult, chatId);
            }
            logger.info("Успешно отправлено");
        } else {
            SendMessage messageFail = new SendMessage();
            messageFail.setChatId(chatId);
            messageFail.setText("Некорректный запрос!\n" + validatorCreator.instruction);
            try {
                bot.execute(messageFail);
            } catch (TelegramApiException e) {
                logger.error("Ошибка отправки сообщения");
            }
        }
    }

    private void sendGraph(PredictionResult predictionResult, Long chatId) {
        GraphPrediction graphPrediction = new GraphPrediction();
        try {
            File pic = graphPrediction.createGraphFile(predictionResult);
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId);
            InputFile pic2 = new InputFile(pic, "CVDPlot.png");
            sendPhoto.setPhoto(pic2);
            bot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            logger.error("Ошибка отправки изображения");
        }
    }

    private void sendList(ArrayList<String> predictionResultList, Long chatId) {
        try {
            String resultMessage = "";
            for (String dayPrediction : predictionResultList) {
                resultMessage += dayPrediction + "\n";
            }
            SendMessage listOutput = new SendMessage();
            listOutput.setChatId(chatId);
            listOutput.setText(resultMessage);
            bot.execute(listOutput);
        } catch (TelegramApiException e) {
            logger.error("Ошибка отправки изображения");
        }
    }
}

