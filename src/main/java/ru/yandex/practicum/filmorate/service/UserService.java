package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.FriendsPair;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    @Qualifier("usersInDatabase")
    private UserStorage userStorage;
    private Integer lastIdentifier = 0;
    @Autowired
    private FriendStorage friendStorage;

    private Integer getId() {
        return ++lastIdentifier;
    }

    public User addUser(User user) {
        user.setId(getId());
        return userStorage.add(user);
    }

    public User updateUser(User user) {
        return userStorage.modify(user);
    }

    public Collection<User> getUsers() {
        return userStorage.getAll();
    }

    public void addFriend(int id, int friendId) {
        User user = userStorage.get(id);
        User friend = userStorage.get(friendId);
        FriendsPair currentFriendPair = friendStorage.getCurrentFriendStatus(id, friendId);
        if (currentFriendPair == null) {//связи еще нет
            friendStorage.addRequestToFriend(user.getId(), friend.getId());
        } else if (Objects.equals(currentFriendPair.getUserId(), friend.getId())) {//связь есть, подтверждение
            friendStorage.updateVerifyFriends(friend.getId(), user.getId(), true);
        }
    }

    public void deleteFriend(int id, int friendId) {
        User user = userStorage.get(id);
        User friend = userStorage.get(friendId);
        friendStorage.deleteFriends(user.getId(), friend.getId());
    }

    public Collection<User> getFriends(int id) {
        return userStorage.getFriends(id);
    }

    public Collection<User> getCommonFriends(int userId, int otherId) {
        return userStorage.getFriends(userId).stream().filter(userStorage.getFriends(otherId)::contains).collect(Collectors.toList());
    }

    public User getUserById(int id) {
        return userStorage.get(id);
    }
}
