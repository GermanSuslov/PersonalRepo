package ru.ligaintenship.prerevolutionarytinder.dao.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ligaintenship.prerevolutionarytinder.domain.Match;
import ru.ligaintenship.prerevolutionarytinder.domain.User;
import ru.ligaintenship.prerevolutionarytinder.dao.repository.SpringJdbcConnectionProvider;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DataBaseService {

    private final SpringJdbcConnectionProvider provider;

    private final String create = "insert into tinder.tinder_users " +
            "(user_id, sex, name, story, looking_for) values (%d, '%s', '%s', '%s', '%s')";
    private final String deleteById = "delete from tinder.tinder_users where user_id = ";
    private final String deleteByMatch = "delete from tinder.user_matches where user_id = ";
    private final String findAll = "select * from tinder.tinder_users";
    private final String findById = "select * from tinder.tinder_users where user_id = ";
    private final String findMatchUserLiked = "select tu.*\n" +
            "from tinder.user_matches um\n" +
            "join tinder.tinder_users tu\n" +
            "  on um.liked_id = tu.user_id\n" +
            "where um.user_id = %d\n" +
            "      and um.liked_id not in (select um2.user_id\n" +
            "                              from tinder.user_matches um2\n" +
            "                              where um2.liked_id = %d)";
    private final String findMatchLikedUser = "select tu.*\n" +
            "from tinder.user_matches um\n" +
            "join tinder.tinder_users tu\n" +
            "  on um.user_id = tu.user_id\n" +
            "where um.liked_id = %d\n" +
            "      and tu.user_id not in (select um2.liked_id\n" +
            "                             from tinder.user_matches um2\n" +
            "                             where um2.user_id = %d)";
    private final String findMatchMutualLiked = "select tu.*\n" +
            "from tinder.user_matches um\n" +
            "join tinder.user_matches um2\n" +
            "  on um.user_id = um2.liked_id and um.liked_id = um2.user_id\n" +
            "join tinder.tinder_users tu\n" +
            "  on um2.liked_id = tu.user_id\n" +
            "where um.liked_id = ";
    private final String search = "select tu2.*\n" +
            "from tinder.tinder_users tu\n" +
            "join tinder.tinder_users tu2\n" +
            "  on (tu.looking_for = tu2.sex or tu.looking_for = 'Всех' and tu2.sex in ('Сударъ', 'Сударыня'))\n" +
            "     and (tu.sex = tu2.looking_for or tu2.looking_for = 'Всех')\n" +
            "     and tu.user_id <> tu2.user_id\n" +
            "where tu.user_id = %d\n" +
            "      and tu2.user_id not in (select um.liked_id\n" +
            "                              from tinder.user_matches um\n" +
            "                              where um.user_id = %d)";
    private final String putMatch = "insert into tinder.user_matches (user_id, liked_id) values (%d, %d)";



    public void create(User resource) {
        String sql = create
                .formatted(resource.getId(), resource.getSex(), resource.getName(), resource.getStory(), resource.getLookingFor());
        int responseCode = provider.putData(sql);
        System.out.println(responseCode);
    }

    @Transactional
    public void deleteById(Long id) {
        String sqlUsers = deleteById + id;
        String sqlMatches = deleteByMatch + id + " and liked_id = " + id;
        provider.deleteData(sqlUsers);
        provider.deleteData(sqlMatches);
    }

    public List<User> findAll() {
        String sql = findAll;
        List<User> list = provider.getData(sql);
        return list;
    }

    public User findById(Long id) {
        String sql = findById + id.toString();
        List<User> foundedUser = provider.getData(sql);
        return foundedUser.stream().findFirst().get();
    }

    public List<List<User>> findMatch(Long id) {
        String sqlLike = String.format(findMatchUserLiked, id, id);

        String sqlLiked = String.format(findMatchLikedUser, id, id);

        String sqlEachOther = findMatchMutualLiked + id.toString();
        List<List<User>> resList = new ArrayList<>();
        resList.add(provider.getData(sqlLike));
        resList.add(provider.getData(sqlLiked));
        resList.add(provider.getData(sqlEachOther));
        return resList;
    }

    public List<User> search(Long id) {
        String sql = String.format(search, id, id);
        return provider.getData(sql);
    }

    public void match(Match match) {
        String sql = putMatch
                .formatted(match.getId(), match.getLikedId());
        provider.putData(sql);
    }
}
