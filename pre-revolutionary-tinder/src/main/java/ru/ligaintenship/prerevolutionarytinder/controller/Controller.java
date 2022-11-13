package ru.ligaintenship.prerevolutionarytinder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ligaintenship.prerevolutionarytinder.dao.User;
import ru.ligaintenship.prerevolutionarytinder.dao.service.DataBaseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final DataBaseService dataBaseService;

    @GetMapping(value = "/users")
    public List<User> findAll() {
        return dataBaseService.findAll();
    }

    @GetMapping(value = "/users/{id}")
    public User findById(@PathVariable("id") Long id) {
        User user = null;
        try {
            user = dataBaseService.findById(id);
        } catch (Exception e) {
            System.out.println("Пользователь с id: " + id + " не найден");
        }
        return user;
    }

    @GetMapping(value = "/users/{id}/search")
    public List<User> search(@PathVariable("id") Long id) {
        return dataBaseService.search(id);
    }

    @GetMapping(value = "/users/{id}/matches")
    public List<List<User>> findMatch(@PathVariable("id") Long id) {
        return dataBaseService.findMatch(id);
    }

    @PostMapping(value = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody User resource) {
        dataBaseService.create(resource);
    }

    @PutMapping(value = "/matches/{id}/{id_matched}")
    public void match(@PathVariable("id") Long id, @PathVariable("id_matched") Long id_matched) {
        dataBaseService.match(id, id_matched);
    }

    @DeleteMapping(value = "/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        dataBaseService.deleteById(id);
    }
}
