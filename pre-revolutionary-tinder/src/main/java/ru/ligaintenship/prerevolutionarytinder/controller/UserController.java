package ru.ligaintenship.prerevolutionarytinder.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ligaintenship.prerevolutionarytinder.dao.service.DataBaseService;
import ru.ligaintenship.prerevolutionarytinder.domain.Match;
import ru.ligaintenship.prerevolutionarytinder.domain.User;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final DataBaseService dataBaseService;
    private final String userPath = "/users";
    private final String matchesPath = "/matches";

    @GetMapping(userPath)
    public List<User> findAll() {
        return dataBaseService.findAll();
    }

    @GetMapping(userPath + "/{id}")
    public User findById(@PathVariable("id") Long id) {
        User user = null;
        try {
            user = dataBaseService.findById(id);
        } catch (Exception e) {
            log.info("Пользователь с id: " + id + " не найден\n" + e);
        }
        return user;
    }

    @GetMapping(userPath + "/{id}/search")
    public List<User> search(@PathVariable("id") Long id) {
        return dataBaseService.search(id);
    }

    @PostMapping(userPath)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody User user) {
        dataBaseService.create(user);
    }

    @DeleteMapping(userPath + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        dataBaseService.deleteById(id);
    }
}
