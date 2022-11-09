package ru.prerev.tinderclient.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.prerev.tinderclient.domain.User;

import java.util.Collections;

@RequiredArgsConstructor
public class MatchService {
    private final RestTemplate restTemplate;

    public void match(Long id_user, Long liked_id) {
        /*String url = "http://localhost:8090/matches/" + id_user + "/" + liked_id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(id, headers);
        ResponseEntity<User> response = this.restTemplate.postForEntity(url, entity, User.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return null;
        }*/
    }
}
