package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    public void addFriend(int id, int friendId) {
        User user = userStorage.getUser(id);
        if (userCanAddFriend(user, userStorage.getUser(friendId))) {
            user.getFriends().add(friendId);
        }
    }

    public void deleteFriend(int id, int friendId) {
        User user = userStorage.getUser(id);
        if (userCanDeleteFriend(user, userStorage.getUser(friendId))) {
            user.getFriends().remove(friendId);
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
}
