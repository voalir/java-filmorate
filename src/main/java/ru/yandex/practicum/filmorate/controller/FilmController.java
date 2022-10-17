package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    @Autowired
    FilmService filmService;

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("add film: " + film.toString());
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("update film:  " + film.toString());
        return filmService.updateFilm(film);
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return filmService.getFilms();
    }

}
