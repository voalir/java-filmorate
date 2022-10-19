package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
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
        log.info("get all users");
        return userService.getUsers();
    }

    @GetMapping(path = "/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PutMapping(path = "{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info(String.format("add friend %s to user %s", friendId, id));
        userService.addFriend(id, friendId);
    }

    @DeleteMapping(path = "{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info(String.format("delete friend %s from user %s", friendId, id));
        userService.deleteFriend(id, friendId);
    }

    @GetMapping(path = "{id}/friends")
    public Collection<User> friendsList(@PathVariable int id) {
        log.info(String.format("get all friends to user %s", id));
        return userService.getFriends(id);
    }

    @GetMapping(path = "{id}/friends/common/{otherId}")
    public Collection<User> commonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info(String.format("add common friends for %s and %s", otherId, id));
        return userService.getCommonUsers(id, otherId);
    }
}
