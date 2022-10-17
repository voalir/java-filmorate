package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Film addFilm(Film film);

    void removeFilm(Film film);

    Film modifyFilm(Film film);

    Collection<Film> getFilms();
}
