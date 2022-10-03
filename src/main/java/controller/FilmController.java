package controller;

import lombok.extern.slf4j.Slf4j;
import model.Film;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;

@RestController(value = "/film")
@Slf4j
public class FilmController {
    HashMap<Integer, Film> films = new HashMap<>();

    @PostMapping(value = "/add")
    public Film addUser(@Valid @RequestBody Film film){
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping("/update")
    public Film updateUser(@Valid @RequestBody Film film){
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping("/all")
    public Collection<Film> getUsers(){
        return films.values();
    }
}
