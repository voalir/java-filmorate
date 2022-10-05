package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    private Integer id = 1;//целочисленный идентификатор
    @Email
    private String email;//электронная почта
    @NotBlank
    private String login;//логин пользователя
    private String name = "common";//имя для отображения
    @Past
    private LocalDate birthday;// дата рождения
}
