package org.ru.exchangerates.telegrambot;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Map;


public class StartBot {
    private static final Logger logger = Logger.getLogger(StartBot.class);
    private static final Map<String, String> getenv = System.getenv();

    public static void main(String[] args) {
        TelegramBotsApi botsApi = null;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            Bot telegramBot = new Bot(getenv.get("BOT_NAME"), getenv.get("BOT_TOKEN"));
            botsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            logger.error("Не удалось инициировать API");
        }
    }
}
