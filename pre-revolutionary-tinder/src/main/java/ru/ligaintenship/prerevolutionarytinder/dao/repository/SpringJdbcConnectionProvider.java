package ru.ligaintenship.prerevolutionarytinder.dao.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.ligaintenship.prerevolutionarytinder.dao.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class SpringJdbcConnectionProvider {
    private final JdbcTemplate jdbcTemplate;

    public SpringJdbcConnectionProvider(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getData(String sql) {
        ExchangeMapper mapper = new ExchangeMapper();
        List<User> resList = jdbcTemplate.query(sql, mapper);
        System.out.println("SpringJdbcConnectionProvider run");
        return resList;
    }

    public int putData(String sql) {
        int resCode = jdbcTemplate.update(sql);
        System.out.println("SpringJdbcConnectionProvider run : " + resCode);
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
