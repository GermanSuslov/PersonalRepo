package ru.ligaintenship.prerevolutionarytinder.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.ligaintenship.prerevolutionarytinder.dao.service.DataBaseService;
import ru.ligaintenship.prerevolutionarytinder.dao.repository.SpringJdbcConnectionProvider;

@Configuration
@PropertySource(value= {"classpath:application.properties"})
public class ServerConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public JdbcTemplate jdbcTemplate(DriverManagerDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
        ds.setUrl(environment.getProperty("spring.datasource.url"));
        ds.setUsername(environment.getProperty("spring.datasource.username"));
        ds.setPassword(environment.getProperty("spring.datasource.password"));
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
    @Bean
    DataBaseService dataBaseService(SpringJdbcConnectionProvider provider) {
        return new DataBaseService(provider);
    }

/*    @Bean
    UserCreator userCreator(SpringJdbcConnectionProvider provider) {
        return new UserCreator(provider);
    }

    @Bean
    UserDeleter userDeleter(SpringJdbcConnectionProvider provider) {
        return new UserDeleter(provider);
    }

    @Bean
    UserFinder userFinder(SpringJdbcConnectionProvider provider) {
        return new UserFinder(provider);
    }

    @Bean
    UserMatcher userMatcher(SpringJdbcConnectionProvider provider) {
        return new UserMatcher(provider);
    }*/

}
