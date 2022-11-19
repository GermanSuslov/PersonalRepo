package ru.prerev.tinderclient.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MatchHelper {
    private int index;
    private List<MatchUserDto> users;
}
