package ru.prerev.tinderclient.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.prerev.tinderclient.domain.Match;
import ru.prerev.tinderclient.domain.MatchUserDto;
import ru.prerev.tinderclient.domain.User;
import ru.prerev.tinderclient.domain.UserDto;
import ru.prerev.tinderclient.enums.resources.MatchEnum;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchService {
    private final RestTemplate restTemplate;

    @Value("${server.url}")
    private String url;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    public List<MatchUserDto> getMatchesList(Long id) {
        List<MatchUserDto> matchUserList = new ArrayList<>();
        UserDto userLists = getMatches(id);
        matchUserList.addAll(getMatchUser(userLists.getUserLiked(), MatchEnum.LIKE));
        matchUserList.addAll(getMatchUser(userLists.getLikedUser(), MatchEnum.LIKED));
        matchUserList.addAll(getMatchUser(userLists.getMutualLiked(), MatchEnum.MUTUAL));
        return matchUserList;
    }

    private List<MatchUserDto> getMatchUser(List<User> users, MatchEnum match) {
        List<MatchUserDto> matchUserList = new ArrayList<>();
        for (User user : users) {
            matchUserList.add(new MatchUserDto(user, match));
        }
        return matchUserList;
    }

    public Match match(Match match) {
        String urlMatch = url + contextPath + "/" + "matches";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Match> matchEntity = new HttpEntity<>(match, headers);
        restTemplate.postForObject(urlMatch, matchEntity, Match.class);
        return match;
    }

    public UserDto getMatches(Long id) {
        String urlMatch = url + contextPath + "/" + id + "/matches";
        return restTemplate.getForEntity(urlMatch, UserDto.class).getBody();
    }

    public void deleteUserMatches(Long id) {
        String urlUser = url + contextPath + "/" + "matches/" + id;
        this.restTemplate.delete(urlUser);
    }
}
