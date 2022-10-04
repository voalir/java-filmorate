package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        Film.validate(film);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film updateUser(@Valid @RequestBody Film film) {
        log.info("update film:  " + film.toString());
        Film.validate(film);
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
}
