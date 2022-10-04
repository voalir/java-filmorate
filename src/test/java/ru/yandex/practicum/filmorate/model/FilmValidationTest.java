package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmValidationTest {

    Film createFilm(Integer id, String name, LocalDate releaseDate, String description, Long duration) {
        Film film = new Film();
        film.setId(id);
        film.setName(name);
        film.setReleaseDate(releaseDate);
        film.setDescription(description);
        film.setDuration(duration);
        if (Film.validate(film)) {
            return film;
        }
        return null;
    }

    @Test
    @DisplayName("Создать фильм - корректные данные")
    void createCorrectFilm() {
        assertDoesNotThrow(() -> createFilm(10, "film", LocalDate.of(2000, 10, 10),
                "good film", 110L));
    }

    @Test
    @DisplayName("Создать фильм - отрицательный ид")
    void createFilmIdLessZero() {
        assertThrows(ValidationException.class, () -> createFilm(-10, "film", LocalDate.of(2000, 10, 10),
                "good film", 110L));
    }

    @Test
    @DisplayName("Создать фильм - id=0 ")
    void createFilmIdZero() {
        assertDoesNotThrow(() -> createFilm(0, "film", LocalDate.of(2000, 10, 10),
                "good film", 110L));
    }

    @Test
    @DisplayName("Создать фильм - id=1")
    void createFilmIdOne() {
        assertDoesNotThrow(() -> createFilm(1, "film", LocalDate.of(2000, 10, 10),
                "good film", 110L));
    }

    @Test
    @DisplayName("Создать фильм - пустое имя")
    void createFilmNameIsBlank() {
        assertThrows(ValidationException.class, () -> createFilm(10, "", LocalDate.of(2000, 10, 10),
                "good film", 110L));
    }

    @Test
    @DisplayName("Создать фильм - описание 199 символов")
    void createFilmDescription199() {
        assertDoesNotThrow(() -> createFilm(10, "film", LocalDate.of(2000, 10, 10),
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", 110L));
    }

    @Test
    @DisplayName("Создать фильм - описание 200 символов")
    void createFilmDescription200() {
        assertDoesNotThrow(() -> createFilm(10, "film", LocalDate.of(2000, 10, 10),
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", 110L));
    }

    @Test
    @DisplayName("Создать фильм - описание 201 символ")
    void createFilmDescription201() {
        assertThrows(ValidationException.class, () -> createFilm(10, "film", LocalDate.of(2000, 10, 10),
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", 110L));
    }

    @Test
    @DisplayName("Создать фильм - релиз до 28.12.1895")
    void createFilmRealizeBefore28Dec1895() {
        assertThrows(ValidationException.class, () -> createFilm(10, "film", LocalDate.of(1895, 11, 27),
                "good film", 110L));
    }

    @Test
    @DisplayName("Создать фильм - релиз 28.12.1895")
    void createFilmRealizeEquals28Dec1895() {
        assertDoesNotThrow(() -> createFilm(10, "film", LocalDate.of(1895, 11, 28),
                "good film", 110L));
    }

    @Test
    @DisplayName("Создать фильм - релиз после 28.12.1895")
    void createFilmRealizeAfter28Dec1895() {
        assertDoesNotThrow(() -> createFilm(10, "film", LocalDate.of(1895, 11, 29),
                "good film", 110L));
    }

    @Test
    @DisplayName("Создать фильм - продолжительность нулевая")
    void createFilmDurationZero() {
        assertDoesNotThrow(() -> createFilm(10, "film", LocalDate.of(2000, 10, 10),
                "good film", 0L));
    }

    @Test
    @DisplayName("Создать фильм - продолжительность отрицательная")
    void createFilmDurationNegative() {
        assertThrows(ValidationException.class, () -> createFilm(10, "film", LocalDate.of(2000, 10, 10),
                "good film", -1L));
    }

}