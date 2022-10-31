package ru.ligaintenship.prerevolutionarytinder.domain;

import ru.ligaintenship.prerevolutionarytinder.SpringJdbcConnectionProvider;

public class UserCreator {
    private final SpringJdbcConnectionProvider provider;

    public UserCreator(SpringJdbcConnectionProvider provider) {
        this.provider = provider;
    }

    public int create(User resource) {
        String sql = "insert into tinder.tinder_users (sex, name, story, looking_for) values ('%s', '%s', '%s')"
                .formatted(resource.getSex(), resource.getName(), resource.getStory(), resource.getLooking_for());
        int responseCode = provider.putData(sql);
        User user = new User(resource.getId(),
                resource.getName(),
                resource.getSex(),
                resource.getStory(),
                resource.getLooking_for());
        return responseCode;
    }
}
