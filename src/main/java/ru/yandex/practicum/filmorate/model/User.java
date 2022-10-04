package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    Integer id = 1;//целочисленный идентификатор
    @Email
    String email;//электронная почта
    @NotBlank
    String login;//логин пользователя
    String name = "common";//имя для отображения
    @Past
    LocalDate birthday;// дата рождения
}
