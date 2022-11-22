package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.Collection;

@Service
public class MpaService {

    @Autowired
    MpaStorage mpaStorage;

    public Collection<Mpa> getMpas() {
        return mpaStorage.getMpas();
    }

    public Mpa getMpaById(int id) {
        return mpaStorage.getMpaById(id);
    }
}
