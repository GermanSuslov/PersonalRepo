package ru.prerev.tinderclient.telegrambot;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.domain.Authorizer;

import javax.annotation.PostConstruct;
import java.io.IOException;

@RequiredArgsConstructor
public final class Bot extends TelegramLongPollingBot {
    //private final BotProperty property;
    private final TelegramBotsApi botsApi;
    private final Authorizer authorizer;
    @Value("${botName}")
    private String botName;
    @Value("${botToken}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String message_text = update.getMessage().getText();
            authorizeUser(chatId, message_text);
        } else if (update.hasCallbackQuery()) {
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            String message_text = update.getCallbackQuery().getData();
            authorizeUser(chatId, message_text);
        }
    }

    private void authorizeUser(Long chatId, String message_text) {
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