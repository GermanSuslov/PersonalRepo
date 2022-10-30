package ru.ligaintenship.prerevolutionarytinder.domain;

import org.springframework.stereotype.Component;

@Component
public class UserCreator implements UserService {
    public Long create(User resource) {
        return resource.getId();
    }
}
