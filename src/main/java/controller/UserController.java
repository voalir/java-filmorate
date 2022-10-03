package controller;

import lombok.extern.slf4j.Slf4j;
import model.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;

@RestController(value = "/user")
@Slf4j
public class UserController {

    HashMap<Integer, User> users = new HashMap<>();

    @PostMapping(value = "/add")
    public User addUser(@Valid @RequestBody User user){
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping("/update")
    public User updateUser(@Valid @RequestBody User user){
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping("/all")
    public Collection<User> getUsers(){
        return users.values();
    }
}
