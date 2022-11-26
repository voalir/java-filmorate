package ru.yandex.practicum.filmorate.storage;

public interface FriendStorage {

    void addRequestToFriend(int id, int friendId);

    void deleteFriends(int id, int friendId);

}
