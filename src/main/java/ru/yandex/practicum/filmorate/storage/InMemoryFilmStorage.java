package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.model.Film;

@Component
public class InMemoryFilmStorage extends InMemoryObjectStorage<Film> implements FilmStorage {

    @Override
    public Film add(Film film) {
        super.putObject(film.getId(), film);
        return film;
    }

    @Override
    public void remove(Film film) {
        if (super.containsObjectKey(film.getId())) {
            super.removeObject(film.getId());
        } else {
            throw new InvalidIdException("film with id=" + film.getId() + " not found");
        }
    }

    @Override
    public Film modify(Film film) {
        if (super.containsObjectKey(film.getId())) {
            super.putObject(film.getId(), film);
            return film;
        } else {
            throw new InvalidIdException("film with id=" + film.getId() + " not found");
        }
    }

}
