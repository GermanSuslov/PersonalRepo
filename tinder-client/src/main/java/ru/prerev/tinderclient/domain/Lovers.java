package ru.prerev.tinderclient.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.ReplyKeyboardMaker;

import java.util.*;

@RequiredArgsConstructor
public class Lovers {
    private final FormPictureCreator formPictureCreator;
    @Setter
    private final ReplyKeyboardMaker replyKeyboardMaker;

    private Bot bot;
    @Getter
    private Map<Long, ArrayList<ArrayList<User>>> userMatchesMap;
    //private Map<Long, Integer> userCountMap;
    private List<Integer> userFormCount;
    private List<Boolean> pictureSent;

    public void showLovers(Long id, String message) {
        ArrayList<ArrayList<User>> userMatches = userMatchesMap.get(id);

        ArrayList<User> whoUserLiked = userMatches.get(0);
        ArrayList<User> whoLikedUser = userMatches.get(1);
        ArrayList<User> mutualLiking = userMatches.get(2);
        if (message.equalsIgnoreCase("Вправо")) {
            //boolean pictureSended = false;

            if (!pictureSent.get(0)) {
                pictureSent.set(0, sendForm(id, whoUserLiked, 0));
                //userCountMap.replace(id, userCountMap.get(id) + 1);
                userFormCount.set(0, userFormCount.get(0) + 1);
            }
            if (!pictureSent.get(1)) {
                pictureSent.set(1, sendForm(id, whoLikedUser, 1));
                userFormCount.set(1, userFormCount.get(1) + 1);
            }
            if (!pictureSent.get(2)) {
                pictureSent.set(2, sendForm(id, mutualLiking, 2));
                userFormCount.set(2, userFormCount.get(2) + 1);
            }

        }
        else if (message.equalsIgnoreCase("Влево")) {

        }
        if (message.equalsIgnoreCase("Меню")) {
            //userMatchesMap.remove(id);
            //userCountMap.remove(id);
            userMatchesMap = null;
            userFormCount = null;
            pictureSent = null;
            menuButtons(id);
        }
    }

    private boolean sendForm(Long id, ArrayList<User> userForms, Integer userFormsId) {
        boolean pictureSent = false;
        if (userFormCount.get(userFormsId) < userForms.size() && !userForms.isEmpty()) {
            if (formPictureCreator.getBot() == null) {
                formPictureCreator.setBot(bot);
            }
            User liked = userForms.get(userFormCount.get(userFormsId));
            //User liked = decryptUser(userForms, userCountMap.get(id));
            formPictureCreator.showUserData(id, liked, replyKeyboardMaker.getScrollKeyboard());
            SendMessage message = null;
            if (userFormsId == 0) {
                message = new SendMessage(id.toString(), "Любим вами");
            } else if (userFormsId == 1) {
                message = new SendMessage(id.toString(), "Вы любимы");
            } else if (userFormsId == 2) {
                message = new SendMessage(id.toString(), "Взаимность");
            }
            try {
                bot.execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            //pictureSent = true;
        } else {
            //userCountMap.replace(id, 0);
            pictureSent = true;
        }
        return pictureSent;
    }

    public void setUserMatchesMap(Long id, ArrayList<ArrayList<User>> userMatches) {
        if (userMatchesMap == null) {
            userMatchesMap = new HashMap<>();
        }
        /*if (userCountMap == null) {
            userCountMap = new HashMap<>();
        }*/
        if (userFormCount == null) {
            userFormCount = new ArrayList<>();
            userFormCount.add(0);
            userFormCount.add(0);
            userFormCount.add(0);
        }
        if (pictureSent == null) {
            pictureSent = new ArrayList<>();
            pictureSent.add(false);
            pictureSent.add(false);
            pictureSent.add(false);
        }
        userMatchesMap.put(id, userMatches);
        //userCountMap.put(id, 0);
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

    public void setBot(Bot bot) {
        this.bot = bot;
    }
}
