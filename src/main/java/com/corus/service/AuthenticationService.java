package com.corus.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;


/**
 * Created by dmitrigu on 21/09/2019.
 */
@Service
public class AuthenticationService {


    public User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth == null ? null : (User) auth.getPrincipal();
    }

}
