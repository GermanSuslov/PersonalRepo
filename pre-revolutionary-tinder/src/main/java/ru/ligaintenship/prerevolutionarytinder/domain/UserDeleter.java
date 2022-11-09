package ru.ligaintenship.prerevolutionarytinder.domain;

import ru.ligaintenship.prerevolutionarytinder.rest.SpringJdbcConnectionProvider;

public class UserDeleter {
    private final SpringJdbcConnectionProvider provider;

    public UserDeleter(SpringJdbcConnectionProvider provider) {
        this.provider = provider;
    }

    public void deleteById(Long id) {
        String sqlUsers = "delete from tinder.tinder_users where user_id = " + id;
        String sqlMatches = "delete from tinder.user_matches where user_id = " + id + " and liked_id = " + id;
        provider.deleteData(sqlUsers);
        provider.deleteData(sqlMatches);
    }
}
