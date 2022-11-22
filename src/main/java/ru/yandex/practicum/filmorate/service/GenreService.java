package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenresStorage;

import java.util.Collection;
import java.util.List;

@Service
public class GenreService {

    @Autowired
    GenresStorage genresStorage;

    public Collection<Genre> getGenres() {
        return genresStorage.getGenres();
    }

    public Genre getGenreById(int id) {
        return genresStorage.getGenreById(id);
    }

    public List<Genre> getFilmGenres(Integer id) {
        return genresStorage.getFilmGenres(id);
    }

    public void updateFilmGenres(Integer id, List<Integer> genres) {
        genresStorage.updateFilmGenres(id, genres);
    }
}
