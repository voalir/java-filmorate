package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {


    FilmController filmController;
    Film createFilm(Integer id, String name, LocalDate releaseDate, String description, Long duration) {
        Film film = new Film();
        film.setId(id);
        film.setName(name);
        film.setReleaseDate(releaseDate);
        film.setDescription(description);
        film.setDuration(duration);
        return film;
    }

    @BeforeEach
    void setUp() {
        filmController = new FilmController();
    }

    @Test
    void addFilm() throws IOException, InterruptedException {
        assertDoesNotThrow(() -> filmController.addFilm(createFilm(10, "film", LocalDate.of(2000, 10, 10),
                "good film", 110L)));
        assertThrows(ValidationException.class, () -> filmController.addFilm(createFilm(-10, "film", LocalDate.of(2000, 10, 10),
                "good film", 110L)));
        assertThrows(NullPointerException.class, () -> filmController.addFilm(null));
    }
}