package ru.prerev.tinderclient.telegrambot.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.prerev.tinderclient.constants.bot.ProfileButtonsEnum;
import ru.prerev.tinderclient.constants.resources.GenderEnum;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardMaker {
    public InlineKeyboardMarkup getInlineMessageSexButtons() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(getButton(GenderEnum.MALE.getGender()));
        rowList.add(getButton(GenderEnum.FEMALE.getGender()));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineMessageLookingForButtons() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(getButton(GenderEnum.MALE.getGender()));
        rowList.add(getButton(GenderEnum.FEMALE.getGender()));
        rowList.add(getButton(GenderEnum.ALL.getGender()));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineMessageProfileButtons() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(getButton(ProfileButtonsEnum.EDIT_PROFILE_BUTTON.getButtonName()));
        rowList.add(getButton(ProfileButtonsEnum.MENU_BUTTON.getButtonName()));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private List<InlineKeyboardButton> getButton(String buttonName) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData(buttonName);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }
}
