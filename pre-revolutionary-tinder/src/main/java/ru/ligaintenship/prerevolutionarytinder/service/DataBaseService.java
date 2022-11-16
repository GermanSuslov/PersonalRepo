package ru.ligaintenship.prerevolutionarytinder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ligaintenship.prerevolutionarytinder.repository.MatchRepository;
import ru.ligaintenship.prerevolutionarytinder.repository.UserRepository;
import ru.ligaintenship.prerevolutionarytinder.domain.Match;
import ru.ligaintenship.prerevolutionarytinder.domain.User;

import java.util.List;

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
