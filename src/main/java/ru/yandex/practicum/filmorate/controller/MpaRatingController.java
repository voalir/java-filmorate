package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.MpaRatingService;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
@Slf4j
public class MpaRatingController {

    @Autowired
    MpaRatingService mpaRatingService;

    @GetMapping
    public Collection<MpaRating> getMpaRatings() {
        return mpaRatingService.getMpaRatings();
    }

    @GetMapping(path = "/{id}")
    public MpaRating getMpaRating(@PathVariable int id) {
        return mpaRatingService.getMpaRatingById(id);
    }
}
