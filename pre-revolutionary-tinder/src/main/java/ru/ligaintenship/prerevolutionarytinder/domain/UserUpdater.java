package ru.ligaintenship.prerevolutionarytinder.domain;

import ru.ligaintenship.prerevolutionarytinder.SpringJdbcConnectionProvider;

public class UserUpdater {
    private final SpringJdbcConnectionProvider provider;

    public UserUpdater(SpringJdbcConnectionProvider provider) {
        this.provider = provider;
    }
    public void update(User resource) {
    }
}
