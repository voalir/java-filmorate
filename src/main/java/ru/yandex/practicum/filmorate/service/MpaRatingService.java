package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

import java.util.Collection;

@Service
public class MpaRatingService {

    @Autowired
    MpaRatingStorage mpaRatingStorage;

    public Collection<MpaRating> getMpaRatings() {
        return mpaRatingStorage.getMpaRatings();
    }

    public MpaRating getMpaRatingById(int id) {
        return mpaRatingStorage.getMpaRatingById(id);
    }
}
