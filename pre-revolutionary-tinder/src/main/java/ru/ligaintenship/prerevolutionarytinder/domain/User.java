package ru.ligaintenship.prerevolutionarytinder.domain;

import lombok.Data;
import ru.ligaintenship.prerevolutionarytinder.enums.GenderEnum;

@Data
public class User {
    private final Long id;
    private final GenderEnum sex;
    private final String name;
    private final String story;
    private final GenderEnum lookingFor;
}
