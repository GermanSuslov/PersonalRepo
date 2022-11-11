package ru.ligaintenship.prerevolutionarytinder.domain;

import jdk.dynalink.linker.LinkerServices;
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

    public List<List<User>> findMatch(Long id) {
        String sqlLike = String.format("select tu.*\n" +
                "from tinder.user_matches um\n" +
                "join tinder.tinder_users tu\n" +
                "  on um.liked_id = tu.user_id\n" +
                "where um.user_id = %d\n" +
                "      and um.liked_id not in (select um2.user_id\n" +
                "                              from tinder.user_matches um2\n" +
                "                              where um2.liked_id = %d)", id, id);

        String sqlLiked = String.format("select tu.*\n" +
                "from tinder.user_matches um\n" +
                "join tinder.tinder_users tu\n" +
                "  on um.user_id = tu.user_id\n" +
                "where um.liked_id = %d\n" +
                "      and tu.user_id not in (select um2.liked_id\n" +
                "                             from tinder.user_matches um2\n" +
                "                             where um2.user_id = %d)", id, id);

        String sqlEachOther = "select tu.*\n" +
                "from tinder.user_matches um\n" +
                "join tinder.user_matches um2\n" +
                "  on um.user_id = um2.liked_id and um.liked_id = um2.user_id\n" +
                "join tinder.tinder_users tu\n" +
                "  on um2.liked_id = tu.user_id\n" +
                "where um.liked_id = " + id.toString();
        List<List<User>> resList = new ArrayList<>();
        resList.add(provider.getData(sqlLike));
        resList.add(provider.getData(sqlLiked));
        resList.add(provider.getData(sqlEachOther));
        return resList;
    }

    public List<User> search(Long id) {
        String sql = String.format("select tu2.*\n" +
                "from tinder.tinder_users tu\n" +
                "join tinder.tinder_users tu2\n" +
                "  on (tu.looking_for = tu2.sex or tu.looking_for = 'Всех' and tu2.sex in ('Сударъ', 'Сударыня'))\n" +
                "     and (tu.sex = tu2.looking_for or tu2.looking_for = 'Всех')\n" +
                "     and tu.user_id <> tu2.user_id\n" +
                "where tu.user_id = %d\n" +
                "      and tu2.user_id not in (select um.liked_id\n" +
                "                              from tinder.user_matches um\n" +
                "                              where um.user_id = %d)", id, id);
        return provider.getData(sql);
    }
}
