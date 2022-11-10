package ru.ligaintenship.prerevolutionarytinder.domain;

import ru.ligaintenship.prerevolutionarytinder.rest.SpringJdbcConnectionProvider;

public class UserMatcher {
    private final SpringJdbcConnectionProvider provider;

    public UserMatcher(SpringJdbcConnectionProvider provider) {
        this.provider = provider;
    }
    public void match(Long id, Long id_matched) {
        String sql = "insert into tinder.user_matches (user_id, liked_id) values (%d, %d)"
                .formatted(id, id_matched);
        provider.putData(sql);
    }
}
