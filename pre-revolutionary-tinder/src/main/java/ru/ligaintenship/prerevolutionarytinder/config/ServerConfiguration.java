package ru.ligaintenship.prerevolutionarytinder.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.ligaintenship.prerevolutionarytinder.PreRevolutionaryTinderApplication;
import ru.ligaintenship.prerevolutionarytinder.SpringJdbcConnectionProvider;

@Configuration
public class ServerConfiguration {

    @Bean
    public JdbcTemplate jdbcTemplate(DriverManagerDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/tinder_db");
        ds.setUsername("admin");
        ds.setPassword("qwertyJ4");
        return ds;
    }

    @Bean
    public SpringLiquibase springLiquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog("classpath:/db/changelog-master.yaml");
        return liquibase;
    }

    @Bean
    SpringJdbcConnectionProvider springJdbcConnectionProvider(JdbcTemplate jdbcTemplate) {
        return new SpringJdbcConnectionProvider(jdbcTemplate);
    }

/*    @Bean
    PreRevolutionaryTinderApplication preRevolutionaryTinderApplication() {
        return new PreRevolutionaryTinderApplication();
    }*/
}
