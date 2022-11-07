package ru.prerev.tinderclient.telegrambot;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.config.BotProperty;
import ru.prerev.tinderclient.domain.Authorizer;

import javax.annotation.PostConstruct;
import java.io.IOException;

@RequiredArgsConstructor
public final class Bot extends TelegramLongPollingBot {
    private final BotProperty property;
    private final TelegramBotsApi botsApi;
    private final Authorizer authorizer;

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
            Long chatId = update.getMessage().getChatId();
            String message_text = update.getMessage().getText();
            authorizeUser(chatId.intValue(), message_text);
        } else if (update.hasCallbackQuery()) {
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            String message_text = update.getCallbackQuery().getData();
            authorizeUser(chatId.intValue(), message_text);
        }
    }

    private void authorizeUser(Integer chatId, String message_text) {
        authorizer.setBot(this);
        try {
            authorizer.authorize(chatId, message_text);
        } catch (IOException e) {
            throw new RuntimeException(e);
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