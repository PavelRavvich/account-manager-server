package ru.pravvich.config.security;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.pravvich.domain.Role;
import ru.pravvich.domain.User;
import ru.pravvich.service.UserService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetailsImpl loadUserByUsername(final @NonNull String username) {
        final User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> authorities = user.getRoles()
                .stream().map(Role::getRole).collect(toList())
                .stream().map(SimpleGrantedAuthority::new).collect(toList());
        return new UserDetailsImpl(user, authorities);
    }
}