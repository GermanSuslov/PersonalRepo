package ru.prerev.tinderclient.constants.resources;

public enum QuestionnaireEnum {
    SEX_QUESTION("Вы сударь иль сударыня?"),
    NAME_QUESTION("Как вас величать?"),
    STORY_QUESTION("Опишите себя."),
    LOOKING_FOR_QUESTION("Кого вы ищите?");

    private final String question;

    QuestionnaireEnum(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}
