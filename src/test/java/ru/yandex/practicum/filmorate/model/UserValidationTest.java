package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import javax.validation.ValidationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserValidationTest {
    User createUser(Integer id, String name, LocalDate birthDate, String login, String email) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setBirthday(birthDate);
        user.setLogin(login);
        user.setEmail(email);
        if (User.validate(user)){
            return user;
        }
        return null;
    }

    @Test
    @DisplayName("Создать пользователя - корректные данные")
    void createCorrectUser() {
        assertDoesNotThrow(() -> createUser(10, "Nick", LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org"));
    }

    @Test
    @DisplayName("Создать пользователя - ид меньше нуля")
    void createUserIdLessZero() {
        assertThrows(ValidationException.class, () -> createUser(-10, "Nick", LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org"));
    }

    @Test
    @DisplayName("Создать пользователя - id=0")
    void createUserIdZero() {
        assertDoesNotThrow(() -> createUser(0, "Nick", LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org"));
    }

    @Test
    @DisplayName("Создать пользователя - id=1")
    void createUserIdOne() {
        assertDoesNotThrow(() -> createUser(1, "Nick", LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org"));
    }

    @Test
    @DisplayName("Создать пользователя - почта не заполнено")
    void createUserEmailIsNull() {
        assertThrows(ValidationException.class, () -> createUser(10, "Nick", LocalDate.of(2000, 10, 10),
                "nick", null));
    }

    @Test
    @DisplayName("Создать пользователя - почта пустая")
    void createUserEmailIsBlank() {
        assertThrows(ValidationException.class, () -> createUser(10, "Nick", LocalDate.of(2000, 10, 10),
                "nick", ""));
    }

    @Test
    @DisplayName("Создать пользователя - почта без символа @")
    void createUserEmailNoAtSymbol() {
        assertThrows(ValidationException.class, () -> createUser(10, "Nick", LocalDate.of(2000, 10, 10),
                "nick", "index_inbox.org"));
    }

    @Test
    @DisplayName("Создать пользователя - логин не заполнен")
    void createUserLoginBlank() {
        assertThrows(ValidationException.class, () -> createUser(10, "Nick", LocalDate.of(2000, 10, 10),
                "", "index@inbox.org"));
    }

    @Test
    @DisplayName("Создать пользователя - дата рождения в будущем")
    void createUserBirthDayInFuture() {
        assertThrows(ValidationException.class, () -> createUser(10, "Nick", LocalDate.now().plusDays(1),
                "nick", "index@inbox.org"));
    }

    @Test
    @DisplayName("Создать пользователя - дата рождения сегодня")
    void createUserBirthDayNow() {
        assertDoesNotThrow(() -> createUser(10, "Nick", LocalDate.now(),
                "nick", "index@inbox.org"));
    }

    @Test
    @DisplayName("Создать пользователя - имя не заполнено")
    void createUserNameIsBlank() {
        assertDoesNotThrow(() -> createUser(10, "", LocalDate.now(),
                "nick", "index@inbox.org"));
    }
}