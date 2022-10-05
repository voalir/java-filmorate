package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    UserController userController;
    User createUser(Integer id, String name, LocalDate birthDate, String login, String email) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setBirthday(birthDate);
        user.setLogin(login);
        user.setEmail(email);
        return user;
    }

    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

    @Test
    void addUser() throws IOException, InterruptedException {
        assertDoesNotThrow(() -> userController.addUser(createUser(10, "Nick",LocalDate.of(2000, 10, 10) ,
                "nick", "index@inbox.org")));
        assertThrows(ValidationException.class, () -> userController.addUser(createUser(-10, "Nick",LocalDate.of(2000, 10, 10) ,
                "nick", "index@inbox.org")));
        assertThrows(NullPointerException.class, () -> userController.addUser(null));
    }
}