package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validator.AfterDate;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class Film {

    /**
     * целочисленный идентификатор
     */
    private Integer id = 1;

    /**
     * название
     */
    @Size(min = 1)
    private String name;

    /**
     * описание
     */
    @Size(min = 1, max = 200)
    private String description;

    /**
     * дата релиза
     */
    @AfterDate
    private LocalDate releaseDate;

    /**
     * продолжительность фильма
     */
    @Positive
    private Long duration;
}
