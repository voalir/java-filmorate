package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
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
            throw new InvalidIdException("film with id=" + film.getId() + " not found");
        }
    }

    @Override
    public Film modifyFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        } else {
            throw new InvalidIdException("film with id=" + film.getId() + " not found");
        }
    }

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }

    @Override
    public Film getFilm(int id) {
        if (!films.containsKey(id)) {
            throw new InvalidIdException("film with id=" + id + " not found");
        }
        return films.get(id);
    }
}
