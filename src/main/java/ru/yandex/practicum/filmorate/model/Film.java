package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.validator.AfterDate;

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


}
