package ru.pravvich.service;

import org.springframework.stereotype.Service;
import ru.pravvich.web.user.UserRest;

@Service
public class UserService {
    public UserRest getUser(String login, String password) {
        return new UserRest(1,"Pavel", "12345", "123@1");
    }
}
