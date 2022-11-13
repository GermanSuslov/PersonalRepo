package ru.prerev.tinderclient.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RequiredArgsConstructor
public class MatchService {
    private final RestTemplate restTemplate;

    public void match(Long id, Long likedId) {
        String url = "http://localhost:8090/matches/" + id + "/" + likedId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        this.restTemplate.put(url, entity);
    }
}
