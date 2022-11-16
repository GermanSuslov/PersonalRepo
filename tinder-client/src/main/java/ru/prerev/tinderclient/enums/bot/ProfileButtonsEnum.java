package ru.prerev.tinderclient.enums.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProfileButtonsEnum {
    EDIT_PROFILE_BUTTON("Изменить анкету"),
    MENU_BUTTON("Перейти в меню");

    private final String buttonName;
}
