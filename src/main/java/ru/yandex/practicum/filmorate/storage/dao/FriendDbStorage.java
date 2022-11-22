package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FriendsPair;
import ru.yandex.practicum.filmorate.storage.FriendStorage;

import java.util.List;

@Component
public class FriendDbStorage implements FriendStorage {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static RowMapper<FriendsPair> getRowMapperFriendsPair() {
        return (rs, rowNum) -> new FriendsPair(
                rs.getInt("user_id"),
                rs.getInt("friend_id"),
                rs.getBoolean("verify"));
    }

    @Override
    public FriendsPair getCurrentFriendStatus(Integer userWhoId, Integer userWhomId) {
        String query = "select * from friends where user_id = ? and friend_id = ?" +
                "union " +
                "select * from friends where friend_id = ? and user_id = ?;";
        List<FriendsPair> friendsPairs = jdbcTemplate.query(query, getRowMapperFriendsPair(), userWhoId, userWhomId, userWhoId, userWhomId);
        if (friendsPairs.size() == 0) {
            return null;
        } else {
            return friendsPairs.get(0);
        }
    }

    @Override
    public void addRequestToFriend(int id, int friendId) {
        String query = "insert into friends(user_id ,friend_id, verify) values(? ,?, false);";
        jdbcTemplate.update(query, id, friendId);
    }

    @Override
    public void updateVerifyFriends(int id, int friendId, boolean verify) {
        String query = "update friends set verify = ? where user_id = ? and friend_id = ?;";
        jdbcTemplate.update(query, verify, id, friendId);
    }

    @Override
    public void deleteFriends(int id, int friendId) {
        String query = "delete friends where user_id = ? and friend_id = ?;";
        jdbcTemplate.update(query, id, friendId);
    }
}
