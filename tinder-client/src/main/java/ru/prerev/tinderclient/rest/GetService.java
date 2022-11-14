package ru.prerev.tinderclient.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.client.RestTemplate;
import ru.prerev.tinderclient.domain.User;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class GetService {
    private final RestTemplate restTemplate;
    private final String url = "http://localhost:8090/users/";

    public User get(Long id) {
        String urlUser = url + id;
        return this.restTemplate.getForObject(urlUser, User.class);
    }

    public List<User> getList(Long id) {
        String urlSearch = url + id + "/search";
        User[] userArray = this.restTemplate.getForEntity(urlSearch, User[].class).getBody();
        return Arrays.stream(userArray).toList();
    }

    public List[] getMatchesList(Long id) {
        String urlMatch = url + id + "/matches";
        return this.restTemplate.getForEntity(urlMatch, List[].class).getBody();
    }

    public String getTranslate(String text) {
        String urlTranslate = "http://localhost:5006/translate?resource=" + text;
        return this.restTemplate.getForObject(urlTranslate, String.class);
    }

    public File getTranslatedPicture(User user) {
        String urlPng = "http://localhost:5005/internal/image/from/text/?description="
                + getTranslate(user.getStory());
        byte[] png = this.restTemplate.getForObject(urlPng, byte[].class);
        File filePng = null;
        String fileName = "src/main/resources/" + user.getId() + "_form.png";
        try {
            FileUtils.writeByteArrayToFile(filePng = new File(fileName), png);
        } catch (IOException e) {
            log.error("Не удалось отправить сообщение: ");
        }
        return filePng;
    }
}
