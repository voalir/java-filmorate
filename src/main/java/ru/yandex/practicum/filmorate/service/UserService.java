package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.Collection;

@Service
public class UserService {

    @Autowired
    UserStorage userStorage;

    public User addUser(@Valid @RequestBody User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(@Valid @RequestBody User user) {
        return userStorage.modifyUser(user);
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public void deleteUser(User user) {
        userStorage.removeUser(user);
        //TODO delete him from friends
    }

    void addFriend(User userWho, User userWhom) {
        userWhom.getFriends().add(userWho.getId());
    }

    void deleteFriend(User userWho, User userFrom) {
        userFrom.getFriends().remove(userWho.getId());
    }

    Collection<User> commonUsers(User[] users) {
        return null;//TODO implement method
    }
}
