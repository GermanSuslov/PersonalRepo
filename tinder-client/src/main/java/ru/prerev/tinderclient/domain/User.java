package ru.prerev.tinderclient.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {
    @JsonProperty("user_id")
    private Long id;
    private String sex;
    private String name;
    private String story;
    @JsonProperty("looking_for")
    private String lookingFor;

    public boolean initiated() {
        boolean initiated = true;
        if (this.id == null) {
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
        if (this.lookingFor == null) {
            return false;
        }
        return initiated;
    }

    @Override
    public String toString() {
        return name + " \n" + sex + " \n" + story + " \n";
    }
}
