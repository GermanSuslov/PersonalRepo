package ru.ligaintenship.prerevolutionarytinder.dao.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ru.ligaintenship.prerevolutionarytinder.dao.User;
import ru.ligaintenship.prerevolutionarytinder.dao.repository.SpringJdbcConnectionProvider;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@PropertySource(value = {"classpath:application.properties"})
public class DataBaseService {

    @Autowired
    private Environment environment;

    private final SpringJdbcConnectionProvider provider;

    public void create(User resource) {
        String sql = environment.getProperty("database.create")
                .formatted(resource.getId(), resource.getSex(), resource.getName(), resource.getStory(), resource.getLookingFor());
        int responseCode = provider.putData(sql);
        System.out.println(responseCode);
    }

    public void deleteById(Long id) {
        String sqlUsers = environment.getProperty("database.delete-by-id.users") + id;
        String sqlMatches = environment.getProperty("database.delete-by-id.matches") + id + " and liked_id = " + id;
        provider.deleteData(sqlUsers);
        provider.deleteData(sqlMatches);
    }

    public List<User> findAll() {
        String sql = environment.getProperty("database.find-all");
        List<User> list = provider.getData(sql);
        return list;
    }

    public User findById(Long id) {
        String sql = environment.getProperty("database.find-by-id") + id.toString();
        List<User> foundedUser = provider.getData(sql);
        return foundedUser.stream().findFirst().get();
    }

    public List<List<User>> findMatch(Long id) {
        String sqlLike = String.format(environment.getProperty("database.find-match.user-liked"), id, id);

        String sqlLiked = String.format(environment.getProperty("database.find-match.liked-user"), id, id);

        String sqlEachOther = environment.getProperty("database.find-match.mutual-liked") + id.toString();
        List<List<User>> resList = new ArrayList<>();
        resList.add(provider.getData(sqlLike));
        resList.add(provider.getData(sqlLiked));
        resList.add(provider.getData(sqlEachOther));
        return resList;
    }

    public List<User> search(Long id) {
        String sql = String.format(environment.getProperty("database.search"), "Всех", "Сударъ", "Сударыня", "Всех", id, id);
        return provider.getData(sql);
    }

    public void match(Long id, Long id_matched) {
        String sql = environment.getProperty("database.match")
                .formatted(id, id_matched);
        provider.putData(sql);
    }

/*    public void create(User resource) {
        String sql = "insert into tinder.tinder_users (user_id, sex, name, story, looking_for) values (%d, '%s', '%s', '%s', '%s')"
                .formatted(resource.getId(), resource.getSex(), resource.getName(), resource.getStory(), resource.getLookingFor());
        int responseCode = provider.putData(sql);
        System.out.println(responseCode);
    }

    public void deleteById(Long id) {
        String sqlUsers = "delete from tinder.tinder_users where user_id = " + id;
        String sqlMatches = "delete from tinder.user_matches where user_id = " + id + " and liked_id = " + id;
        provider.deleteData(sqlUsers);
        provider.deleteData(sqlMatches);
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

    public void match(Long id, Long id_matched) {
        String sql = "insert into tinder.user_matches (user_id, liked_id) values (%d, %d)"
                .formatted(id, id_matched);
        provider.putData(sql);
    }*/
}
