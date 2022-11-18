package ru.ligaintenship.prerevolutionarytinder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ligaintenship.prerevolutionarytinder.domain.Match;
import ru.ligaintenship.prerevolutionarytinder.domain.User;
import ru.ligaintenship.prerevolutionarytinder.repository.MatchRepository;
import ru.ligaintenship.prerevolutionarytinder.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchService {
    private final MatchRepository matchRepository;

    public void deleteByMatch(Long id) {
        matchRepository.deleteMatch(id);
    }

    public void match(Match match) {
        matchRepository.postMatch(match);
    }

}
