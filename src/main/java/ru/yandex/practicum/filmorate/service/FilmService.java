package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FilmService {

    public static final int COUNT_TOP_FILMS_BY_DEFAULT = 10;

    @Autowired
    FilmStorage filmStorage;

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.modifyFilm(film);
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    void addLike(Film film, User user) {
        film.getLikes().add(user.getId());
    }

    void deleteLike(Film film, User user) {
        film.getLikes().remove(user.getId());
    }

    Collection<Film> getTopFilms() {
        return filmStorage.getFilms().stream().sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size()).limit(COUNT_TOP_FILMS_BY_DEFAULT).
                collect(Collectors.toList());
    }
}
