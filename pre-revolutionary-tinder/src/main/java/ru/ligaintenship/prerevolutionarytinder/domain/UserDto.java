package ru.ligaintenship.prerevolutionarytinder.domain;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private List<User> userLiked;
    private List<User> likedUser;
    private List<User> mutualLiked;
}
