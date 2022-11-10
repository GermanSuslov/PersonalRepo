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
    private Map<Long, ArrayList<ArrayList<LinkedHashMap<String, String>>>> userMatchesMap;
    private final FormPictureCreator formPictureCreator;
    private Map<Long, Integer> userCountMap;
    @Setter
    private Bot bot;

    private final ReplyKeyboardMaker replyKeyboardMaker;

    public void showLovers(Long id, String message) {
        if (message.equalsIgnoreCase("Вправо")) {
            ArrayList<ArrayList<LinkedHashMap<String, String>>> userMatches = userMatchesMap.get(id);
            boolean pictureSended = false;

            ArrayList<LinkedHashMap<String, String>> whoUserLiked = userMatches.get(0);
            ArrayList<LinkedHashMap<String, String>> whoLikedUser = userMatches.get(1);
            ArrayList<LinkedHashMap<String, String>> mutualLiking = userMatches.get(2);

            if (!pictureSended) {
                pictureSended = sendForm(id, whoUserLiked);
            }
            if (!pictureSended) {
                pictureSended = sendForm(id, whoLikedUser);
            }
            if (!pictureSended) {
                pictureSended = sendForm(id, mutualLiking);
            }

        }
        if (message.equalsIgnoreCase("Влево")) {

        }
        if (message.equalsIgnoreCase("Меню")) {
            userMatchesMap.remove(id);
            userCountMap.remove(id);
            menuButtons(id);
        }
    }

    private boolean sendForm(Long id, ArrayList<LinkedHashMap<String, String>> userForms) {
        boolean pictureSended = false;
        if (userCountMap.get(id) < userForms.size() && !userForms.isEmpty()) {
            //User liked = userForms.get(userCountMap.get(id));
            User liked = decryptUser(userForms, userCountMap.get(id));
            formPictureCreator.showUserData(liked, null);
            pictureSended = true;
        } else {
            userForms.clear();
        }
        return pictureSended;
    }

    public void setUserMatchesMap(Long id, ArrayList<ArrayList<LinkedHashMap<String, String>>> userMatches) {
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

    private User decryptUser(ArrayList<LinkedHashMap<String, String>> userForms, Integer count) {
        LinkedHashMap<String, String> userHashMap = userForms.get(count);
        User user = new User();
        user.setUser_id(Long.parseLong(userHashMap.get("user_id")));
        user.setSex(userHashMap.get("sex"));
        user.setName(userHashMap.get("name"));
        user.setStory(userHashMap.get("story"));
        user.setLooking_for(userHashMap.get("looking_for"));
        return user;
    }
}
