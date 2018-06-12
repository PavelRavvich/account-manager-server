package ru.pravvich.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pravvich.domain.User;
import ru.pravvich.repository.UserRepository;

/**
 * @author Pavel Ravvich.
 * <p>
 * Service for User entity.
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * Repository layer.
     */
    private final UserRepository userRepository;

    /**
     * Default constructor.
     *
     * @param userRepository user repository layer.
     */
    @Autowired
    public UserServiceImpl(@NonNull UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get User by username.
     * Auth method for UserDetailServiceImpl.
     *
     * @param username of auth user.
     * @return User if exist or null if user does't exist.
     */
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
