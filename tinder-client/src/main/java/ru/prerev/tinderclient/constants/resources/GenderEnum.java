package ru.prerev.tinderclient.constants.resources;

public enum GenderEnum {
    MALE("Сударъ"),
    FEMALE("Сударыня"),
    ALL("Всех");

    private final String gender;

    GenderEnum(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
