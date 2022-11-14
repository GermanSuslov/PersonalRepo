package ru.prerev.tinderclient.db;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class DeleteService {
    private final RestTemplate restTemplate;

    public void delete(Long id) {
        String url = "http://localhost:8090/users";
        String urlUser = url + "/" + id;
        this.restTemplate.delete(urlUser);
    }
}
