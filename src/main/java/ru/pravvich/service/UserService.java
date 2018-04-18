package ru.pravvich.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.pravvich.web.user.UserRest;

@Service
public class UserService {

    @Autowired
    private AuthenticationManagerBuilder auth;

    public UserRest getUser(String login, String password) {
        return new UserRest(1,"Pavel", "12345", "123@1");
    }
}
