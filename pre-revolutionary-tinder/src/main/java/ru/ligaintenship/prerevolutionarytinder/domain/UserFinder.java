package ru.ligaintenship.prerevolutionarytinder.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class UserFinder implements UserService {
    private List<User> userList = new ArrayList<>();
    public List<User> findAll() {
        return userList;
    }
    public User findById(Long id) {
        User foundedUser = userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .get();
        return foundedUser;
    }
}
