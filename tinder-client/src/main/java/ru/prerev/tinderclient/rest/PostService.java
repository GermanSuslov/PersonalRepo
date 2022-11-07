package ru.prerev.tinderclient.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.prerev.tinderclient.config.ServerProperty;
import ru.prerev.tinderclient.domain.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class PostService {
    private final RestTemplate restTemplate;
    private final ServerProperty property;

    public User post(User user) {
        String url = "http://localhost:8090/users";
        //String url = property.getUrl();
        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // build the request
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        // send POST request
        ResponseEntity<User> response = this.restTemplate.postForEntity(url, entity, User.class);

        /*// create a map for post parameters
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        map.put("title", user.getName());
        map.put("body", user);*/


        // check response status code
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return null;
        }
    }
}