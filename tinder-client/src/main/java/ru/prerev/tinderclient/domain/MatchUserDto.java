package ru.prerev.tinderclient.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.prerev.tinderclient.enums.resources.MatchEnum;

@Getter
@AllArgsConstructor
public class MatchUserDto {
    private User user;
    private MatchEnum match;
}
