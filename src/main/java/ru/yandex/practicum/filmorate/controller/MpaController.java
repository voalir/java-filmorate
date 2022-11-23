package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
@Slf4j
public class MpaController {

    @Autowired
    MpaService mpaService;

    @GetMapping
    public Collection<Mpa> getMpas() {
        return mpaService.getMpas();
    }

    @GetMapping(path = "/{id}")
    public Mpa getMpa(@PathVariable int id) {
        return mpaService.getMpaById(id);
    }
}
