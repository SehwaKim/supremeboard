package me.sehwa.supremeboard.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/supremeboard.properties")
@Getter @Setter
public class DBInfo {

    @Value("${supremeboard.db.driverClassName}")
    private String driverClassName;

    @Value("${supremeboard.db.url}")
    private String url;

    @Value("${supremeboard.db.username}")
    private String username;

    @Value("${supremeboard.db.password}")
    private String password;

    @Value("${supremeboard.db.autoCommit}")
    private boolean autoCommit;

    @Value("${supremeboard.db.connectionTestQuery}")
    private String connectionTestQuery;

    @Value("${supremeboard.db.connectionTimeout}")
    private Long connectionTimeout;

    @Value("${supremeboard.db.idleTimeout}")
    private Long idleTimeout;

    @Value("${supremeboard.db.maxLifetime}")
    private Long maxLifetime;

    @Value("${supremeboard.db.minimumIdle}")
    private Integer minimumIdle;

    @Value("${supremeboard.db.maximumPoolSize}")
    private Integer maximumPoolSize;
}
