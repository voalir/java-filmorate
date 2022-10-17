package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public Film addFilm(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void removeFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.remove(film.getId());
        } else {
            throw new RuntimeException("unknown film");
        }
    }

    @Override
    public Film modifyFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        } else {
            throw new RuntimeException("unknown film");
        }
    }

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }
}
