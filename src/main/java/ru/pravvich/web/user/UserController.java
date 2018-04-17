package ru.pravvich.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pravvich.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public UserRest signIn(@RequestBody() UserRest userRest) {
        return userService.getUser(userRest.getEmail(), userRest.getPassword());
    }
}
