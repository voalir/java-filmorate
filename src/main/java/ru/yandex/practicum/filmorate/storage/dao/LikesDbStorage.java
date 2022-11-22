package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.LikesStorage;

@Component
public class LikesDbStorage implements LikesStorage {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void addLike(Integer userId, Integer filmId) {
        String query = "insert into likes (user_id, film_id) values(?,?)";
        jdbcTemplate.update(query, userId, filmId);
    }

    @Override
    public void deleteLike(Integer userId, Integer filmId) {
        String query = "delete from likes where user_id = ? and film_id = ?";
        jdbcTemplate.update(query, userId, filmId);
    }
}
