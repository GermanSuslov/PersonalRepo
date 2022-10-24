package org.ru.exchangerates.telegrambot;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class StartBot {
    private static Logger logger = Logger.getLogger(StartBot.class);

    public static void main(String[] args) {
        TelegramBotsApi botsApi = null;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            Bot telegramBot = new Bot();
            botsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            logger.error("Не удалось инициировать API");
        }
    }
}
