package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenresStorage {
    List<Genre> getFilmGenres(Integer id);

    void updateFilmGenres(Integer id, List<Integer> genres);

    List<Genre> getGenres();

    Genre getGenreById(Integer id);

}
