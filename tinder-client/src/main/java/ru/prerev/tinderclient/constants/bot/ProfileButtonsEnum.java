package ru.prerev.tinderclient.constants.bot;

public enum ProfileButtonsEnum {
    EDIT_PROFILE_BUTTON("Изменить анкету"),
    MENU_BUTTON("Перейти в меню");

    private final String buttonName;

    ProfileButtonsEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
