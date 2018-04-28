package ru.pravvich.service;

import ru.pravvich.domain.User;

import java.util.List;

public interface UserService {

    User getUserByUsername(String username);

    List<String> getPermissions(String username);

    User getCurrentUser();

    Boolean isCurrentUserLoggedIn();

}
