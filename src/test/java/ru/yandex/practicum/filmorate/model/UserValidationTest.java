package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserValidationTest {

    static Validator validator;

    @BeforeAll
    static void prepareValidator() {
        validator = Validation.buildDefaultValidatorFactory().usingContext().getValidator();
    }

    User createUser(Integer id, String name, LocalDate birthDate, String login, String email) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setBirthday(birthDate);
        user.setLogin(login);
        user.setEmail(email);
        return user;
    }

    @Test
    @DisplayName("Создать пользователя - корректные данные")
    void createCorrectUser() {
        assertEquals(0, validator.validate(createUser(1, "Nick", LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org")).size());
    }

    @Test
    @DisplayName("Создать пользователя - почта не заполнено")
    void createUserEmailIsNull() {
        assertEquals(1, validator.validate(createUser(10, "Nick", LocalDate.of(2000, 10, 10),
                "nick", null)).size());
    }

    @Test
    @DisplayName("Создать пользователя - почта пустая")
    void createUserEmailIsBlank() {
        assertEquals(1, validator.validate(createUser(10, "Nick", LocalDate.of(2000, 10, 10),
                "nick", "")).size());
    }

    @Test
    @DisplayName("Создать пользователя - почта без символа @")
    void createUserEmailNoAtSymbol() {
        assertEquals(1, validator.validate(createUser(10, "Nick", LocalDate.of(2000, 10, 10),
                "nick", "index_inbox.org")).size());
    }

    @Test
    @DisplayName("Создать пользователя - логин не заполнен")
    void createUserLoginBlank() {
        assertEquals(1, validator.validate(createUser(10, "Nick", LocalDate.of(2000, 10, 10),
                "", "index@inbox.org")).size());
    }

    @Test
    @DisplayName("Создать пользователя - дата рождения в будущем")
    void createUserBirthDayInFuture() {
        assertEquals(1, validator.validate(createUser(10, "Nick", LocalDate.now().plusDays(1),
                "nick", "index@inbox.org")).size());
    }

    @Test
    @DisplayName("Создать пользователя - дата рождения сегодня")
    void createUserBirthDayNow() {
        assertEquals(0, validator.validate(createUser(10, "Nick", LocalDate.now(),
                "nick", "index@inbox.org")).size());
    }

    @Test
    @DisplayName("Создать пользователя - имя не заполнено")
    void createUserNameIsBlank() {
        assertEquals(0, validator.validate(createUser(10, "", LocalDate.now(),
                "nick", "index@inbox.org")).size());
    }
}