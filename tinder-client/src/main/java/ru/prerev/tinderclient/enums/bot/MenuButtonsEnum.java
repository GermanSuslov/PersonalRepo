package ru.prerev.tinderclient.enums.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MenuButtonsEnum {
    SEARCH_BUTTON("Поиск"),
    PROFILE_BUTTON("Анкета"),
    LOVERS_BUTTON("Любимцы");

    private final String buttonName;
}
