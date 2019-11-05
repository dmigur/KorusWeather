package com.corus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * Created by dmitrigu on 21/09/2019.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests().anyRequest().hasRole("USER")
            .and().formLogin();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication()
                .withUser("eugene")
                .password("{noop}eugene")
                .roles("USER");

        auth.inMemoryAuthentication()
                .withUser("dmitri")
                .password("{noop}dmitri")
                .roles("USER");

        auth.inMemoryAuthentication()
                .withUser("oleg")
                .password("{noop}oleg")
                .roles("USER");

        auth.inMemoryAuthentication()
                .withUser("roman")
                .password("{noop}roman")
                .roles("USER");
    }


}
