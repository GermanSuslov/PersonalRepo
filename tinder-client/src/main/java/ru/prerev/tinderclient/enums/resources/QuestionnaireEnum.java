package ru.prerev.tinderclient.enums.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestionnaireEnum {
    SEX_QUESTION("Вы сударь иль сударыня?"),
    NAME_QUESTION("Как вас величать?"),
    STORY_QUESTION("Опишите себя."),
    LOOKING_FOR_QUESTION("Кого вы ищите?");

    private final String question;
}
