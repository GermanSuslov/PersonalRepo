package ru.prerev.tinderclient.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.enums.bot.ScrollButtonsEnum;
import ru.prerev.tinderclient.enums.resources.MatchEnum;
import ru.prerev.tinderclient.db.search.MatchSearcher;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.ReplyKeyboardMaker;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class Lovers {
    private final Profile profile;
    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final MatchSearcher matchSearcher;

    private Bot bot;
    @Getter
    private Map<Long, List<User>> loversMap;
    private Map<Long, List<Integer>> matchMap;
    private Map<Long, Integer> indexMap;
    private Map<Long, Boolean> isFirstProfileMap;

    public void showLovers(Long id, String message) {
        if (loversMap == null || !loversMap.containsKey(id)) {
            setMatchParameters(id);
        }

        if (message.equalsIgnoreCase(ScrollButtonsEnum.RIGHT_BUTTON.getButtonName())) {
            User currentUser;
            if (isFirstProfileMap.get(id)) {
                currentUser = loversMap.get(id).get(indexMap.get(id));
                isFirstProfileMap.put(id, false);
            } else {
                currentUser = loversMap.get(id).get(getNextIndex(id, indexMap.get(id)));
            }
            showProfileWithMatch(id, currentUser);
        } else if (message.equalsIgnoreCase(ScrollButtonsEnum.LEFT_BUTTON.getButtonName())) {
            User currentUser = loversMap.get(id).get(getPrevIndex(id, indexMap.get(id)));
            showProfileWithMatch(id, currentUser);
        }
    }

    private void showProfileWithMatch(Long id, User user) {
        profile.showProfile(id, user, null);

        String currentMatch = "";
        for (int i = 0; i < matchMap.get(id).size() - 1; i++) {
            if (matchMap.get(id).get(i) <= indexMap.get(id) && indexMap.get(id) < matchMap.get(id).get(i + 1)) {
                currentMatch = MatchEnum.values()[i].getMatch();
                break;
            }
        }
        SendMessage matchMessage = new SendMessage(id.toString(), currentMatch);
        matchMessage.setReplyMarkup(replyKeyboardMaker.getScrollKeyboard());
        try {
            bot.execute(matchMessage);
        } catch (TelegramApiException e) {
            log.error("Не удалось отправить изображение: ");
        }
    }

    private Integer getNextIndex(Long id, Integer currentIndex) {
        if (currentIndex == loversMap.get(id).size() - 1) {
            indexMap.put(id, 0);
            return 0;
        } else {
            indexMap.put(id, indexMap.get(id) + 1);
            return currentIndex + 1;
        }
    }

    private Integer getPrevIndex(Long id, Integer currentIndex) {
        if (currentIndex == 0) {
            indexMap.put(id, loversMap.get(id).size() - 1);
            return loversMap.get(id).size() - 1;
        } else {
            indexMap.put(id, indexMap.get(id) - 1);
            return currentIndex - 1;
        }
    }

    private void setMatchParameters(Long id) {
        if (loversMap == null) {
            loversMap = new HashMap<>();
            matchMap = new HashMap<>();
            indexMap = new HashMap<>();
            isFirstProfileMap = new HashMap<>();
        }
        if (!loversMap.containsKey(id)) {
            List<Integer> matchList = new ArrayList<>();
            List<List<User>> loversListList = matchSearcher.search(id);
            List<User> loversList = new ArrayList<>();
            int prevMatchSize = 0;

            matchList.add(prevMatchSize);
            for (List<User> userList : loversListList) {
                loversList.addAll(userList);
                matchList.add(prevMatchSize + userList.size());
                prevMatchSize += userList.size();
            }
            loversMap.put(id, loversList);
            matchMap.put(id, matchList);
            indexMap.put(id, 0);
            isFirstProfileMap.put(id, true);
        }
    }

    public void resetAddParameters(Long id) {
        loversMap.remove(id);
        matchMap.remove(id);
        indexMap.remove(id);
        isFirstProfileMap.remove(id);
    }

    public void setBot(Bot bot) {
        this.bot = bot;
        profile.setBot(bot);
    }
}