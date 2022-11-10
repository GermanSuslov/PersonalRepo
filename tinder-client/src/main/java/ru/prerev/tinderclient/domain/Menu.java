package ru.prerev.tinderclient.domain;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.rest.MatchService;
import ru.prerev.tinderclient.search.GenderSearcher;
import ru.prerev.tinderclient.search.MatchSearcher;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.InlineKeyboardMaker;

import ru.prerev.tinderclient.telegrambot.keyboard.ReplyKeyboardMaker;

import java.util.ArrayList;

@RequiredArgsConstructor
public class Menu {
    private Bot bot;
    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final Lovers lovers;
    private final GenderSearcher genderSearcher;
    private final MatchSearcher matchSearcher;
    private final Profile profile;

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public void showMenu(Long id, String message) {
        if (message.equalsIgnoreCase("Перейти в меню")) {
            menuButtons(id);
        }
        if (message.equalsIgnoreCase("Поиск")) {
            genderSearcher.setBot(bot);
            genderSearcher.search(id);
        }
        if (message.equalsIgnoreCase("Анкета")) {
            profile.setBot(bot);
            profile.showProfile(id);
        }
        if (message.equalsIgnoreCase("Любимцы")) {
            matchSearcher.setBot(bot);
            ArrayList<ArrayList<User>> loversList = matchSearcher.search(id);
            lovers.setUserArrayLists(loversList);
            lovers.setBot(bot);
        }

        if (message.equalsIgnoreCase("Вправо")) {
            genderSearcher.match(id);
            genderSearcher.search(id);
        }
        if (message.equalsIgnoreCase("Влево")) {
            genderSearcher.search(id);
        }
        if (message.equalsIgnoreCase("Меню")) {
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
