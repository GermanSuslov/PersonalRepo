package ru.prerev.tinderclient.domain;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class User {
    private Long id;
    private String sex;
    private String name;
    private String story;
    private String lookingFor;
}
