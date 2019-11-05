/**
 * Created by dmitrigu on 21/09/2019.
 */
package com.corus.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;

public class WeatherUserDetailsService implements UserDetailsService {

    @Override
    public User loadUserByUsername(String username) {
        return new User(username, getPassword(username), Arrays.asList(new SimpleGrantedAuthority("USER")));
    }

    private String getPassword(String username) {
        return "qwerty";
    }
}
