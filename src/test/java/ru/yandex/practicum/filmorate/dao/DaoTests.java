package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
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
    private final LikeDbStorage likeDbStorage;
    private final MpaRatingDbStorage mpaDbStorage;
    private final GenreDbStorage genreDbStorage;

    Film createFilm(Integer id, String name, LocalDate releaseDate, String description, Long duration) {
        Film film = new Film();
        film.setId(id);
        film.setName(name);
        film.setReleaseDate(releaseDate);
        film.setDescription(description);
        film.setDuration(duration);
        film.setMpa(mpaDbStorage.getMpaRatingById(1));
        film.getGenres().add(genreDbStorage.getGenreById(1));
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
        User user2 = createUser(100, "NickName", LocalDate.of(2000, 10, 10),
                "nickName", "indexNick@inbox.org");
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
        Film film2 = createFilm(0, "film2", LocalDate.of(2000, 10, 10),
                "good film2", 120L);
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
        likeDbStorage.addLike(user.getId(), film1.getId());
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
        assertDoesNotThrow(() -> likeDbStorage.addLike(user1.getId(), film1.getId()));
        assertDoesNotThrow(() -> likeDbStorage.addLike(user2.getId(), film1.getId()));
        assertDoesNotThrow(() -> likeDbStorage.addLike(user1.getId(), film2.getId()));
        assertEquals(film1, filmDbStorage.getPopular(filmDbStorage.getAll().size()).iterator().next());
        assertDoesNotThrow(() -> likeDbStorage.deleteLike(user1.getId(), film1.getId()));
        assertDoesNotThrow(() -> likeDbStorage.deleteLike(user2.getId(), film1.getId()));
        assertEquals(film2, filmDbStorage.getPopular(filmDbStorage.getAll().size()).iterator().next());
    }

    @Test
    void testMpaDbStorage() {
        assertNotEquals(0, mpaDbStorage.getMpaRatings().size());
        assertDoesNotThrow(() -> mpaDbStorage.getMpaRatingById(1));
    }

    @Test
    void testGenreDbStorage() {
        assertNotEquals(0, genreDbStorage.getGenres().size());
        assertDoesNotThrow(() -> genreDbStorage.getGenreById(1));
        Film film1 = createFilm(null, "film1", LocalDate.of(2000, 10, 10),
                "good film1", 110L);
        filmDbStorage.add(film1);
        assertEquals(film1.getGenres().size(), genreDbStorage.getFilmGenres(film1.getId()).size());
    }
}
