package ru.ligaintenship.prerevolutionarytinder;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.ligaintenship.prerevolutionarytinder.domain.User;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SpringJdbcConnectionProvider {
    private JdbcTemplate jdbcTemplate;

    public SpringJdbcConnectionProvider(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void printData() {
        String sql = "select * from tinder.tinder_users where user_id = ?";
        long id = 2;
        ExchangeMapper mapper = new ExchangeMapper();
        List<User> resList = jdbcTemplate.query(sql, mapper, id);

        System.out.println("SpringJdbcConnectionProvider run");
        System.out.println(resList);
    }

    private static class ExchangeMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User(Long.parseLong(rs.getString(1)),
                    rs.getString("sex"),
                    rs.getString("name"),
                    rs.getString("story"),
                    rs.getString("looking_for"));
            return user;
        }
    }
}
