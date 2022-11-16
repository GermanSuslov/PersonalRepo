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
public class Controller {
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

    @PostMapping(userPath + matchesPath)
    @ResponseStatus(HttpStatus.CREATED)
    public void match(@RequestBody Match match) {
        dataBaseService.match(match);
    }

    @GetMapping(value = userPath + "/{id}/search")
    public List<User> search(@PathVariable("id") Long id) {
        return dataBaseService.search(id);
    }

    @GetMapping(value = userPath + "/{id}" + matchesPath)
    public List<List<User>> findMatch(@PathVariable("id") Long id) {
        return dataBaseService.findMatch(id);
    }

    @PostMapping(value = userPath)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody User user) {
        dataBaseService.create(user);
    }

    @DeleteMapping(value = userPath + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        dataBaseService.deleteById(id);
    }
}
