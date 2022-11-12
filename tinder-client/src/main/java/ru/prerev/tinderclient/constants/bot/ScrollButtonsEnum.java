package ru.prerev.tinderclient.constants.bot;

public enum ScrollButtonsEnum {
    RIGHT_BUTTON("Вправо"),
    LEFT_BUTTON("Влево"),
    MENU_BUTTON("Меню");

    private final String buttonName;

    ScrollButtonsEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
