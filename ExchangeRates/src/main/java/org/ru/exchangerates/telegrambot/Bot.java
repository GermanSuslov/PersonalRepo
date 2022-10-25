package org.ru.exchangerates.telegrambot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


public final class Bot extends TelegramLongPollingBot {

    private final String bot_name;
    private final String bot_token;

    public Bot(String bot_name, String bot_token) {
        this.bot_name = bot_name;
        this.bot_token = bot_token;
    }

    @Override
    public String getBotUsername() {
        return bot_name;
        //"ExchangeRates_TelegramBot";
    }

    @Override
    public String getBotToken() {
        return bot_token;
        //"5786769685:AAELRq6IpYfWXCh4mIFntNep2w138bjj1TY";
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
