package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import javax.validation.constraints.*;
import java.time.Duration;
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
    LocalDate releaseDate;//дата релиза
    @Positive
    Long duration;//продолжительность фильма

}
