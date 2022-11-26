package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Map;

public interface GenreStorage {
    List<Genre> getFilmGenres(Integer id);

    void updateFilmGenres(Integer id, List<Integer> genres);

    List<Genre> getGenres();

    Genre getGenreById(Integer id);

    Map<Integer, List<Genre>> getFilmGenres();

    Map<Integer, List<Genre>> getFilmGenresForPopular(int count);
}
