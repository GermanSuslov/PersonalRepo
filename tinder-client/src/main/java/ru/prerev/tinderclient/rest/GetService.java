package ru.prerev.tinderclient.rest;

import lombok.RequiredArgsConstructor;

import org.springframework.web.client.RestTemplate;
import ru.prerev.tinderclient.domain.User;

@RequiredArgsConstructor
public class GetService {
    /* @Getter
     @Setter
     @Value("${server.url}")
     String url;*/
    private final RestTemplate restTemplate;

    public User get(Long user_id) {
        String url = "http://localhost:8090/users";
        String urlUser = url + "/" + user_id;
        return this.restTemplate.getForObject(urlUser, User.class);
    }
}
