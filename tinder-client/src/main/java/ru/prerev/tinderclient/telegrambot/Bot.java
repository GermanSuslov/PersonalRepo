package ru.prerev.tinderclient.telegrambot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.service.TelegramService;
import ru.prerev.tinderclient.service.UserService;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
@Component
public final class Bot extends TelegramLongPollingBot {
    private final TelegramBotsApi botsApi;
    private final TelegramService telegramService;

    @Value("${botName}")
    private String botName;
    @Value("${botToken}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            messageProcess(update);
        } else if (update.hasCallbackQuery()) {
            callbackQueryProcess(update);
        }
    }

    private void messageProcess(Update update) {
        Long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();
        telegramService.authorize(chatId, message.trim());
        telegramService.showMenu(chatId, message);
    }

    private void callbackQueryProcess(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String message = update.getCallbackQuery().getData();
        telegramService.authorize(chatId, message.trim());
        telegramService.showMenu(chatId, message);
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
        telegramService.setBot(this);
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