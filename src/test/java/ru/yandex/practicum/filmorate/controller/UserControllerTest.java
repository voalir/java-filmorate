package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.model.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

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
        FilmorateApplication.main(new String[] {});

    }

    @Test
    void addUser() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/users");
        Gson gson = getGson();
        User user = createUser(10, "Nick",LocalDate.of(2000, 10, 10) ,
                "nick", "index@inbox.org");
        String json = gson.toJson(user);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).header("Content-Type", "application/json").build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        user = createUser(-10, "Nick",LocalDate.of(2000, 10, 10) ,
                "nick", "index@inbox.org");
        json = gson.toJson(user);
        body = HttpRequest.BodyPublishers.ofString(json);
        request = HttpRequest.newBuilder().uri(url).POST(body).header("Content-Type", "application/json").build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(500, response.statusCode());

        body = HttpRequest.BodyPublishers.ofString("");
        request = HttpRequest.newBuilder().uri(url).POST(body).header("Content-Type", "application/json").build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new TypeAdapter<LocalDate>() {
            @Override
            public void write(final JsonWriter jsonWriter, final LocalDate localDate) throws IOException {
                jsonWriter.value(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            }

            @Override
            public LocalDate read(final JsonReader jsonReader) throws IOException {
                return LocalDate.parse(jsonReader.nextString(), DateTimeFormatter.ISO_LOCAL_DATE);
            }
        });
        return gsonBuilder.create();
    }
}