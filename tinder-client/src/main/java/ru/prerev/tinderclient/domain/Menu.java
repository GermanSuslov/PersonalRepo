package ru.prerev.tinderclient.domain;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.prerev.tinderclient.search.GenderSearcher;
import ru.prerev.tinderclient.search.MatchSearcher;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.InlineKeyboardMaker;
@RequiredArgsConstructor
public class Menu {
    private Bot bot;
    private final InlineKeyboardMaker inlineKeyboardMaker;
    private final GenderSearcher genderSearcher;
    private final Authorizer authorizer;
    private final MatchSearcher matchSearcher;
    public void setBot(Bot bot) {
        this.bot = bot;
    }
    public void showMenu(Long id, String message) {
        if (message.equalsIgnoreCase("Перейти в меню")) {
            SendMessage menuMessage = new SendMessage();
            menuMessage.setChatId(id);
            menuMessage.setReplyMarkup(inlineKeyboardMaker.getMenuButton());
        }
        if (message.equalsIgnoreCase("Поиск")) {

        }
        if (message.equalsIgnoreCase("Анкета")) {
            SendMessage menuMessage = new SendMessage();
            menuMessage.setChatId(id);
            menuMessage.setReplyMarkup(inlineKeyboardMaker.getMenuButton());
        }
        if (message.equalsIgnoreCase("Любимцы")) {
            SendMessage menuMessage = new SendMessage();
            menuMessage.setChatId(id);
            menuMessage.setReplyMarkup(inlineKeyboardMaker.getMenuButton());
        }
    }
}
