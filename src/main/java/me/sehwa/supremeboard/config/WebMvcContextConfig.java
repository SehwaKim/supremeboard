package me.sehwa.supremeboard.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "me.sehwa.supremeboard.controller")
public class WebMvcContextConfig {
}
