package ru.pravvich.web.user;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.pravvich.config.api.RestApi;
import ru.pravvich.domain.Role;
import ru.pravvich.domain.User;
import ru.pravvich.service.UserService;

import static java.util.stream.Collectors.toList;

@RestApi
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static UserRest MOCK;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public UserRest login(@RequestBody() UserRest user) {
        User auth = userService.getUser(user.getUsername(), user.getPassword());
        return toRest(auth);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public void logout(@RequestBody() UserRest user) {
    }

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
