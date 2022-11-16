package ru.ligaintenship.prerevolutionarytinder.dao.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ligaintenship.prerevolutionarytinder.controller.exceptions.UserNotFoundException;
import ru.ligaintenship.prerevolutionarytinder.dao.repository.MatchRepository;
import ru.ligaintenship.prerevolutionarytinder.dao.repository.UserRepository;
import ru.ligaintenship.prerevolutionarytinder.domain.Match;
import ru.ligaintenship.prerevolutionarytinder.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DataBaseService {

    private final UserRepository userRepository;
    private final MatchRepository matchRepository;

    public void create(User user) {
        userRepository.create(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteByMatch(Long id) {
        matchRepository.deleteMatch(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    public List<List<User>> findMatch(Long id) {
        return userRepository.findMatch(id);
    }

    public List<User> search(Long id) {
        return userRepository.search(id);
    }

    public void match(Match match) {
        matchRepository.postMatch(match);
    }
}
