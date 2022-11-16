package ru.prerev.tinderclient.enums.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchEnum {
    LIKE("Любим вами"),
    LIKED("Вы любимы"),
    MUTUAL("Взаимность");

    private final String match;
}
