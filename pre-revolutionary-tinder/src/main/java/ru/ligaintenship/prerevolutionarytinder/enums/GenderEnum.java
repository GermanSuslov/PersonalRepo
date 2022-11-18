package ru.ligaintenship.prerevolutionarytinder.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderEnum {
    MALE("Сударъ"),
    FEMALE("Сударыня"),
    ALL("Всех");

    private final String gender;

    public static GenderEnum getGenderEnum(String gender) {
        if (gender.equalsIgnoreCase("Сударъ")) {
            return GenderEnum.MALE;
        }
        if (gender.equalsIgnoreCase("Сударыня")) {
            return GenderEnum.FEMALE;
        }
        if (gender.equalsIgnoreCase("Всех")) {
            return GenderEnum.ALL;
        } else return null;
    }
}
