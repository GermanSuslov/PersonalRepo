package ru.ligaintenship.prerevolutionarytinder.domain;

import ru.ligaintenship.prerevolutionarytinder.rest.SpringJdbcConnectionProvider;

public class UserCreator {
    private final SpringJdbcConnectionProvider provider;

    public UserCreator(SpringJdbcConnectionProvider provider) {
        this.provider = provider;
    }

    public void create(User resource) {
        String sql = "insert into tinder.tinder_users (user_id, sex, name, story, looking_for) values (%d, '%s', '%s', '%s', '%s')"
                .formatted(resource.getUser_id().intValue(), resource.getSex(), resource.getName(), resource.getStory(), resource.getLooking_for());
        int responseCode = provider.putData(sql);
        System.out.println(responseCode);
    }
}
