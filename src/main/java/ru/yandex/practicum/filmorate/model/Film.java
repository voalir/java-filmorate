package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validator.AfterDate;

import javax.validation.ValidationException;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class Film {
    @NotNull
    Integer id = 1; //целочисленный идентификатор
    @Size(min = 1)
    String name;//название
    @Size(min = 1, max = 200)
    String description;//описание
    //TODO validate after date
    @AfterDate
    LocalDate releaseDate;//дата релиза
    @Positive
    Long duration;//продолжительность фильма

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
