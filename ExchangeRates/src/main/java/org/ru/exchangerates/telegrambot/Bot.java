package org.ru.exchangerates.telegrambot;

import org.ru.exchangerates.courseplot.GraphPrediction;
import org.ru.exchangerates.domain.PredictionRequest;
import org.ru.exchangerates.domain.PredictionResult;
import org.ru.exchangerates.domain.RequestValidator;
import org.ru.exchangerates.domain.ValidatorCreator;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;


public final class Bot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "ExchangeRates_TelegramBot";
    }

    @Override
    public String getBotToken() {
        return "5786769685:AAELRq6IpYfWXCh4mIFntNep2w138bjj1TY";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            PredictionSender sender = new PredictionSender(message_text, chatId, this);
            sender.sendResult();
        }
    }

}
