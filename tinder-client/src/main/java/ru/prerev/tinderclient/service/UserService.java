package ru.prerev.tinderclient.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.prerev.tinderclient.domain.User;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final RestTemplate restTemplate;

    @Value("${server.url}")
    private String url;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${translate.url}")
    private String urlTranslate;
    @Value("${image.url}")
    private String urlImage;

    public boolean userInitiated(User user) {
        if (user.getId() == null) {
            return false;
        }
        if (user.getSex() == null) {
            return false;
        }
        if (user.getName() == null) {
            return false;
        }
        if (user.getStory() == null) {
            return false;
        }
        if (user.getLookingFor() == null) {
            return false;
        }
        return true;
    }

    public User get(Long id) {
        String urlUser = url + contextPath + "/" + id;
        User user = null;
        try {
            user = this.restTemplate.getForObject(urlUser, User.class);
        } catch (RestClientException e) {
            log.debug("User " + id + " not found");
        }
        return user;
    }

    public List<User> getSearch(Long id) {
        String urlSearch = url + contextPath + "/" + id + "/search";
        User[] userArray = this.restTemplate.getForEntity(urlSearch, User[].class).getBody();
        return Arrays.stream(userArray).toList();
    }

    public User post(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        return restTemplate.postForObject(url, entity, User.class);
    }

    public void delete(Long id) {
        String urlUser = url + contextPath + "/" + id;
        this.restTemplate.delete(urlUser);
    }

    public String getTranslate(String text) {
        return this.restTemplate.getForObject(urlTranslate + text, String.class);
    }

    public File getTranslatedPicture(User user) {
        String urlPng = urlImage + getTranslate(user.getStory());
        byte[] png = this.restTemplate.getForObject(urlPng, byte[].class);
        File filePng = null;
        String fileName = "src/main/resources/profiles/" + user.getId() + "_form.png";
        try {
            FileUtils.writeByteArrayToFile(filePng = new File(fileName), png);
        } catch (IOException e) {
            log.error("Не удалось сформировать изображение: ");
        }
        return filePng;
    }
}
