package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FriendsPair;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DaoTests {

    private final UserDbStorage userDbStorage;
    private final FilmDbStorage filmDbStorage;
    private final FriendDbStorage friendDbStorage;
    private final LikesDbStorage likesDbStorage;
    private final MpaDbStorage mpaDbStorage;
    private final GenresDbStorage genresDbStorage;

    Film createFilm(Integer id, String name, LocalDate releaseDate, String description, Long duration) {
        Film film = new Film();
        film.setId(id);
        film.setName(name);
        film.setReleaseDate(releaseDate);
        film.setDescription(description);
        film.setDuration(duration);
        film.setMpa(mpaDbStorage.getMpaById(1));
        film.getGenres().add(genresDbStorage.getGenreById(1));
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
    void testUserStorage() {
        User user1 = createUser(10, "Nick", LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org");
        User user2 = createUser(10, "Nick", LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org");
        assertDoesNotThrow(() -> userDbStorage.getAll().size());
        int countUsersBeforeAdd = userDbStorage.getAll().size();
        assertDoesNotThrow(() -> userDbStorage.add(user1));
        assertDoesNotThrow(() -> userDbStorage.add(user2));
        assertEquals(user1, userDbStorage.get(user1.getId()));
        assertEquals(user2, userDbStorage.get(user2.getId()));
        assertEquals(countUsersBeforeAdd + 2, userDbStorage.getAll().size());
        user2.setName("new name");
        assertDoesNotThrow(() -> userDbStorage.modify(user2));
        assertEquals("new name", userDbStorage.get(user2.getId()).getName());
        assertEquals(0, userDbStorage.getFriends(user1.getId()).size());
        friendDbStorage.addRequestToFriend(user1.getId(), user2.getId());
        assertEquals(1, userDbStorage.getFriends(user1.getId()).size());
        int countUsersBeforeDelete = userDbStorage.getAll().size();
        assertDoesNotThrow(() -> userDbStorage.remove(user2));
        assertEquals(countUsersBeforeDelete - 1, userDbStorage.getAll().size());
    }

    @Test
    void testFilmStorage() {
        Film film1 = createFilm(null, "film1", LocalDate.of(2000, 10, 10),
                "good film1", 110L);
        Film film2 = createFilm(null, "film1", LocalDate.of(2000, 10, 10),
                "good film2", 110L);
        assertDoesNotThrow(() -> filmDbStorage.getAll().size());
        int countFilmsBeforeAdd = filmDbStorage.getAll().size();
        assertDoesNotThrow(() -> filmDbStorage.add(film1));
        assertDoesNotThrow(() -> filmDbStorage.add(film2));
        assertEquals(film1, filmDbStorage.get(film1.getId()));
        assertEquals(film2, filmDbStorage.get(film2.getId()));
        assertEquals(countFilmsBeforeAdd + 2, filmDbStorage.getAll().size());
        film1.setDescription("new description");
        assertDoesNotThrow(() -> filmDbStorage.modify(film1));
        assertEquals("new description", filmDbStorage.get(film1.getId()).getDescription());
        User user = createUser(10, "Nick", LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org");
        userDbStorage.add(user);
        likesDbStorage.addLike(user.getId(), film1.getId());
        assertEquals(2, filmDbStorage.getPopular(2).size());
        int countFilmsBeforeDelete = filmDbStorage.getAll().size();
        assertDoesNotThrow(() -> filmDbStorage.remove(film1));
        assertEquals(countFilmsBeforeDelete - 1, filmDbStorage.getAll().size());
    }

    @Test
    void testFriendDbStorage() {
        User user1 = createUser(10, "Nick", LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org");
        User user2 = createUser(10, "Nick", LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org");
        userDbStorage.add(user1);
        userDbStorage.add(user2);
        assertDoesNotThrow(() -> friendDbStorage.addRequestToFriend(user1.getId(), user2.getId()));
        assertEquals(new FriendsPair(user1.getId(), user2.getId(), false), friendDbStorage.getCurrentFriendStatus(user1.getId(), user2.getId()));
        assertDoesNotThrow(() -> friendDbStorage.updateVerifyFriends(user1.getId(), user2.getId(), true));
        assertEquals(new FriendsPair(user1.getId(), user2.getId(), true), friendDbStorage.getCurrentFriendStatus(user1.getId(), user2.getId()));
        friendDbStorage.deleteFriends(user1.getId(), user2.getId());
        assertEquals(0, userDbStorage.getFriends(user1.getId()).size());
    }

    @Test
    void testLikesDbStorage() {
        User user1 = createUser(10, "Nick", LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org");
        User user2 = createUser(10, "Nick", LocalDate.of(2000, 10, 10),
                "nick", "index@inbox.org");
        Film film1 = createFilm(null, "film1", LocalDate.of(2000, 10, 10),
                "good film1", 110L);
        Film film2 = createFilm(null, "film1", LocalDate.of(2000, 10, 10),
                "good film2", 110L);
        userDbStorage.add(user1);
        userDbStorage.add(user2);
        filmDbStorage.add(film1);
        filmDbStorage.add(film2);
        assertDoesNotThrow(() -> likesDbStorage.addLike(user1.getId(), film1.getId()));
        assertDoesNotThrow(() -> likesDbStorage.addLike(user2.getId(), film1.getId()));
        assertDoesNotThrow(() -> likesDbStorage.addLike(user1.getId(), film2.getId()));
        assertEquals(film1, filmDbStorage.getPopular(filmDbStorage.getAll().size()).iterator().next());
        assertDoesNotThrow(() -> likesDbStorage.deleteLike(user1.getId(), film1.getId()));
        assertDoesNotThrow(() -> likesDbStorage.deleteLike(user2.getId(), film1.getId()));
        assertEquals(film2, filmDbStorage.getPopular(filmDbStorage.getAll().size()).iterator().next());
    }

    @Test
    void testMpaDbStorage() {
        assertNotEquals(0, mpaDbStorage.getMpas().size());
        assertDoesNotThrow(() -> mpaDbStorage.getMpaById(1));
    }

    @Test
    void testGenreDbStorage() {
        assertNotEquals(0, genresDbStorage.getGenres().size());
        assertDoesNotThrow(() -> genresDbStorage.getGenreById(1));
        Film film1 = createFilm(null, "film1", LocalDate.of(2000, 10, 10),
                "good film1", 110L);
        filmDbStorage.add(film1);
        assertEquals(film1.getGenres().size(), genresDbStorage.getFilmGenres(film1.getId()).size());
    }
}
