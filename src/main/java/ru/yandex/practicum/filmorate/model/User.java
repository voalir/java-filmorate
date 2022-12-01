package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class User {

    /**
     * целочисленный идентификатор
     */
    private Integer id = 1;

    /**
     * электронная почта
     */
    @Email
    @NotBlank
    private String email;

    /**
     * логин пользователя
     */
    @NotBlank
    private String login;

    /**
     * имя для отображения
     */
    private String name;

    /**
     * дата рождения
     */
    @PastOrPresent
    private LocalDate birthday;

}
