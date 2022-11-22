package ru.yandex.practicum.filmorate.storage;

public interface LikesStorage {

    void addLike(Integer userId, Integer filmId);

    void deleteLike(Integer userId, Integer filmId);


}
