package ru.prerev.tinderclient.telegrambot.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.prerev.tinderclient.enums.bot.MenuButtonsEnum;
import ru.prerev.tinderclient.enums.bot.ScrollButtonsEnum;

import java.util.List;

@Component
public class ReplyKeyboardMaker {
    public ReplyKeyboardMarkup getMenuKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(MenuButtonsEnum.SEARCH_BUTTON.getButtonName()));
        row1.add(new KeyboardButton(MenuButtonsEnum.PROFILE_BUTTON.getButtonName()));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(MenuButtonsEnum.LOVERS_BUTTON.getButtonName()));

        return getReplyKeyboardMarkup(List.of(row1, row2));
    }

    public ReplyKeyboardMarkup getScrollKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(ScrollButtonsEnum.LEFT_BUTTON.getButtonName()));
        row1.add(new KeyboardButton(ScrollButtonsEnum.RIGHT_BUTTON.getButtonName()));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(ScrollButtonsEnum.MENU_BUTTON.getButtonName()));

        return getReplyKeyboardMarkup(List.of(row1, row2));
    }

    private ReplyKeyboardMarkup getReplyKeyboardMarkup(List<KeyboardRow> keyboardRowList) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }
}
