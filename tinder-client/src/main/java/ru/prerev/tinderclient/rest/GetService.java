package ru.prerev.tinderclient.rest;

import lombok.RequiredArgsConstructor;

import org.apache.commons.io.FileUtils;
import org.springframework.web.client.RestTemplate;
import ru.prerev.tinderclient.domain.User;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class GetService {
    private final RestTemplate restTemplate;

    public User get(Long user_id) {
        String url = "http://localhost:8090/users";
        String urlUser = url + "/" + user_id;
        return this.restTemplate.getForObject(urlUser, User.class);
    }

    public List<User> getList(Long user_id) {
        String url = "http://localhost:8090/users";
        String urlUser = url + "/" + user_id + "/search";
        User[] userArray = this.restTemplate.getForEntity(urlUser, User[].class).getBody();
        return Arrays.stream(userArray).toList();
    }

    public List[] getMatchesList(Long user_id) {
        String url = "http://localhost:8090/users/" + user_id + "/matches";
        return this.restTemplate.getForEntity(url, List[].class).getBody();
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
        String fileName = user.getUser_id() + "_form.png";
        try {
            FileUtils.writeByteArrayToFile(filePng = new File(fileName), png);
        } catch (IOException e) {
            System.out.println("Не удалось отправить сообщение :" + getClass());
        }
        return filePng;
    }
}
