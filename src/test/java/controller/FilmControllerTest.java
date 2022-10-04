package controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.yandex.practicum.filmorate.FilmorateApplication;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    ConfigurableApplicationContext context;

    @BeforeEach
    void setUp() {
        context = SpringApplication.run(FilmControllerTest.class);
    }

    @AfterEach
    void tearDown() {
        SpringApplication.exit(context);
    }

    @Test
    void addUser() {

    }

    @Test
    void updateUser() {
    }

    @Test
    void getUsers() {
    }
}