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
    private final UserMatcher updater;
    private final UserDeleter deleter;

    @GetMapping(value = "/users")
    public List<User> findAll() {
        return finder.findAll();
    }

    @GetMapping(value = "/users/{id}")
    public User findById(@PathVariable("id") Long id) {
        System.out.println("findById");
        return finder.findById(id);
    }

    @GetMapping(value = "/users/{id}/search")
    public List<User> search(@PathVariable("id") Long id) {
        System.out.println("search");
        return finder.search(id);
    }

    @GetMapping(value = "/users/{id}/matches")
    public List<List<User>> findMatch(@PathVariable("id") Long id) {
        System.out.println("findMatch");
        return finder.findMatch(id);
    }

    @PostMapping(value = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody User resource) {
        creator.create(resource);
    }

    @PutMapping(value = "/matches/{id}/{id_matched}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @PathVariable("id_matched") Long id_matched) {
        updater.update(id, id_matched);
    }

    @DeleteMapping(value = "/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        deleter.deleteById(id);
    }
}
