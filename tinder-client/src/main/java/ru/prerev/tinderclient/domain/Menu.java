package ru.prerev.tinderclient.domain;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.constants.bot.MenuButtonsEnum;
import ru.prerev.tinderclient.constants.bot.ProfileButtonsEnum;
import ru.prerev.tinderclient.constants.bot.ScrollButtonsEnum;
import ru.prerev.tinderclient.rest.GetService;
import ru.prerev.tinderclient.search.GenderSearcher;
import ru.prerev.tinderclient.telegrambot.Bot;

import ru.prerev.tinderclient.telegrambot.keyboard.InlineKeyboardMaker;
import ru.prerev.tinderclient.telegrambot.keyboard.ReplyKeyboardMaker;

@RequiredArgsConstructor
public class Menu {
    private Bot bot;
    private final GetService getService;
    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final InlineKeyboardMaker inlineKeyboardMaker;
    private final Lovers lovers;
    private final GenderSearcher genderSearcher;
    private final Profile profile;

    public void showMenu(Long id, String message) {
        if (message.equalsIgnoreCase(ProfileButtonsEnum.MENU_BUTTON.getButtonName())) {
            menuButtons(id);
        } else if (message.equalsIgnoreCase(MenuButtonsEnum.SEARCH_BUTTON.getButtonName())) {
            genderSearcher.search(id);
        } else if (message.equalsIgnoreCase(MenuButtonsEnum.PROFILE_BUTTON.getButtonName())) {
            profile.showProfile(id, getService.get(id), inlineKeyboardMaker.getInlineMessageProfileButtons());
        } else if (message.equalsIgnoreCase(MenuButtonsEnum.LOVERS_BUTTON.getButtonName())) {
            lovers.showLovers(id, ScrollButtonsEnum.RIGHT_BUTTON.getButtonName());
        } else if (message.equalsIgnoreCase(ScrollButtonsEnum.RIGHT_BUTTON.getButtonName())) {
            if (!lovers.getLoversMap().containsKey(id)) {
                genderSearcher.match(id);
                genderSearcher.search(id);
            } else {
                lovers.showLovers(id, message);
            }
        } else if (message.equalsIgnoreCase(ScrollButtonsEnum.LEFT_BUTTON.getButtonName())) {
            if (lovers.getLoversMap() == null || !lovers.getLoversMap().containsKey(id)) {
                genderSearcher.search(id);
            } else {
                lovers.showLovers(id, message);
            }
        } else if (message.equalsIgnoreCase(ScrollButtonsEnum.MENU_BUTTON.getButtonName())) {
            if (lovers.getLoversMap() == null || !lovers.getLoversMap().containsKey(id)) {
                genderSearcher.resetAddParameters(id);
            } else {
                lovers.resetAddParameters(id);
            }
            menuButtons(id);
        }
    }

    private void menuButtons(Long id) {
        SendMessage menuMessage = new SendMessage(id.toString(), "Переход в меню");
        menuMessage.setReplyMarkup(replyKeyboardMaker.getMenuKeyboard());
        try {
            bot.execute(menuMessage);
        } catch (TelegramApiException e) {
            System.out.println("Не отобразить кнопки :" + getClass());
        }
    }

    public void setBot(Bot bot) {
        this.bot = bot;
        genderSearcher.setBot(bot);
        profile.setBot(bot);
        lovers.setBot(bot);
    }
}
