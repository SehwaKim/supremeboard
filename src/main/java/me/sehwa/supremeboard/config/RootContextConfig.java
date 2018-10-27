package me.sehwa.supremeboard.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"me.sehwa.supremeboard.service", "me.sehwa.supremeboard.dao"})
public class RootContextConfig {
}
