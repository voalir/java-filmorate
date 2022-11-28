package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.GenreController;
import ru.yandex.practicum.filmorate.controller.MpaRatingController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmorateApplicationTests {

    @Autowired
    UserController userController;

    @Autowired
    FilmController filmController;

    @Autowired
    MpaRatingController mpaRatingController;

    @Autowired
    GenreController genreController;


    Film createFilm(Integer id, String name, LocalDate releaseDate, String description, Long duration) {
        Film film = new Film();
        film.setId(id);
        film.setName(name);
        film.setReleaseDate(releaseDate);
        film.setDescription(description);
        film.setDuration(duration);
        film.setMpa(mpaRatingController.getMpaRating(1));
        film.getGenres().add(genreController.getGenre(1));
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
        Film film = createFilm(null, "film1", LocalDate.of(2000, 10, 10),
                "good film1", 110L);
        Film addedFilm = filmController.addFilm(film);
        assertEquals(1, filmController.getFilms().size());
        assertEquals(film, addedFilm);
        film.setDescription("new description");
        film.getGenres().add(genreController.getGenre(2));
        Film updatedFilm = filmController.updateFilm(film);
        assertEquals(film, updatedFilm);
        User user1 = userController.addUser(createUser(null, "Nick1", LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org"));
        User user2 = userController.addUser(createUser(null, "Nick2", LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org"));
        filmController.likeFilm(updatedFilm.getId(), user1.getId());
        filmController.likeFilm(updatedFilm.getId(), user2.getId());
        assertThrows(DuplicateKeyException.class, () -> filmController.likeFilm(updatedFilm.getId(), user2.getId()));
        assertEquals(updatedFilm, filmController.getPopularFilms(10).iterator().next());
        assertThrows(InvalidIdException.class, () -> filmController.likeFilm(addedFilm.getId(), 9999));
        filmController.deleteLike(updatedFilm.getId(), user1.getId());
        filmController.deleteLike(updatedFilm.getId(), user2.getId());
        assertDoesNotThrow(() -> filmController.deleteLike(addedFilm.getId(), user1.getId()));
        assertEquals(1, filmController.getPopularFilms(2).size());
        assertEquals(0, filmController.getPopularFilms(0).size());
    }

    @Test
    void testUserController() {
        int usersBeforeTest = userController.getUsers().size();
        User user = createUser(10, "Nick", LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org");
        User userNameIsBlank = createUser(100, "", LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org");
        User userNameIsNull = createUser(1000, null, LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org");

        User addedUser = userController.addUser(user);
        assertEquals(1 + usersBeforeTest, userController.getUsers().size());
        assertEquals(user, addedUser);
        User addedUserNameIsBlank = userController.addUser(userNameIsBlank);
        assertEquals(2 + usersBeforeTest, userController.getUsers().size());
        assertEquals(userNameIsBlank.getLogin(), addedUserNameIsBlank.getName());
        User addedUserNameIsNull = userController.addUser(userNameIsNull);
        assertEquals(3 + usersBeforeTest, userController.getUsers().size());
        assertEquals(userNameIsNull.getLogin(), addedUserNameIsNull.getName());
        user.setName("new name");
        User updatedUser = userController.updateUser(user);
        assertEquals(user.getName(), updatedUser.getName());
        User addedUser1 = userController.addUser(createUser(null, "Nick1", LocalDate.of(2000, 10, 10),
                "nick1", "index1@inbox.org"));
        User addedUser2 = userController.addUser(createUser(null, "Nick2", LocalDate.of(2000, 10, 10),
                "nick2", "index2@inbox.org"));
        userController.addFriend(addedUser.getId(), addedUser1.getId());
        assertEquals(1, userController.friendsList(addedUser.getId()).size());
        assertEquals(0, userController.friendsList(addedUser1.getId()).size());
        assertThrows(InvalidIdException.class, () -> userController.addFriend(9999, addedUser1.getId()));
        assertThrows(InvalidIdException.class, () -> userController.addFriend(addedUser.getId(), 9999));
        userController.addFriend(addedUser1.getId(), addedUser.getId());
        assertEquals(1, userController.friendsList(addedUser.getId()).size());
        assertEquals(1, userController.friendsList(addedUser1.getId()).size());
        userController.addFriend(addedUser.getId(), addedUser2.getId());
        userController.addFriend(addedUser2.getId(), addedUser.getId());
        assertEquals(1, userController.commonFriends(addedUser1.getId(), addedUser2.getId()).size());
    }

    @Test
    void testGenreController() {
        assertDoesNotThrow(() -> genreController.getGenres());
        assertDoesNotThrow(() -> genreController.getGenre(1));
    }

    @Test
    void testMpaComtroller() {
        assertDoesNotThrow(() -> mpaRatingController.getMpaRatings());
        assertDoesNotThrow(() -> mpaRatingController.getMpaRating(1));
    }

}
