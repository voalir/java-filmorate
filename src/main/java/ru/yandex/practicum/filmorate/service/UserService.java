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
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        return userStorage.modifyUser(user);
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public void addFriend(int id, int friendId) {
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);
        if (userCanAddFriend(user, friend)) {
            user.getFriends().add(friendId);
            friend.getFriends().add(id);
        }
    }

    public void deleteFriend(int id, int friendId) {
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);
        if (userCanDeleteFriend(user, friend)) {
            user.getFriends().remove(friendId);
            friend.getFriends().remove(id);
        }
    }

    public Collection<User> getFriends(int id) {
        List<User> friends = new ArrayList<>();
        for (Integer userId : userStorage.getUser(id).getFriends()) {
            friends.add(userStorage.getUser(userId));
        }
        return friends;
    }

    public Collection<User> getCommonUsers(int id, int otherId) {
        User otherUser = userStorage.getUser(otherId);
        List<Integer> commonFriendIds = userStorage.getUser(id).getFriends().stream().filter((s) ->
                otherUser.getFriends().contains(s)).collect(Collectors.toList());
        List<User> commonFriends = new ArrayList<>();
        for (Integer userId : commonFriendIds) {
            commonFriends.add(userStorage.getUser(userId));
        }
        return commonFriends;
    }

    boolean userCanAddFriend(User user, User friend) {
        return !user.getFriends().contains(friend.getId());
    }

    boolean userCanDeleteFriend(User user, User friend) {
        return user.getFriends().contains(friend.getId());
    }

    public User getUserById(int id) {
        return userStorage.getUser(id);
    }
}
