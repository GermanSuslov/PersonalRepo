package ru.prerev.tinderclient.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.constants.bot.ScrollButtonsEnum;
import ru.prerev.tinderclient.constants.resources.MatchEnum;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.ReplyKeyboardMaker;

import java.util.*;

@RequiredArgsConstructor
public class Lovers {
    private final Profile profile;
    @Setter
    private final ReplyKeyboardMaker replyKeyboardMaker;

    private Bot bot;
    @Getter
    private Map<Long, ArrayList<ArrayList<User>>> userMatchesMap;
    private List<Integer> userFormCount;
    private List<Boolean> allPicturesSent;

    public void showLovers(Long id, String message) {
        ArrayList<ArrayList<User>> userMatches = userMatchesMap.get(id);

        ArrayList<User> whoUserLiked = userMatches.get(0);
        ArrayList<User> whoLikedUser = userMatches.get(1);
        ArrayList<User> mutualLiking = userMatches.get(2);

        if (message.equalsIgnoreCase(ScrollButtonsEnum.LEFT_BUTTON.getButtonName())) {
            if (!allPicturesSent.get(0)) {
                try {
                    userFormCount.set(0, userFormCount.get(0) - 2);
                    sendForm(id, whoUserLiked, 0);
                } catch (Exception ignored) {
                    userFormCount.set(0, 0);
                    sendForm(id, whoUserLiked, 0);
                    userFormCount.set(0, 1);
                }
            }
            if (!allPicturesSent.get(1) && allPicturesSent.get(0)) {
                try {
                    userFormCount.set(1, userFormCount.get(1) - 2);
                } catch (Exception ignored) {
                    allPicturesSent.set(0, false);
                    userFormCount.set(0, userFormCount.get(0) + 1);
                }
                sendForm(id, whoLikedUser, 1);
            }
            if (!allPicturesSent.get(2) && allPicturesSent.get(1)) {
                try {
                    userFormCount.set(2, userFormCount.get(2) - 2);
                } catch (Exception ignored) {
                    allPicturesSent.set(1, false);
                    userFormCount.set(1, userFormCount.get(1) + 1);
                }
                sendForm(id, mutualLiking, 2);
            }
        } else if (message.equalsIgnoreCase(ScrollButtonsEnum.RIGHT_BUTTON.getButtonName())) {
            if (!allPicturesSent.get(0)) {
                sendForm(id, whoUserLiked, 0);
                userFormCount.set(0, userFormCount.get(0) + 1);
            }
            if (!allPicturesSent.get(1) && allPicturesSent.get(0)) {
                sendForm(id, whoLikedUser, 1);
                userFormCount.set(1, userFormCount.get(1) + 1);
            }
            if (!allPicturesSent.get(2) && allPicturesSent.get(1)) {
                sendForm(id, mutualLiking, 2);
                userFormCount.set(2, userFormCount.get(2) + 1);
                if (allPicturesSent.get(2)) {
                    for (int i = 0; i < 3; i++) {
                        userFormCount.set(i, 0);
                        allPicturesSent.set(i, false);
                    }
                    sendForm(id, whoUserLiked, 0);
                    userFormCount.set(0, userFormCount.get(0) + 1);
                }
            }
        }
    }

    private void sendForm(Long id, ArrayList<User> userForms, Integer userFormsId) {
        if (userFormCount.get(userFormsId) < userForms.size() && !userForms.isEmpty()) {
            if (profile.getBot() == null) {
                profile.setBot(bot);
            }
            User liked = userForms.get(userFormCount.get(userFormsId));
            profile.showProfile(id, liked, replyKeyboardMaker.getScrollKeyboard());
            SendMessage message = null;
            if (userFormsId == 0) {
                message = new SendMessage(id.toString(), MatchEnum.LIKE.getMatch());
            } else if (userFormsId == 1) {
                message = new SendMessage(id.toString(), MatchEnum.LIKED.getMatch());
            } else if (userFormsId == 2) {
                message = new SendMessage(id.toString(), MatchEnum.MUTUAL.getMatch());
            }
            try {
                bot.execute(message);
            } catch (TelegramApiException e) {
                System.out.println("Не удалось отправить сообщение :" + getClass());
            }
        } else {
            allPicturesSent.set(userFormsId, true);
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
        if (allPicturesSent == null) {
            allPicturesSent = new ArrayList<>();
            allPicturesSent.add(false);
            allPicturesSent.add(false);
            allPicturesSent.add(false);
        }
        userMatchesMap.put(id, userMatches);
    }

    public void resetAddParameters() {
        userMatchesMap = null;
        userFormCount = null;
        allPicturesSent = null;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }
}