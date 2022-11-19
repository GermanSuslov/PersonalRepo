package ru.prerev.tinderclient.domain;

import lombok.Data;
import ru.prerev.tinderclient.enums.resources.GenderEnum;

@Data
public class User {
    private Long id;
    private GenderEnum sex;
    private String name;
    private String story;
    private GenderEnum lookingFor;
}
