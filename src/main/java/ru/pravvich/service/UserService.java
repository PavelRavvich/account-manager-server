package ru.pravvich.service;

import ru.pravvich.domain.User;

public interface UserService {
    User getUserByUsername(String username);
}
