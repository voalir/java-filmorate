package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

import java.util.List;

@Component
public class MpaRatingDbStorage implements MpaRatingStorage {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<MpaRating> getMpaRatings() {
        String query = "select * from mpa_ratings";
        return jdbcTemplate.query(query, getRowMapperMpaRating());
    }

    @Override
    public MpaRating getMpaRatingById(Integer id) {
        String query = "select * from mpa_ratings where id = ?";
        List<MpaRating> mpaRatings = jdbcTemplate.query(query, getRowMapperMpaRating(), id);
        if (mpaRatings.size() == 1) {
            return mpaRatings.get(0);
        } else {
            throw new InvalidIdException("MPA rating with id=" + id + " not found");
        }
    }

    private RowMapper<MpaRating> getRowMapperMpaRating() {
        return (rs, rowNum) -> {
            MpaRating mpaRating = new MpaRating();
            mpaRating.setId(rs.getInt("id"));
            mpaRating.setName(rs.getString("name"));
            return mpaRating;
        };
    }
}
