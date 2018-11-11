package me.sehwa.supremeboard.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DBConfig {

    @Autowired
    private DBInfo dbInfo;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(dbInfo.getDriverClassName());
        config.setJdbcUrl(dbInfo.getUrl());
        config.setUsername(dbInfo.getUsername());
        config.setPassword(dbInfo.getPassword());
        config.setAutoCommit(dbInfo.isAutoCommit());
        config.setConnectionTestQuery(dbInfo.getConnectionTestQuery());
        config.setConnectionTimeout(dbInfo.getConnectionTimeout());
        config.setIdleTimeout(dbInfo.getIdleTimeout());
        config.setMaxLifetime(dbInfo.getMaxLifetime());
        config.setMinimumIdle(dbInfo.getMinimumIdle());
        config.setMaximumPoolSize(dbInfo.getMaximumPoolSize());

        return new HikariDataSource(config);
    }

    @Bean
    public PlatformTransactionManager transactionManger() {
        return new DataSourceTransactionManager(dataSource());
    }
}
