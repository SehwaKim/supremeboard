package me.sehwa.supremeboard.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/supremeboard.properties")
public class DBConfig {

//    @Autowired
//    private DBInfo dbInfo;

    @Autowired
    Environment env;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(env.getProperty("supremeboard.driverClassName"));
        config.setJdbcUrl(env.getProperty("supremeboard.url"));
        config.setUsername(env.getProperty("supremeboard.username"));
        config.setPassword(env.getProperty("supremeboard.password"));
        config.setAutoCommit(Boolean.parseBoolean(env.getProperty("supremeboard.autoCommit")));
        config.setConnectionTestQuery(env.getProperty("supremeboard.connectionTestQuery"));
        config.setConnectionTimeout(Long.parseLong(env.getProperty("supremeboard.connectionTimeout")));
        config.setIdleTimeout(Long.parseLong(env.getProperty("supremeboard.idleTimeout")));
        config.setMaxLifetime(Long.parseLong(env.getProperty("supremeboard.maxLifetime")));
        config.setMinimumIdle(Integer.parseInt(env.getProperty("supremeboard.minimumIdle")));
        config.setMaximumPoolSize(Integer.parseInt(env.getProperty("supremeboard.maximumPoolSize")));
        /*config.setJdbcUrl(dbInfo.getUrl());
        config.setUsername(dbInfo.getUsername());
        config.setPassword(dbInfo.getPassword());
        config.setAutoCommit(Boolean.parseBoolean(dbInfo.getAutoCommit()));
        config.setConnectionTestQuery(dbInfo.getConnectionTestQuery());
        config.setConnectionTimeout(Long.parseLong(dbInfo.getConnectionTimeout()));
        config.setIdleTimeout(Long.parseLong(dbInfo.getIdleTimeout()));
        config.setMaxLifetime(Long.parseLong(dbInfo.getMaxLifetime()));
        config.setMinimumIdle(Integer.parseInt(dbInfo.getMinimumIdle()));
        config.setMaximumPoolSize(Integer.parseInt(dbInfo.getMaximumPoolSize()));*/

        return new HikariDataSource(config);
    }

    @Bean
    public PlatformTransactionManager transactionManger() {
        return new DataSourceTransactionManager(dataSource());
    }
}
