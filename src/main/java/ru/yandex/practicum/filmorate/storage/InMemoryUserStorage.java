package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public User addUser(User user) {
        validateUserName(user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void removeUser(User user) {
        if (users.containsKey(user.getId())) {
            users.remove(user.getId());
        } else {
            throw new RuntimeException("unknown film");
        }
    }

    @Override
    public User modifyUser(User user) {
        validateUserName(user);
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return user;
        } else {
            throw new RuntimeException("unknown user");
        }
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    @Override
    public User getUser(Integer id) {
        if (!users.containsKey(id)) {
            throw new InvalidIdException("user with id=" + id + " not found");
        }
        return users.get(id);
    }

    private static void validateUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
