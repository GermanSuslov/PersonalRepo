package ru.ligaintenship.prerevolutionarytinder.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ligaintenship.prerevolutionarytinder.service.UserService;
import ru.ligaintenship.prerevolutionarytinder.domain.User;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final String userPath = "/users";

    @GetMapping(userPath)
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping(userPath + "/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping(userPath + "/{id}/search")
    public List<User> search(@PathVariable Long id) {
        return userService.search(id);
    }

    @PostMapping(userPath)
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        userService.create(user);
        return user;
    }

    @DeleteMapping(userPath + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
