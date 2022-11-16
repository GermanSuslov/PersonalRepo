package ru.prerev.tinderclient.enums.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderEnum {
    MALE("Сударъ"),
    FEMALE("Сударыня"),
    ALL("Всех");

    private final String gender;
}
