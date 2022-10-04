package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    HashMap<Integer, User> users = new HashMap<>();

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("add user: " + user.toString());
        User.validate(user);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("update user: " + user.toString());
        User.validate(user);
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return user;
        } else {
            throw new RuntimeException("unknown user");
        }
    }

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }
}
