package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.FriendsPair;

public interface FriendStorage {
    FriendsPair getCurrentFriendStatus(Integer userWhoId, Integer userWhomId);

    void addRequestToFriend(int id, int friendId);

    void updateVerifyFriends(int id, int friendId, boolean verify);

    void deleteFriends(int id, int friendId);

}
