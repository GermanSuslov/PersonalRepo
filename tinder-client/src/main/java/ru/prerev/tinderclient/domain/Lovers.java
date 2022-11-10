package ru.prerev.tinderclient.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.ReplyKeyboardMaker;

import java.util.ArrayList;
@RequiredArgsConstructor
public class Lovers {
    @Getter
    @Setter
    private ArrayList<ArrayList<User>> userArrayLists;
    @Setter
    private Bot bot;

    private final ReplyKeyboardMaker replyKeyboardMaker;

    public void showLovers(Long id, String message) {
        if (message.equalsIgnoreCase("Вправо")) {

        }
        if (message.equalsIgnoreCase("Влево")) {

        }
        if (message.equalsIgnoreCase("Меню")) {
            userArrayLists.clear();
            menuButtons(id);
        }
    }

    private void menuButtons(Long id) {
        SendMessage menuMessage = new SendMessage(id.toString(), "Добро пожаловать");
        menuMessage.setReplyMarkup(replyKeyboardMaker.getMenuKeyboard());
        try {
            bot.execute(menuMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
