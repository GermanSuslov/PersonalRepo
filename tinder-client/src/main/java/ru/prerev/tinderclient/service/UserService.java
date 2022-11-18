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
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.domain.User;
import ru.prerev.tinderclient.enums.resources.GenderEnum;
import ru.prerev.tinderclient.enums.resources.QuestionnaireEnum;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.InlineKeyboardMaker;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final RestTemplate restTemplate;
    private final InlineKeyboardMaker inlineKeyboardMaker;

    private Bot bot;
    @Value("${server.url}")
    private String url;
    private Map<Long, User> userMap;
    private Map<Long, Boolean> authorizedMap;

    public void showProfile(Long id, User user, ReplyKeyboard keyboard) {
        File filePng = getTranslatedPicture(user);
        InputFile pngFile = new InputFile(filePng, user.getId() + "_form.png");
        SendPhoto formPng = new SendPhoto(id.toString(), pngFile);
        formPng.setReplyMarkup(keyboard);
        SendMessage translatedMessage = new SendMessage(id.toString(), user.getSex().getGender() + ", "
                + getTranslate(user.getName()));
        try {
            bot.execute(translatedMessage);
            bot.execute(formPng);
        } catch (TelegramApiException e) {
            log.error("Не удалось отправить изображение: ");
        }
    }

    public void authorize(Long chatId, String message) {
        if (userMap == null) {
            userMap = new HashMap<>();
            authorizedMap = new HashMap<>();
        }
        if (!userMap.containsKey(chatId)) {
            userMap.put(chatId, get(chatId));
            authorizedMap.put(chatId, false);
        }
        if (userMap.get(chatId) != null) {
            if (userInitiated(userMap.get(chatId)) && !authorizedMap.get(chatId)) {
                showProfile(chatId, userMap.get(chatId), inlineKeyboardMaker.getInlineMessageProfileButtons());
                authorizedMap.put(chatId, true);
            }
        } else {
            userMap.replace(chatId, new User());
        }
        if (userMap.get(chatId) == null || !userInitiated(userMap.get(chatId))) {
            registration(chatId, message);
        }
    }

    private void registration(Long chatId, String message) {
        try {
            initiateUserData(chatId, message);
        } catch (IOException e) {
            log.error("Ошибка при обработке сообщения: ");
        } catch (TelegramApiException e) {
            log.error("Не удалось отправить сообщение: ");
        }
        if (userInitiated(userMap.get(chatId))) {
            User postedUser = post(userMap.get(chatId));
            log.debug("Пользователь " + postedUser.toString() + " внесен в базу данных");
        }
    }

    private void initiateUserData(Long chatId, String message)
            throws IOException, TelegramApiException {
        if (userMap.get(chatId).getSex() == null) {
            if (message.equalsIgnoreCase(GenderEnum.MALE.getGender()) || message.equalsIgnoreCase(GenderEnum.FEMALE.getGender())) {
                userMap.get(chatId).setId(chatId);
                userMap.get(chatId).setSex(GenderEnum.getGenderEnum(message));
                SendMessage nameMessage = new SendMessage(chatId.toString(), QuestionnaireEnum.NAME_QUESTION.getQuestion());
                bot.execute(nameMessage);
            } else {
                SendMessage sexMessageRepeat = new SendMessage(chatId.toString(), QuestionnaireEnum.SEX_QUESTION.getQuestion());
                sexMessageRepeat.setReplyMarkup(inlineKeyboardMaker.getInlineMessageSexButtons());
                bot.execute(sexMessageRepeat);
            }
        } else if (userMap.get(chatId).getName() == null) {
            SendMessage storyMessage = new SendMessage(chatId.toString(), QuestionnaireEnum.STORY_QUESTION.getQuestion());
            bot.execute(storyMessage);
            userMap.get(chatId).setName(message);
        } else if (userMap.get(chatId).getStory() == null) {
            SendMessage lookingForMessage = new SendMessage(chatId.toString(), QuestionnaireEnum.LOOKING_FOR_QUESTION.getQuestion());
            lookingForMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMessageLookingForButtons());
            bot.execute(lookingForMessage);
            userMap.get(chatId).setStory(message);
        } else if (userMap.get(chatId).getLookingFor() == null) {
            userMap.get(chatId).setLookingFor(GenderEnum.getGenderEnum(message));
            SendMessage successMessage = new SendMessage(chatId.toString(),
                    "Вы успешно зарегистрированы.");
            bot.execute(successMessage);
            showProfile(chatId, userMap.get(chatId), inlineKeyboardMaker.getInlineMessageProfileButtons());
            authorizedMap.put(chatId, true);
        }
    }

    public void deleteUserData(Long chatId) {
        delete(chatId);
        SendMessage deleteMessage = new SendMessage(chatId.toString(), "Анкета успешно удалена");
        try {
            bot.execute(deleteMessage);
            userMap.remove(chatId);
            authorize(chatId, "Регистрация");
        } catch (TelegramApiException e) {
            log.error("Не удалось отправить сообщение: ");
        }
    }

    private boolean userInitiated(User user) {
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

    private User post(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        return restTemplate.postForObject(url, entity, User.class);
    }

    public User get(Long id) {
        String urlUser = url + id;
        User user = null;
        try {
            user = this.restTemplate.getForObject(urlUser, User.class);
        } catch (RestClientException e) {
            log.debug("User " + id + " not found");
        }
        return user;
    }

    public List<User> getList(Long id) {
        String urlSearch = url + id + "/search";
        User[] userArray = this.restTemplate.getForEntity(urlSearch, User[].class).getBody();
        return Arrays.stream(userArray).toList();
    }

    private void delete(Long id) {
        String urlUser = url + "/" + id;
        this.restTemplate.delete(urlUser);
    }

    private String getTranslate(String text) {
        String urlTranslate = "http://localhost:5006/translate?resource=" + text;
        return this.restTemplate.getForObject(urlTranslate, String.class);
    }

    private File getTranslatedPicture(User user) {
        String urlPng = "http://localhost:5005/internal/image/from/text/?description="
                + getTranslate(user.getStory());
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

    public void setBot(Bot bot) {
        this.bot = bot;
    }
}
