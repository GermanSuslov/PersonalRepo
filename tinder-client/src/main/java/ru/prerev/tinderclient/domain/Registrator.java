package ru.prerev.tinderclient.domain;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.telegrambot.Bot;

@RequiredArgsConstructor
public class Registrator {
    private final Bot bot;
    private User newUser;

    public void registration(Long chatId, Update update) throws TelegramApiException {
        SendMessage sexMessage = new SendMessage(chatId.toString(), "Вы сударь иль сударыня?");
        bot.execute(sexMessage);
        update.getMessage();
        newUser.setSex(update.getMessage().getText());
        SendMessage nameMessage = new SendMessage(chatId.toString(), "Как вас зовут?");
        bot.execute(nameMessage);
    }
}
