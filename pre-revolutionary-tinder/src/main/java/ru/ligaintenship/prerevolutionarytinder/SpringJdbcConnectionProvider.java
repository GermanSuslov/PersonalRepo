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
        String sql = "select * from lab.exchange_rates where cost > ? and currency_code like ?";
        ExchangeMapper mapper = new ExchangeMapper();
        BigDecimal argBig = new BigDecimal(10);
        List<User> resList = jdbcTemplate.query(sql, mapper, argBig, "P%");

        System.out.println("SpringJdbcConnectionProvider run");
        System.out.println(resList);
    }

    private static class ExchangeMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setId(Long.parseLong(rs.getString(1)));
            user.setSex(rs.getString("sex"));
            user.setName(rs.getString("name"));
            user.setStory(rs.getString("story"));
            return user;
        }
    }
}
