package ru.ligaintenship.prerevolutionarytinder.domain;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String sex;
    private String name;
    private String story;
}
