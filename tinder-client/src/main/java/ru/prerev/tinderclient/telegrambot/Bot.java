package ru.prerev.tinderclient.telegrambot;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.domain.Authorizer;
import ru.prerev.tinderclient.domain.Menu;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
public final class Bot extends TelegramLongPollingBot {
    //private final BotProperty property;
    private final TelegramBotsApi botsApi;
    private final Authorizer authorizer;
    private final Menu menu;
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
        Long chatId = null;
        String message_text = null;
        if (update.hasMessage() && update.getMessage().hasText()) {
            chatId = update.getMessage().getChatId();
            message_text = update.getMessage().getText();
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            message_text = update.getCallbackQuery().getData();
        }
        setBot();
        authorizer.authorize(chatId, message_text);
        menu.showMenu(chatId, message_text);
    }

    private void setBot() {
        authorizer.setBot(this);
        menu.setBot(this);
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