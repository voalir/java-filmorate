package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenresStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class GenresDbStorage implements GenresStorage {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getFilmGenres(Integer id) {
        String query = "select genres.* from film_genre join genres on genres.id = film_genre.genre_id where film_id = ?";
        return jdbcTemplate.query(query, getRowMapperGenres(), id);
    }

    @Override
    public void updateFilmGenres(Integer filmId, List<Integer> genreIds) {
        String queryClearGenre = "delete from film_genre where film_id = ?;";
        String query = "insert into film_genre(genre_id, film_id) values(?, ?)";
        jdbcTemplate.update(queryClearGenre, filmId);
        for (Integer genreId : new ArrayList<>(new HashSet<>(genreIds))) {
            jdbcTemplate.update(query, genreId, filmId);
        }
    }

    @Override
    public List<Genre> getGenres() {
        String query = "select * from genres";
        return jdbcTemplate.query(query, getRowMapperGenres());
    }

    @Override
    public Genre getGenreById(Integer id) {
        String query = "select * from genres where id = ?";
        List<Genre> genres = jdbcTemplate.query(query, getRowMapperGenres(), id);
        if (genres.size() == 1) {
            return genres.get(0);
        } else {
            throw new InvalidIdException("Genre with id=" + id + " not found");
        }
    }

    private RowMapper<Genre> getRowMapperGenres() {
        return (rs, rowNum) -> {
            Genre genre = new Genre();
            genre.setId(rs.getInt("id"));
            genre.setName(rs.getString("name"));
            return genre;
        };
    }
}
