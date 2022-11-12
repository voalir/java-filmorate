package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface ObjectStorage<T> {

    T add(T object);

    void remove(T object);

    T modify(T object);

    Collection<T> getAll();

    T get(int id);
}
