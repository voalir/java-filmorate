package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.exception.UpdateDatabaseException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Guide;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("filmsInDatabase")
public class FilmDbStorage implements FilmStorage {

    private static final String QUERY_SELECT_FILMS = "select films.*, mpa_ratings.name mpa_rating_name " +
            "from films join mpa_ratings on films.mpa_rating_id = mpa_ratings.id";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    GenreService genreService;

    @Override
    public Film add(Film film) {
        String query = "insert into films(name, description, release_date, duration, mpa_rating_id) values(?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, new String[]{"ID"});
            preparedStatement.setString(1, film.getName());
            preparedStatement.setString(2, film.getDescription());
            preparedStatement.setDate(3, Date.valueOf(film.getReleaseDate()));
            preparedStatement.setLong(4, film.getDuration());
            preparedStatement.setInt(5, film.getMpa().getId());
            return preparedStatement;
        }, keyHolder);
        if (keyHolder.getKey() == null) {
            throw new UpdateDatabaseException("add film failed");
        }
        film.setId((int) keyHolder.getKey());
        genreService.updateFilmGenres(film.getId(), film.getGenres().stream().map(Guide::getId).collect(Collectors.toList()));
        return film;
    }

    @Override
    public void remove(Film film) {
        String query = "delete films where id = ?";
        jdbcTemplate.update(query, film.getId());
    }

    @Override
    public Film modify(Film film) {
        String query = "update films set (name, description, release_date, duration, mpa_rating_id) = (?,?,?,?,?) where id = ?";
        if (jdbcTemplate.update(query, film.getName(), film.getDescription(), film.getReleaseDate()
                , film.getDuration(), film.getMpa().getId(), film.getId()) == 0) {
            throw new InvalidIdException("film with id=" + film.getId() + " not found");
        }
        genreService.updateFilmGenres(film.getId(), film.getGenres().stream().map(Guide::getId).collect(Collectors.toList()));
        return get(film.getId());
    }

    @Override
    public Collection<Film> getAll() {
        List<Film> films = new ArrayList<>(jdbcTemplate.query(QUERY_SELECT_FILMS, getRowMapperFilms()));
        Map<Integer, List<Genre>> filmGenres = genreService.getFilmGenres();
        for (Film film : films) {
            film.setGenres(filmGenres.getOrDefault(film.getId(), new ArrayList<>()));
        }
        return films;
    }

    @Override
    public Film get(int id) {
        String query = QUERY_SELECT_FILMS + " where films.id = ?";
        List<Film> filmList = jdbcTemplate.query(query, getRowMapperFilms(), id);
        if (filmList.size() == 1) {
            Film film = filmList.get(0);
            film.setGenres(genreService.getFilmGenres(film.getId()));
            return film;
        } else {
            throw new InvalidIdException("film with id=" + id + " not found.");
        }
    }

    @Override
    public Collection<Film> getPopular(Integer count) {
        String query = "select films.*, mpa_ratings.name mpa_rating_name from films " +
                "left join likes on films.id = likes.film_id " +
                "join mpa_ratings on films.mpa_rating_id = mpa_ratings.id " +
                "group by films.id " +
                "order by count(likes.user_id) desc " +
                "limit ?";
        List<Film> filmList = jdbcTemplate.query(query, getRowMapperFilms(), count);
        Map<Integer, List<Genre>> filmGenres = genreService.getFilmGenresForPopular(count);
        for (Film film : filmList) {
            film.setGenres(filmGenres.getOrDefault(film.getId(), new ArrayList<>()));
        }
        return filmList;
    }

    private RowMapper<Film> getRowMapperFilms() {
        return (rs, rowNum) -> {
            Film film = new Film();
            film.setId(rs.getInt("id"));
            film.setName(rs.getString("name"));
            film.setReleaseDate(rs.getDate("release_date").toLocalDate());
            film.setDescription(rs.getString("description"));
            MpaRating mpaRating = new MpaRating();
            mpaRating.setId(rs.getInt("mpa_rating_id"));
            mpaRating.setName(rs.getString("mpa_rating_name"));
            film.setMpa(mpaRating);
            film.setDuration(rs.getLong("duration"));
            return film;
        };
    }


}
