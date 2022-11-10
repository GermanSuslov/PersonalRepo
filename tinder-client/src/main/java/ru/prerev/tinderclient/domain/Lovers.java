package ru.prerev.tinderclient.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.ReplyKeyboardMaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public class Lovers {
    @Getter
    private Map<Long, ArrayList<ArrayList<User>>> userMatchesMap;
    private final FormPictureCreator formPictureCreator;
    private Map<Long, Integer> userCountMap;
    @Setter
    private Bot bot;

    private final ReplyKeyboardMaker replyKeyboardMaker;

    public void showLovers(Long id, String message) {
        if (message.equalsIgnoreCase("Вправо")) {
            ArrayList<ArrayList<User>> userMatches = userMatchesMap.get(id);

            ArrayList<User> whoUserLiked = userMatches.get(0);
            ArrayList<User> whoLikedUser = userMatches.get(1);
            ArrayList<User> mutualLiking = userMatches.get(2);
            boolean pictureSended = false;

            if (!pictureSended) {
                pictureSended = sendForm(id, whoUserLiked);
                userCountMap.replace(id, userCountMap.get(id) + 1);
            }
            if (!pictureSended) {
                pictureSended = sendForm(id, whoLikedUser);
            }
            if (!pictureSended) {
                pictureSended = sendForm(id, mutualLiking);
            }

        }
        else if (message.equalsIgnoreCase("Влево")) {

        }
        if (message.equalsIgnoreCase("Меню")) {
            userMatchesMap.remove(id);
            userCountMap.remove(id);
            menuButtons(id);
        }
    }

    private boolean sendForm(Long id, ArrayList<User> userForms) {
        boolean pictureSended = false;
        if (userCountMap.get(id) < userForms.size() && !userForms.isEmpty()) {
            if (formPictureCreator.getBot() == null) {
                formPictureCreator.setBot(bot);
            }
            User liked = userForms.get(userCountMap.get(id));
            //User liked = decryptUser(userForms, userCountMap.get(id));
            formPictureCreator.showMatchedUserData(id, liked);
            pictureSended = true;
        } else {
            userCountMap.replace(id, 0);
        }
        return pictureSended;
    }

    public void setUserMatchesMap(Long id, ArrayList<ArrayList<User>> userMatches) {
        if (userMatchesMap == null) {
            userMatchesMap = new HashMap<>();
        }
        if (userCountMap == null) {
            userCountMap = new HashMap<>();
        }
        userMatchesMap.put(id, userMatches);
        userCountMap.put(id, 0);
    }

    private void menuButtons(Long id) {
        SendMessage menuMessage = new SendMessage(id.toString(), "Добро пожаловать");
        menuMessage.setReplyMarkup(replyKeyboardMaker.getMenuKeyboard());
        try {
            bot.execute(menuMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private User decryptUser(ArrayList<User> userForms, Integer count) {
        User user= userForms.get(count);
        //User user = userHashMap.get(1);
        return user;
    }
}
