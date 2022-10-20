package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

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

    @GetMapping(path = "/{id}")
    public Film getFilm(@PathVariable int id) {
        return filmService.getFilmById(id);
    }

    @PutMapping(path = "/{id}/like/{userId}")
    public void likeFilm(@PathVariable int id, @PathVariable int userId) {
        log.info(String.format("add like to film with id=%s from user with id=%s", id, userId));
        filmService.addLike(id, userId);
    }

    @DeleteMapping(path = "/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        log.info(String.format("delete like to film with id=%s from user with id=%s", id, userId));
        filmService.deleteLike(id, userId);
    }

    @GetMapping(path = "/popular")
    public Collection<Film> getPopularFilms(@RequestParam(required = false, defaultValue = "10") Integer count) {
        log.info(String.format("get %s popular films", count));
        return filmService.getTopFilms(count);
    }
}
