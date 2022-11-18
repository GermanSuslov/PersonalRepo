package ru.ligaintenship.prerevolutionarytinder.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ligaintenship.prerevolutionarytinder.domain.UserDto;
import ru.ligaintenship.prerevolutionarytinder.service.MatchService;
import ru.ligaintenship.prerevolutionarytinder.service.UserService;
import ru.ligaintenship.prerevolutionarytinder.domain.Match;
import ru.ligaintenship.prerevolutionarytinder.domain.User;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class MatchController {
    private final UserService userService;
    private final MatchService matchService;
    private final String matchesPath = "/matches";

    @GetMapping("/{id}" + matchesPath)
    public UserDto findMatch(@PathVariable Long id) {
        return userService.findMatch(id);
    }

    @PostMapping(matchesPath)
    @ResponseStatus(HttpStatus.CREATED)
    public Match match(@RequestBody Match match) {
        matchService.match(match);
        return match;
    }

    @DeleteMapping(matchesPath + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        matchService.deleteByMatch(id);
    }
}
