package ua.rusyn.crud_rest.util;

import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component

public final class ConnectionManager {

    private final Environment environment;

    public ConnectionManager(Environment environment) {
        this.environment = environment;
    }

    private static final String PASSWORD_KEY="db.password";
    private static final String USERNAME_KEY="db.username";
    private static final String URL_KEY="db.url";

   @Bean
   private DataSource dataSource(){
        DriverManagerDataSource dataSource =
                new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(environment.getProperty(URL_KEY));
        dataSource.setUsername(environment.getProperty(USERNAME_KEY));
        dataSource.setPassword(environment.getProperty(PASSWORD_KEY));
        return dataSource;
    }
    @Bean
    private JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }


}
