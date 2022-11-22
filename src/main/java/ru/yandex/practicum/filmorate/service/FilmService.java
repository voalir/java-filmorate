package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikesStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Service
public class FilmService {

    @Autowired
    @Qualifier("filmsInDatabase")
    private FilmStorage filmStorage;

    @Autowired
    @Qualifier("usersInDatabase")
    private UserStorage userStorage;

    @Autowired
    private LikesStorage likesStorage;

    private Integer lastIdentifier = 0;

    private Integer getId() {
        return ++lastIdentifier;
    }

    public Film addFilm(Film film) {
        film.setId(getId());
        return filmStorage.add(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.modify(film);
    }

    public Collection<Film> getFilms() {
        return filmStorage.getAll();
    }

    public void addLike(int id, int userId) {
        Film film = filmStorage.get(id);
        User user = userStorage.get(userId);
        likesStorage.addLike(user.getId(), film.getId());
    }

    public void deleteLike(int id, int userId) {
        Film film = filmStorage.get(id);
        User user = userStorage.get(userId);
        likesStorage.deleteLike(user.getId(), film.getId());
    }

    public Collection<Film> getTopFilms(Integer count) {
        return filmStorage.getPopular(count);
    }

    private boolean userCanAddLike(Film film, User user) {
        return !film.getLikes().contains(user.getId());
    }

    private boolean userCanDeleteLike(Film film, User user) {
        return film.getLikes().contains(user.getId());
    }

    public Film getFilmById(int id) {
        return filmStorage.get(id);
    }
}
