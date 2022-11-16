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
public class MatchController {
    private final DataBaseService dataBaseService;
    private final String userPath = "/users";
    private final String matchesPath = "/matches";

    @PostMapping(userPath + matchesPath)
    @ResponseStatus(HttpStatus.CREATED)
    public void match(@RequestBody Match match) {
        dataBaseService.match(match);
    }

    @GetMapping(userPath + "/{id}" + matchesPath)
    public List<List<User>> findMatch(@PathVariable("id") Long id) {
        return dataBaseService.findMatch(id);
    }

    @DeleteMapping(userPath + matchesPath + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        dataBaseService.deleteByMatch(id);
    }
}
