package ru.pravvich.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pravvich.config.security.handler.LoggedInChecker;
import ru.pravvich.domain.Role;
import ru.pravvich.domain.User;
import ru.pravvich.repository.UserRepository;

import java.util.List;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoggedInChecker loggedInChecker;

    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        System.out.println(user);
        return user;
    }

    @Override
    public List<String> getPermissions(String username) {
        User user = userRepository.findByUsername(username);
        return nonNull(user) ? user.getRoles().stream().map(Role::getRole).collect(toList()) : Lists.newArrayList();
    }

    @Override
    public User getCurrentUser() {
        return loggedInChecker.getLoggedInUser();
    }

    @Override
    public Boolean isCurrentUserLoggedIn() {
        return nonNull(loggedInChecker.getLoggedInUser());
    }
}
