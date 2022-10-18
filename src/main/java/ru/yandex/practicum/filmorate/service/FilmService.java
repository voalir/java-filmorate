package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @Autowired
    FilmStorage filmStorage;
    @Autowired
    UserStorage userStorage;

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.modifyFilm(film);
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public void addLike(int id, int userId) {
        Film film = filmStorage.getFilm(id);
        if (userCanAddLike(film, userStorage.getUser(userId))) {
            film.getLikes().add(userId);
        }
    }

    public void deleteLike(int id, int userId) {
        Film film = filmStorage.getFilm(id);
        if (userCanDeleteLike(film, userStorage.getUser(userId))) {
            film.getLikes().remove(userId);
        }
    }

    public Collection<Film> getTopFilms(Integer count) {
        return filmStorage.getFilms().stream().sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size()).limit(count).
                collect(Collectors.toList());
    }

    boolean userCanAddLike(Film film, User user) {
        return !film.getLikes().contains(user.getId());
    }

    boolean userCanDeleteLike(Film film, User user) {
        return film.getLikes().contains(user.getId());
    }
}
