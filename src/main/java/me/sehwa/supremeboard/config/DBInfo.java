package me.sehwa.supremeboard.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/supremeboard.properties")
@Getter @Setter
public class DBInfo {

    @Value("${supremeboard.driverClassName}")
    private String driverClassName;

    @Value("${supremeboard.url}")
    private String url;

    @Value("${supremeboard.username}")
    private String username;

    @Value("${supremeboard.password}")
    private String password;

    @Value("${supremeboard.autoCommit}")
    private String autoCommit;

    @Value("${supremeboard.connectionTestQuery}")
    private String connectionTestQuery;

    @Value("${supremeboard.connectionTimeout}")
    private String connectionTimeout;

    @Value("${supremeboard.idleTimeout}")
    private String idleTimeout;

    @Value("${supremeboard.maxLifetime}")
    private String maxLifetime;

    @Value("${supremeboard.minimumIdle}")
    private String minimumIdle;

    @Value("${supremeboard.maximumPoolSize}")
    private String maximumPoolSize;
}
