package ru.ligaintenship.prerevolutionarytinder.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ligaintenship.prerevolutionarytinder.domain.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final UserFinder finder;
    private final UserCreator creator;
    private final UserUpdater updater;
    //private final UserDeleter deleter;

    @GetMapping(value = "/users")
    public List<User> findAll() {
        return finder.findAll();
    }

    @GetMapping(value = "/users/{id}")
    public User findById(@PathVariable("id") Long id) {
        return finder.findById(id);
    }

    @GetMapping(value = "/users/{id}/search")
    public List<User> search(@PathVariable("id") Long id) {
        return finder.search(id);
    }

    @GetMapping(value = "/users/{id}/matches")
    public List<List<User>> findMatch(@PathVariable("id") Long id) {
        return finder.findMatch(id);
    }

    @PostMapping(value = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody User resource) {
        creator.create(resource);
    }

    @PutMapping(value = "/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody User resource) {
        updater.update(resource);
    }

    /*@DeleteMapping(value = "/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        deleter.deleteById(id);
    }*/
}
