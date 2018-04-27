package ru.pravvich.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pravvich.config.security.UserDetailsServiceImpl;
import ru.pravvich.domain.User;

@Service
public class UserService {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    public User getUser(String username, String password) {
        User user = userDetailsServiceImpl.loadUserByUsername(username).getUser();
        if (user.getPassword().equals(password)) {
            System.out.println(password);
        }
        System.out.println(user);
        return user;
    }
}
