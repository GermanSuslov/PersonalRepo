package ru.prerev.tinderclient.telegrambot.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.prerev.tinderclient.enums.bot.ProfileButtonsEnum;
import ru.prerev.tinderclient.enums.resources.GenderEnum;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardMaker {
    public InlineKeyboardMarkup getInlineMessageSexButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(List.of(
                getButton(GenderEnum.MALE.getGender()),
                getButton(GenderEnum.FEMALE.getGender()))
        );
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineMessageLookingForButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(List.of(
                getButton(GenderEnum.MALE.getGender()),
                getButton(GenderEnum.FEMALE.getGender()),
                getButton(GenderEnum.ALL.getGender())
        ));
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineMessageProfileButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(List.of(
                getButton(ProfileButtonsEnum.EDIT_PROFILE_BUTTON.getButtonName()),
                getButton(ProfileButtonsEnum.MENU_BUTTON.getButtonName())
        ));
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
