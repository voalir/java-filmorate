package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.InvalidIdException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

abstract public class InMemoryObjectStorage<T> implements ObjectStorage<T>{

    private final Map<Integer, T> objects = new HashMap<>();


    @Override
    abstract public T add(T object);

    @Override
    abstract public void remove(T object);

    @Override
    abstract public T modify(T object);

    @Override
    public Collection<T> getAll() {
        return objects.values();
    }

    @Override
    public T get(int id) {
        if (!objects.containsKey(id)) {
            throw new InvalidIdException("object with id=" + id + " not found");
        }
        return objects.get(id);
    }

    protected void putObject(Integer id, T object){
        objects.put(id, object);
    }

    protected boolean containsObjectKey(Integer id){
        return objects.containsKey(id);
    }

    protected void removeObject(Integer id){
        objects.remove(id);
    }
}
