package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.FriendStorage;

@Component
public class FriendDbStorage implements FriendStorage {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void addRequestToFriend(int id, int friendId) {
        String query = "insert into friends(user_id, friend_id) values(? ,?);";
        jdbcTemplate.update(query, id, friendId);
    }

    @Override
    public void deleteFriends(int id, int friendId) {
        String query = "delete friends where user_id = ? and friend_id = ?;";
        jdbcTemplate.update(query, id, friendId);
    }
}
