package org.TelegramBot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Запуск бота
 */
public class StartBot {

    public static void main(String[] args) {
        TelegramBotsApi botsApi = null;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            Bot telegramBot = new Bot();
            botsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            System.out.println("Не удалось инициировать API");
        }
    }
}
