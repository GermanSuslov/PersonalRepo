package ru.prerev.tinderclient.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import ru.prerev.tinderclient.domain.User;

@RequiredArgsConstructor
public class DeleteService {
    private final RestTemplate restTemplate;

    public void delete(Long user_id) {
        String url = "http://localhost:8090/users";
        String urlUser = url + "/" + user_id;
        this.restTemplate.delete(urlUser);
    }
}
