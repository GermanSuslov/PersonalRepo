package ru.prerev.tinderclient.enums.bot;

public enum MenuButtonsEnum {
    SEARCH_BUTTON("Поиск"),
    PROFILE_BUTTON("Анкета"),
    LOVERS_BUTTON("Любимцы");

    private final String buttonName;

    MenuButtonsEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
