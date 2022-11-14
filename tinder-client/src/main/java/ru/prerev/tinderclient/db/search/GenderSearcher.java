package ru.prerev.tinderclient.db.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.domain.Profile;
import ru.prerev.tinderclient.domain.User;
import ru.prerev.tinderclient.db.GetService;
import ru.prerev.tinderclient.db.MatchService;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.ReplyKeyboardMaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class GenderSearcher {
    private final GetService getService;
    private final MatchService matchService;
    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final Profile profile;

    @Getter
    private Map<Long, List<User>> userListsMap;
    private Map<Long, Integer> indexMap;

    public void search(Long id) {
        if (userListsMap == null || !userListsMap.containsKey(id)) {
            setGenderParameters(id);
        }

        if (userListsMap.get(id).size() != 0) {
            User currentUser = userListsMap.get(id).get(indexMap.get(id));
            profile.showProfile(id, currentUser, replyKeyboardMaker.getScrollKeyboard());
        }
        if (indexMap.get(id) + 1 == userListsMap.get(id).size()) {
            indexMap.replace(id, 0);
        } else {
            indexMap.replace(id, indexMap.get(id) + 1);
        }
    }

    public void match(Long id) {
        List<User> userList = userListsMap.get(id);
        int index = indexMap.get(id) - 1;
        if (indexMap.get(id) == 0) {
            index = userList.size() - 1;
        }
        Long likedId = userList.get(index).getId();
        matchService.match(id, likedId);
    }

    private void setGenderParameters(Long id) {
        if (userListsMap == null) {
            userListsMap = new HashMap<>();
            indexMap = new HashMap<>();
        }
        if (!userListsMap.containsKey(id)) {
            userListsMap.put(id, new ArrayList<>(getService.getList(id)));
            indexMap.put(id, 0);
        }
    }

    public void resetAddParameters(Long id) {
        userListsMap.remove(id);
        indexMap.remove(id);
    }

    public void setBot(Bot bot) {
        profile.setBot(bot);
    }
}
