package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmorateApplicationTests {

    @Autowired
    UserController userController;

    @Autowired
    FilmController filmController;

    Film createFilm(Integer id, String name, LocalDate releaseDate, String description, Long duration) {
        Film film = new Film();
        film.setId(id);
        film.setName(name);
        film.setReleaseDate(releaseDate);
        film.setDescription(description);
        film.setDuration(duration);
        return film;
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
    void testFilmController() {
        Film film = createFilm(10, "film", LocalDate.of(2000, 10, 10),
                "good film", 110L);
        Film addedFilm = filmController.addFilm(film);
        assertEquals(1, filmController.getFilms().size());
        assertEquals(film, addedFilm);
        film.setDescription("new description");
        Film updatedFilm = filmController.updateFilm(film);
        assertEquals(film, updatedFilm);
    }

    @Test
    void testUserController() {
        User user = createUser(10, "Nick", LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org");
        User userNameIsBlank = createUser(100, "", LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org");

        User addedUser = userController.addUser(user);
        assertEquals(1, userController.getUsers().size());
        assertEquals(user, addedUser);
        User addedUserNameIsBlank = userController.addUser(userNameIsBlank);
        assertEquals(2, userController.getUsers().size());
        assertEquals(userNameIsBlank.getLogin(), addedUserNameIsBlank.getName());
        user.setName("new name");
        User updatedUser = userController.updateUser(user);
        assertEquals(user.getName(), updatedUser.getName());
    }

}
