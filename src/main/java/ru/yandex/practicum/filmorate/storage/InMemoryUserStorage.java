package ru.yandex.practicum.filmorate.storage;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Primary
public class InMemoryUserStorage extends InMemoryObjectStorage<User> implements UserStorage {

    @Override
    public User add(User user) {
        processUserName(user);
        super.putObject(user.getId(), user);
        return user;
    }

    @Override
    public void remove(User user) {
        if (super.containsObjectKey(user.getId())) {
            super.removeObject(user.getId());
        } else {
            throw new InvalidIdException("user with id=" + user.getId() + " not found");
        }
    }

    @Override
    public User modify(User user) {
        processUserName(user);
        if (super.containsObjectKey(user.getId())) {
            super.putObject(user.getId(), user);
            return user;
        } else {
            throw new InvalidIdException("user with id=" + user.getId() + " not found");
        }
    }

    private static void processUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    @Override
    public Collection<User> getFriends(int id) {
        List<User> friends = new ArrayList<>();
        for (Integer userId : super.get(id).getFriends()) {
            friends.add(super.get(userId));
        }
        return friends;
    }
}
