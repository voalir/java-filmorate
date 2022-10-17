package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    User addUser(User user);

    void removeUser(User user);

    User modifyUser(User user);

    Collection<User> getUsers();
}
