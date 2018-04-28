package ru.pravvich.web.user;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pravvich.config.api.RestApi;
import ru.pravvich.domain.Role;
import ru.pravvich.domain.User;
import ru.pravvich.service.UserService;

import javax.annotation.security.PermitAll;

import static java.util.stream.Collectors.toList;

@RestApi
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static UserRest MOCK;

    @PermitAll
    @GetMapping("/is_logged")
    public Boolean isLoggedIn() {
        return userService.isCurrentUserLoggedIn();
    }

    @PermitAll
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public UserRest login(@RequestBody() UserRest user) {
        User auth = userService.getUserByUsername(user.getUsername());
        return toRest(auth);
    }

    @PermitAll
    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public void logout(@RequestBody() UserRest user) {
    }

    @PermitAll
    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    public UserRest registration(@RequestBody() UserRest user) {
        return MOCK;
    }

    private UserRest toRest(@NonNull User user) {
        UserRest rest = new UserRest();
        rest.setId(user.getId());
        rest.setUsername(user.getUsername());
        rest.setPassword(user.getPassword());
        rest.setAccountNonLocked(user.isAccountNonLocked());
        rest.setRoles(user.getRoles().stream().map(Role::getRole).collect(toList()));
        return rest;
    }
}
