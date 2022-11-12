package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;
    private Integer lastIdentifier;

    @Autowired
    public UserService(UserStorage userStorage) {
        lastIdentifier = 0;
        this.userStorage = userStorage;
    }

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
        if (userCanAddFriend(user, friend)) {
            user.getFriends().add(friendId);
            friend.getFriends().add(id);
        }
    }

    public void deleteFriend(int id, int friendId) {
        User user = userStorage.get(id);
        User friend = userStorage.get(friendId);
        if (userCanDeleteFriend(user, friend)) {
            user.getFriends().remove(friendId);
            friend.getFriends().remove(id);
        }
    }

    public Collection<User> getFriends(int id) {
        List<User> friends = new ArrayList<>();
        for (Integer userId : userStorage.get(id).getFriends()) {
            friends.add(userStorage.get(userId));
        }
        return friends;
    }

    public Collection<User> getCommonFriends(int userId, int otherId) {
        User otherUser = userStorage.get(otherId);
        List<Integer> commonFriendIds = userStorage.get(userId).getFriends().stream().filter((s) ->
                otherUser.getFriends().contains(s)).collect(Collectors.toList());
        List<User> commonFriends = new ArrayList<>();
        for (Integer userIdFriend : commonFriendIds) {
            commonFriends.add(userStorage.get(userIdFriend));
        }
        return commonFriends;
    }

    private boolean userCanAddFriend(User user, User friend) {
        return !user.getFriends().contains(friend.getId());
    }

    private boolean userCanDeleteFriend(User user, User friend) {
        return user.getFriends().contains(friend.getId());
    }

    public User getUserById(int id) {
        return userStorage.get(id);
    }
}
