package ru.pravvich.service;

import ru.pravvich.domain.User;

/**
 * @author Pavel Ravvich.
 */
public interface UserService {
    User getUserByUsername(String username);
}
