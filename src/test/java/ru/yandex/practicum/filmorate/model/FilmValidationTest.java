package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilmValidationTest {

    static Validator validator;

    @BeforeAll
    static void prepareValidator() {
        validator = Validation.buildDefaultValidatorFactory().usingContext().getValidator();
    }

    Film createFilm(Integer id, String name, LocalDate releaseDate, String description, Long duration) {
        Film film = new Film();
        film.setId(id);
        film.setName(name);
        film.setReleaseDate(releaseDate);
        film.setDescription(description);
        film.setDuration(duration);
        return film;
    }

    @Test
    @DisplayName("Создать фильм - корректные данные")
    void createCorrectFilm() {

        assertEquals(0, validator.validate(createFilm(10, "film", LocalDate.of(2000, 10, 10),
                "good film", 110L)).size());
    }

    @Test
    @DisplayName("Создать фильм - пустое имя")
    void createFilmNameIsBlank() {
        assertEquals(1, validator.validate(createFilm(1, "", LocalDate.of(2000, 10, 10),
                "good film", 110L)).size());
    }

    @Test
    @DisplayName("Создать фильм - описание 199 символов")
    void createFilmDescription199() {
        assertEquals(0, validator.validate(createFilm(10, "film", LocalDate.of(2000, 10, 10),
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", 110L)).size());
    }

    @Test
    @DisplayName("Создать фильм - описание 200 символов")
    void createFilmDescription200() {
        assertEquals(0, validator.validate(createFilm(10, "film", LocalDate.of(2000, 10, 10),
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", 110L)).size());
    }

    @Test
    @DisplayName("Создать фильм - описание 201 символ")
    void createFilmDescription201() {
        assertEquals(1, validator.validate(createFilm(10, "film", LocalDate.of(2000, 10, 10),
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", 110L)).size());
    }

    @Test
    @DisplayName("Создать фильм - релиз до 28.12.1895")
    void createFilmRealizeBefore28Dec1895() {
        assertEquals(1, validator.validate(createFilm(10, "film", LocalDate.of(1895, 12, 27),
                "good film", 110L)).size());
    }

    @Test
    @DisplayName("Создать фильм - релиз 28.12.1895")
    void createFilmRealizeEquals28Dec1895() {
        assertEquals(0, validator.validate(createFilm(10, "film", LocalDate.of(1895, 12, 28),
                "good film", 110L)).size());
    }

    @Test
    @DisplayName("Создать фильм - релиз после 28.12.1895")
    void createFilmRealizeAfter28Dec1895() {
        assertEquals(0, validator.validate(createFilm(10, "film", LocalDate.of(1895, 12, 29),
                "good film", 110L)).size());
    }

    @Test
    @DisplayName("Создать фильм - продолжительность нулевая")
    void createFilmDurationZero() {
        assertEquals(1, validator.validate(createFilm(10, "film", LocalDate.of(2000, 10, 10),
                "good film", 0L)).size());
    }

    @Test
    @DisplayName("Создать фильм - продолжительность отрицательная")
    void createFilmDurationNegative() {
        assertEquals(1, validator.validate(createFilm(10, "film", LocalDate.of(2000, 10, 10),
                "good film", -1L)).size());
    }

    @Test
    @DisplayName("Создать фильм - все значения = NULL")
    void createFilmAllValuesNull() {
        assertEquals(2, validator.validate(createFilm(null, null, null,
                null, null)).size());
    }

    @Test
    @DisplayName("Создать фильм - id = NULL")
    void createFilmIdNull() {
        assertEquals(0, validator.validate(createFilm(null, "film", LocalDate.of(2000, 10, 10),
                "good film", 110L)).size());
    }

    @Test
    @DisplayName("Создать фильм - название = NULL")
    void createFilmNameNull() {
        assertEquals(1, validator.validate(createFilm(10, null, LocalDate.of(2000, 10, 10),
                "good film", 110L)).size());
    }

    @Test
    @DisplayName("Создать фильм - дата релиза = NULL")
    void createFilmRealizeDateNull() {
        assertEquals(0, validator.validate(createFilm(10, "film", null,
                "good film", 110L)).size());
    }

    @Test
    @DisplayName("Создать фильм - описание = NULL")
    void createFilmDescriptionNull() {
        assertEquals(0, validator.validate(createFilm(10, "film", LocalDate.of(2000, 10, 10),
                null, 110L)).size());
    }

    @Test
    @DisplayName("Создать фильм - продолжительность = NULL")
    void createFilmDurationNull() {
        assertEquals(1, validator.validate(createFilm(10, "film", LocalDate.of(2000, 10, 10),
                "good film", null)).size());
    }
}