package ru.prerev.tinderclient.constants.resources;

public enum MatchEnum {
    LIKE("Любим вами"),
    LIKED("Вы любимы"),
    MUTUAL("Взаимность");

    private final String match;

    MatchEnum(String match) {
        this.match = match;
    }

    public String getMatch() {
        return match;
    }
}
