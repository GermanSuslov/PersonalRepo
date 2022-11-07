package ru.ligaintenship.prerevolutionarytinder.domain;

import ru.ligaintenship.prerevolutionarytinder.rest.SpringJdbcConnectionProvider;

import java.util.ArrayList;
import java.util.List;

public class UserFinder {
    private final SpringJdbcConnectionProvider provider;


    public UserFinder(SpringJdbcConnectionProvider provider) {
        this.provider = provider;
    }
    public List<User> findAll() {
        String sql = "select * from tinder.tinder_users";
        List<User> list = provider.getData(sql);
        return list;
    }
    public User findById(Long id) {
        String sql = "select * from tinder.tinder_users where user_id = " + id.toString();
        List<User> foundedUser = provider.getData(sql);
        return foundedUser.stream().findFirst().get();
    }
}
