package ru.prerev.tinderclient.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Long user_id;
    private String sex;
    private String name;
    private String story;
    private String looking_for;

    public User(Long user_id) {
        this.user_id = user_id;
    }

    public boolean initiated() {
        boolean initiated = true;
        if (this.user_id == null) {
            return false;
        }
        if (this.sex == null) {
            return false;
        }
        if (this.name == null) {
            return false;
        }
        if (this.story == null) {
            return false;
        }
        if (this.looking_for == null) {
            return false;
        }
        return initiated;
    }

    @Override
    public String toString() {
        String form = name + " \n" + sex + " \n" + story + " \n";
        return form;
    }
}
