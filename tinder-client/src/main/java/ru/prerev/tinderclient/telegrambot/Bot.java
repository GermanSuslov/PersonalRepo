package ru.prerev.tinderclient.telegrambot;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.config.BotProperty;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
public final class Bot extends TelegramLongPollingBot {
    private final BotProperty property;
    private final TelegramBotsApi botsApi;

    @Override
    public String getBotUsername() {
        return property.getBotName();
    }

    @Override
    public String getBotToken() {
        return property.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            SendMessage message = new SendMessage(chatId.toString(), message_text);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @PostConstruct
    public void init() {
        try {
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
