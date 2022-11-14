package ru.prerev.tinderclient.telegrambot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.domain.Authorizer;
import ru.prerev.tinderclient.domain.Menu;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
@Component
public final class Bot extends TelegramLongPollingBot {
    private final TelegramBotsApi botsApi;
    private final Authorizer authorizer;
    private final Menu menu;

    @Value("${botName}")
    private String botName;
    @Value("${botToken}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = null;
        String message = null;
        if (update.hasMessage() && update.getMessage().hasText()) {
            chatId = update.getMessage().getChatId();
            message = update.getMessage().getText();
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            message = update.getCallbackQuery().getData();
        }
        authorizer.authorize(chatId, message);
        menu.showMenu(chatId, message);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    private void setBot() {
        authorizer.setBot(this);
        menu.setBot(this);
    }

    @PostConstruct
    public void init() {
        try {
            botsApi.registerBot(this);
            setBot();
        } catch (TelegramApiException e) {
            log.error("Не удалось зарегестрировать бота: ");
        }
    }
}