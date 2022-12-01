package ru.yandex.practicum.filmorate.storage.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.*;

@Component
public class GenreDbStorage implements GenreStorage {

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

    @Override
    public Map<Integer, List<Genre>> getFilmGenres() {
        String query = "select * from film_genre join genres on genres.id = film_genre.genre_id";
        List<FilmGenre> filmGenreList = jdbcTemplate.query(query, getRowMapperFilmGenres());
        Map<Integer, List<Genre>> filmGenres = new HashMap<>();
        for (FilmGenre filmGenre : filmGenreList) {
            if (!filmGenres.containsKey(filmGenre.getFilmId())) {
                filmGenres.put(filmGenre.getFilmId(), new ArrayList<>());
            }
            filmGenres.get(filmGenre.getFilmId()).add(filmGenre.getGenre());
        }
        return filmGenres;
    }

    @Override
    public Map<Integer, List<Genre>> getFilmGenresForPopular(int count) {
        String query = "select * from film_genre join genres on genres.id = film_genre.genre_id " +
                "join (select films.id film_id from films left join likes on films.id = likes.film_id " +
                "group by films.id limit ? ) popular_films on film_genre.film_id = popular_films.film_id";
        List<FilmGenre> filmGenreList = jdbcTemplate.query(query, getRowMapperFilmGenres(), count);
        Map<Integer, List<Genre>> filmGenres = new HashMap<>();
        for (FilmGenre filmGenre : filmGenreList) {
            if (!filmGenres.containsKey(filmGenre.getFilmId())) {
                filmGenres.put(filmGenre.getFilmId(), new ArrayList<>());
            }
            filmGenres.get(filmGenre.getFilmId()).add(filmGenre.getGenre());
        }
        return filmGenres;
    }

    private RowMapper<FilmGenre> getRowMapperFilmGenres() {
        return (rs, rowNum) -> {
            Genre genre = new Genre();
            genre.setId(rs.getInt("genre_id"));
            genre.setName(rs.getString("name"));
            return new FilmGenre(rs.getInt("film_id"), genre);
        };
    }

    private RowMapper<Genre> getRowMapperGenres() {
        return (rs, rowNum) -> {
            Genre genre = new Genre();
            genre.setId(rs.getInt("id"));
            genre.setName(rs.getString("name"));
            return genre;
        };
    }

    @Getter
    @AllArgsConstructor
    private static class FilmGenre {
        Integer filmId;
        Genre genre;
    }
}
