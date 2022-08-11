package com.org.SpringSecurityAuth.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//On ajoute l'annotation configuration pour dire à Spring que c'est une classe de configuration.
@Configuration
@EnableWebSecurity
public class SpringSecutityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {

    }
}
