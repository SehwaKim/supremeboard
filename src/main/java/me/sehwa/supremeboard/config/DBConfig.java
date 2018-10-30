package me.sehwa.supremeboard.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/supremeboard.properties")
public class DBConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(env.getProperty("url"));
        config.setUsername(env.getProperty("username"));
        config.setPassword(env.getProperty("password"));
        config.setAutoCommit(Boolean.parseBoolean(env.getProperty("autoCommit")));
        config.setConnectionTestQuery(env.getProperty("connectionTestQuery"));
        config.setConnectionTimeout(Long.parseLong(env.getProperty("connectionTimeout")));
        config.setIdleTimeout(Long.parseLong(env.getProperty("idleTimeout")));
        config.setMaxLifetime(Long.parseLong(env.getProperty("maxLifetime")));
        config.setMinimumIdle(Integer.parseInt(env.getProperty("minimumIdle")));
        config.setMaximumPoolSize(Integer.parseInt(env.getProperty("maximumPoolSize")));

        return new HikariDataSource(config);
    }
}
