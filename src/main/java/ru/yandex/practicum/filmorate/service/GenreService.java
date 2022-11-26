package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class GenreService {

    @Autowired
    GenreStorage genreStorage;

    public Collection<Genre> getGenres() {
        return genreStorage.getGenres();
    }

    public Genre getGenreById(int id) {
        return genreStorage.getGenreById(id);
    }

    public List<Genre> getFilmGenres(Integer id) {
        return genreStorage.getFilmGenres(id);
    }

    public void updateFilmGenres(Integer id, List<Integer> genres) {
        genreStorage.updateFilmGenres(id, genres);
    }

    public Map<Integer, List<Genre>> getFilmGenres() {
        return genreStorage.getFilmGenres();
    }

    public Map<Integer, List<Genre>> getFilmGenresForPopular(int count) {
        return genreStorage.getFilmGenresForPopular(count);
    }
}
