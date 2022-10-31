package ru.ligaintenship.prerevolutionarytinder.domain;

import org.springframework.stereotype.Component;

public class UserCreator implements UserService {
    public Long create(User resource) {
        User user = new User(resource.getId(),
                resource.getName(),
                resource.getSex(),
                resource.getStory(),
                resource.getLooking_for());
        return user.getId();
    }
}
