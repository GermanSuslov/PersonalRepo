package ru.ligaintenship.prerevolutionarytinder.domain;

import ru.ligaintenship.prerevolutionarytinder.rest.SpringJdbcConnectionProvider;

public class UserDeleter {
    private final SpringJdbcConnectionProvider provider;

    public UserDeleter(SpringJdbcConnectionProvider provider) {
        this.provider = provider;
    }
    public void deleteById(Long id) {
        
    }
}
