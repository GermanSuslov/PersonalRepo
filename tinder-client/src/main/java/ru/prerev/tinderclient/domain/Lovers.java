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
    private List<Integer> userFormCount;
    private List<Boolean> allPicturesSended;

    public void showLovers(Long id, String message) {
        ArrayList<ArrayList<User>> userMatches = userMatchesMap.get(id);

        ArrayList<User> whoUserLiked = userMatches.get(0);
        ArrayList<User> whoLikedUser = userMatches.get(1);
        ArrayList<User> mutualLiking = userMatches.get(2);

        if (message.equalsIgnoreCase("Влево")) {
            if (!allPicturesSended.get(0)) {
                try {
                    userFormCount.set(0, userFormCount.get(0) - 2);
                    sendForm(id, whoUserLiked, 0);
                } catch (Exception ignored) {
                    userFormCount.set(0, 0);
                    sendForm(id, whoUserLiked, 0);
                    userFormCount.set(0, 1);
                }
            }
            if (!allPicturesSended.get(1) && allPicturesSended.get(0)) {
                try {
                    userFormCount.set(1, userFormCount.get(1) - 2);
                } catch (Exception ignored) {
                    allPicturesSended.set(0, false);
                    userFormCount.set(0, userFormCount.get(0) + 1);
                }
                sendForm(id, whoLikedUser, 1);
            }
            if (!allPicturesSended.get(2) && allPicturesSended.get(1)) {
                try {
                    userFormCount.set(2, userFormCount.get(2) - 2);
                } catch (Exception ignored) {
                    allPicturesSended.set(1, false);
                    userFormCount.set(1, userFormCount.get(1) + 1);
                }
                sendForm(id, mutualLiking, 2);
            }
        }
        else if (message.equalsIgnoreCase("Вправо")) {
            if (!allPicturesSended.get(0)) {
                sendForm(id, whoUserLiked, 0);
                userFormCount.set(0, userFormCount.get(0) + 1);
            }
            if (!allPicturesSended.get(1) && allPicturesSended.get(0)) {
                sendForm(id, whoLikedUser, 1);
                userFormCount.set(1, userFormCount.get(1) + 1);
            }
            if (!allPicturesSended.get(2) && allPicturesSended.get(1)) {
                sendForm(id, mutualLiking, 2);
                userFormCount.set(2, userFormCount.get(2) + 1);
                if (allPicturesSended.get(2)) {
                    for (int i = 0; i < 3; i++) {
                        userFormCount.set(i, 0);
                        allPicturesSended.set(i, false);
                    }
                    sendForm(id, whoUserLiked, 0);
                    userFormCount.set(0, userFormCount.get(0) + 1);
                }
            }

        }
        if (message.equalsIgnoreCase("Меню")) {
            userMatchesMap = null;
            userFormCount = null;
            allPicturesSended = null;
            menuButtons(id);
        }
    }

    private void sendForm(Long id, ArrayList<User> userForms, Integer userFormsId) {
        if (userFormCount.get(userFormsId) < userForms.size() && !userForms.isEmpty()) {
            if (formPictureCreator.getBot() == null) {
                formPictureCreator.setBot(bot);
            }
            User liked = userForms.get(userFormCount.get(userFormsId));
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
                System.out.println("Не удалось отправить сообщение :" + getClass());
            }
        } else {
            allPicturesSended.set(userFormsId, true);
        }
    }

    public void setUserMatchesMap(Long id, ArrayList<ArrayList<User>> userMatches) {
        if (userMatchesMap == null) {
            userMatchesMap = new HashMap<>();
        }
        if (userFormCount == null) {
            userFormCount = new ArrayList<>();
            userFormCount.add(0);
            userFormCount.add(0);
            userFormCount.add(0);
        }
        if (allPicturesSended == null) {
            allPicturesSended = new ArrayList<>();
            allPicturesSended.add(false);
            allPicturesSended.add(false);
            allPicturesSended.add(false);
        }
        userMatchesMap.put(id, userMatches);
    }

    private void menuButtons(Long id) {
        SendMessage menuMessage = new SendMessage(id.toString(), "Добро пожаловать");
        menuMessage.setReplyMarkup(replyKeyboardMaker.getMenuKeyboard());
        try {
            bot.execute(menuMessage);
        } catch (TelegramApiException e) {
            System.out.println("Не отобразить кнопки :" + getClass());
        }
    }


    public void setBot(Bot bot) {
        this.bot = bot;
    }
}