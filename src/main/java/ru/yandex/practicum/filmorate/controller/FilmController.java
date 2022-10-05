package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    HashMap<Integer, Film> films = new HashMap<>();

    @PostMapping
    public Film addUser(@Valid @RequestBody Film film) {
        log.info("add film: " + film.toString());
        validate(film);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film updateUser(@Valid @RequestBody Film film) {
        log.info("update film:  " + film.toString());
        validate(film);
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        } else {
            throw new RuntimeException("unknown film");
        }
    }

    @GetMapping
    public Collection<Film> getUsers() {
        return films.values();
    }

    public static Boolean validate(Film film) {
        if (film.getId() < 0) {
            throw new ValidationException("id less zero");
        }
        if (film.getDescription().length() < 1 || film.getDescription().length() > 200) {
            throw new ValidationException("description length is incorrect");
        }
        if (film.getName().length() < 1) {
            throw new ValidationException("name is blank");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 11, 28))) {
            throw new ValidationException("realize date before 1895-12-28");
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("film duration less 0");
        }
        return true;
    }
}
