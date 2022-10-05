package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
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
        validate(user);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("update user: " + user.toString());
        validate(user);
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

    public static Boolean validate(User user) {
        if (user.getId() < 0) {
            throw new ValidationException("id less zero");
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("email is incorrect");
        }
        if (user.getLogin().isBlank()) {
            throw new ValidationException("login is blank");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("birthday in future");
        }
        return true;
    }
}
