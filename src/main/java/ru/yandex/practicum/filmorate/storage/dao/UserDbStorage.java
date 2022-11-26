package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.exception.UpdateDatabaseException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component("usersInDatabase")
public class UserDbStorage implements UserStorage {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static void processUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    private static RowMapper<User> getRowMapperUsers() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setBirthday(rs.getDate("birthday").toLocalDate());
            user.setEmail(rs.getString("email"));
            user.setLogin(rs.getString("login"));
            return user;
        };
    }

    @Override
    public User add(User user) {
        processUserName(user);
        String query = "insert into users(name, login, email, birthday) values(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, new String[]{"ID"});
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setDate(4, Date.valueOf(user.getBirthday()));
            return preparedStatement;
        }, keyHolder);
        if (keyHolder.getKey() == null) {
            throw new UpdateDatabaseException("add user failed");
        }
        user.setId((int) keyHolder.getKey());
        return user;
    }

    @Override
    public void remove(User user) {
        String query = "delete users where id = ?";
        jdbcTemplate.update(query, user.getId());
    }

    @Override
    public User modify(User user) {
        get(user.getId());
        String query = "update users set (name, login, email, birthday) = (?,?,?,?) where id = ?";
        jdbcTemplate.update(query, user.getName(), user.getLogin(), user.getEmail(), user.getBirthday(), user.getId());
        return user;
    }

    @Override
    public Collection<User> getAll() {
        String query = "select * from users";
        return new ArrayList<>(jdbcTemplate.query(query, getRowMapperUsers()));
    }

    @Override
    public User get(int id) {
        String query = "select * from users where id = ?";
        List<User> userList = jdbcTemplate.query(query, getRowMapperUsers(), id);
        if (userList.size() == 1) {
            return userList.get(0);
        } else {
            throw new InvalidIdException("user with id=" + id + " not found.");
        }
    }

    @Override
    public Collection<User> getFriends(int id) {
        String query = "select users.* from friends join users on friends.friend_id = users.id where user_id = ? ";
        return new ArrayList<>(jdbcTemplate.query(query, getRowMapperUsers(), id));
    }

    @Override
    public Collection<User> getCommonFriends(int userId, int otherId) {
        String query = "select users.* from users join (select f1.friend_id from friends f1 " +
                "join friends f2 on f1.friend_id = f2.friend_id " +
                "where f1.user_id = ? and f2.user_id = ?) common_friend_ids on common_friend_ids.friend_id = users.id";
        return new ArrayList<>(jdbcTemplate.query(query, getRowMapperUsers(), userId, otherId));
    }
}
