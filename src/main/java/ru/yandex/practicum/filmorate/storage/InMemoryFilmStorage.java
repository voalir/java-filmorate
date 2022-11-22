package ru.yandex.practicum.filmorate.storage;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@Primary
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

    @Override
    public Collection<Film> getPopular(Integer count) {
        return super.getAll().stream().sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size()).limit(count).
                collect(Collectors.toList());
    }
}
