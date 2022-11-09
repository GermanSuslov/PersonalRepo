package ru.prerev.tinderclient.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    @JsonProperty("user_id")
    private Long user_id;
    @JsonProperty("sex")
    private String sex;
    @JsonProperty("name")
    private String name;
    @JsonProperty("story")
    private String story;
    @JsonProperty("looking_for")
    private String looking_for;


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
