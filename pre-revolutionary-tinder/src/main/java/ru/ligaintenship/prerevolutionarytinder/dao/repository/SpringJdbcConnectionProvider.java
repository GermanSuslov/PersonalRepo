package ru.ligaintenship.prerevolutionarytinder.dao.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.ligaintenship.prerevolutionarytinder.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
public class SpringJdbcConnectionProvider {
    private final JdbcTemplate jdbcTemplate;

    public SpringJdbcConnectionProvider(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
