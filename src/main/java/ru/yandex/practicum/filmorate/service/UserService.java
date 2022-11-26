package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Service
public class UserService {

    @Autowired
    @Qualifier("usersInDatabase")
    private UserStorage userStorage;

    @Autowired
    private FriendStorage friendStorage;

    public User addUser(User user) {
        return userStorage.add(user);
    }

    public User updateUser(User user) {
        return userStorage.modify(user);
    }

    public Collection<User> getUsers() {
        return userStorage.getAll();
    }

    public void addFriend(int id, int friendId) {
        try {
            friendStorage.addRequestToFriend(id, friendId);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidIdException("user id invalid");
        }
    }

    public void deleteFriend(int id, int friendId) {
        friendStorage.deleteFriends(id, friendId);
    }

    public Collection<User> getFriends(int id) {
        return userStorage.getFriends(id);
    }

    public Collection<User> getCommonFriends(int userId, int otherId) {
        return userStorage.getCommonFriends(userId, otherId);
    }

    public User getUserById(int id) {
        return userStorage.get(id);
    }
}
