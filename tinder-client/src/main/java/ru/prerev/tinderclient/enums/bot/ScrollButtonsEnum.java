package ru.prerev.tinderclient.enums.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScrollButtonsEnum {
    RIGHT_BUTTON("Вправо"),
    LEFT_BUTTON("Влево"),
    MENU_BUTTON("Меню");

    private final String buttonName;
}
