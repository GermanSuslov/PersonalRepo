package ru.ligaintenship.prerevolutionarytinder.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ligaintenship.prerevolutionarytinder.service.DataBaseService;
import ru.ligaintenship.prerevolutionarytinder.domain.Match;
import ru.ligaintenship.prerevolutionarytinder.domain.User;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class MatchController {
    private final DataBaseService dataBaseService;
    private final String matchesPath = "/matches";

    @PostMapping(matchesPath)
    @ResponseStatus(HttpStatus.CREATED)
    public Match match(@RequestBody Match match) {
        dataBaseService.match(match);
        return match;
    }

    @GetMapping("/{id}" + matchesPath)
    public List<List<User>> findMatch(@PathVariable Long id) {
        return dataBaseService.findMatch(id);
    }

    @DeleteMapping(matchesPath + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        dataBaseService.deleteByMatch(id);
    }
}
