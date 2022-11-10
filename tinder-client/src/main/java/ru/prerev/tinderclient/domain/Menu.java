package ru.prerev.tinderclient.domain;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.search.GenderSearcher;
import ru.prerev.tinderclient.search.MatchSearcher;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.InlineKeyboardMaker;

import ru.prerev.tinderclient.telegrambot.keyboard.ReplyKeyboardMaker;

@RequiredArgsConstructor
public class Menu {
    private Bot bot;
    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final GenderSearcher genderSearcher;
    private final MatchSearcher matchSearcher;
    private final Profile profile;

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public void showMenu(Long id, String message) {
        if (message.equalsIgnoreCase("Показать анкету")) {
            SendMessage menuMessage = new SendMessage(id.toString(), "Добро пожаловать");
            menuMessage.setReplyMarkup(replyKeyboardMaker.getMenuKeyboard());
            try {
                bot.execute(menuMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
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
            matchSearcher.search(id);
        }

        if (message.equalsIgnoreCase("Вправо")) {

        }
        if (message.equalsIgnoreCase("Влево")) {

        }
        if (message.equalsIgnoreCase("Меню")) {

        }
    }
}
