package ru.ligaintenship.prerevolutionarytinder.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.ligaintenship.prerevolutionarytinder.exceptions.UserNotFoundException;
import ru.ligaintenship.prerevolutionarytinder.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    private final String create = "insert into tinder.tinder_users " +
            "(user_id, sex, name, story, looking_for) values (%d, '%s', '%s', '%s', '%s')";
    private final String deleteById = "delete from tinder.tinder_users where user_id = ";

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

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(User resource) {
        String sql = create
                .formatted(resource.getId(), resource.getSex(), resource.getName(), resource.getStory(), resource.getLookingFor());
        jdbcTemplate.update(sql);
    }

    public void deleteById(Long id) {
        String sql = deleteById + id;
        jdbcTemplate.update(sql);
    }

    public List<User> findAll() {
        String sql = findAll;
        ExchangeMapper mapper = new ExchangeMapper();
        List<User> resList = jdbcTemplate.query(sql, mapper);
        log.debug("GetData method completed");
        return resList;
    }

    public User findById(Long id) {
        String sql = findById + id.toString();
        ExchangeMapper mapper = new ExchangeMapper();
        List<User> foundedUser = jdbcTemplate.query(sql, mapper);

        Optional<User> user = null;
        try {
            user = Optional.of(foundedUser.stream().findFirst().get());
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("User " + id + " not found");
        }
        return user.get();
    }

    public List<List<User>> findMatch(Long id) {
        String sqlLike = String.format(findMatchUserLiked, id, id);

        String sqlLiked = String.format(findMatchLikedUser, id, id);

        String sqlEachOther = findMatchMutualLiked + id.toString();
        ExchangeMapper mapper = new ExchangeMapper();
        List<List<User>> resList = new ArrayList<>();
        resList.add(jdbcTemplate.query(sqlLike, mapper));
        resList.add(jdbcTemplate.query(sqlLiked, mapper));
        resList.add(jdbcTemplate.query(sqlEachOther, mapper));
        return resList;
    }

    public List<User> search(Long id) {
        String sql = String.format(search, id, id);
        ExchangeMapper mapper = new ExchangeMapper();
        List<User> resList = jdbcTemplate.query(sql, mapper);
        return resList;
    }

    public List<User> getData(String sql) {
        ExchangeMapper mapper = new ExchangeMapper();
        List<User> resList = jdbcTemplate.query(sql, mapper);
        log.debug("GetData method completed");
        return resList;
    }

    public int putData(String sql) {
        int resCode = jdbcTemplate.update(sql);
        log.debug("PutData method completed with code: " + resCode);
        return resCode;
    }

    public void deleteData(String sql) {
        jdbcTemplate.update(sql);
    }

    private static class ExchangeMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User(rs.getLong("user_id"),
                    rs.getString("sex"),
                    rs.getString("name"),
                    rs.getString("story"),
                    rs.getString("looking_for"));
            return user;
        }
    }
}
