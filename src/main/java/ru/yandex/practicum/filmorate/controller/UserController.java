package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("add user: " + user.toString());
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("update user: " + user.toString());
        return userService.updateUser(user);
    }

    @GetMapping
    public Collection<User> getUsers() {
        return userService.getUsers();
    }
}
