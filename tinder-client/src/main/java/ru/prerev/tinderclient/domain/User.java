package ru.prerev.tinderclient.domain;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String sex;
    private String name;
    private String story;
    private String looking_for;
}
