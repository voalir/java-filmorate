package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage extends ObjectStorage<User> {
    Collection<User> getFriends(int id);

    Collection<User> getCommonFriends(int userId, int otherId);

}
