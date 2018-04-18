package ru.pravvich.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pravvich.config.api.RestApi;
import ru.pravvich.service.UserService;

@RestApi
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static final UserRest MOCK = new UserRest(1, "", "", "");

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public UserRest login(@RequestBody() UserRest user) {
        return MOCK;
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public void logout(@RequestBody() UserRest user) {
    }

    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    public UserRest registration(@RequestBody() UserRest user) {
        return MOCK;
    }
}
