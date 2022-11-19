package ru.prerev.tinderclient.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchHelper {
    private int index;
    private List<User> users;
}
