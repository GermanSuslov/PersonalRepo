package ru.ligaintenship.prerevolutionarytinder.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ligaintenship.prerevolutionarytinder.exceptions.ErrorObject;
import ru.ligaintenship.prerevolutionarytinder.exceptions.UserNotFoundException;
import ru.ligaintenship.prerevolutionarytinder.service.DataBaseService;
import ru.ligaintenship.prerevolutionarytinder.domain.User;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final DataBaseService dataBaseService;
    private final String userPath = "/users";

    @GetMapping(userPath)
    public List<User> findAll() {
        return dataBaseService.findAll();
    }

    @GetMapping(userPath + "/{id}")
    public User findById(@PathVariable Long id) {
        return dataBaseService.findById(id);
    }

    @GetMapping(userPath + "/{id}/search")
    public List<User> search(@PathVariable Long id) {
        return dataBaseService.search(id);
    }

    @PostMapping(userPath)
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        dataBaseService.create(user);
        return user;
    }

    @DeleteMapping(userPath + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        dataBaseService.deleteById(id);
    }
}
