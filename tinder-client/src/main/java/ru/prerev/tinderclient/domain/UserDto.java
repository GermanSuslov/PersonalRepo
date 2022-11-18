package ru.prerev.tinderclient.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDto {
    private List<User> userLiked;
    private List<User> likedUser;
    private List<User> mutualLiked;

    public UserDto() {
    }
}
