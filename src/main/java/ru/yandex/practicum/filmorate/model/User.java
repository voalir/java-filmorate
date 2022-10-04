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

    public static Boolean validate(User user) {
        if (user.getId() < 0) {
            throw new ValidationException("id less zero");
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("email is incorrect");
        }
        if (user.getLogin().isBlank()) {
            throw new ValidationException("login is blank");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("birthday in future");
        }
        return true;
    }
}
