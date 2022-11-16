package ru.prerev.tinderclient.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.domain.Match;
import ru.prerev.tinderclient.domain.User;
import ru.prerev.tinderclient.enums.bot.ScrollButtonsEnum;
import ru.prerev.tinderclient.enums.resources.MatchEnum;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.ReplyKeyboardMaker;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchService {
    private final RestTemplate restTemplate;
    private final UserService userService;
    private final ReplyKeyboardMaker replyKeyboardMaker;

    private Bot bot;
    @Value("${server.url}")
    private String url;
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
        userService.showProfile(id, user, null);

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

    private List<List<User>> search(Long id) {
        List[] userList = getMatchesList(id);
        List<Map<String, Object>> userLikedListMap = (List<Map<String, Object>>) userList[0];
        List<Map<String, Object>> likedUserListMap = (List<Map<String, Object>>) userList[1];
        List<Map<String, Object>> mutualLikingListMap = (List<Map<String, Object>>) userList[2];
        List<User> userLiked = getUserList(userLikedListMap);
        List<User> likedUser = getUserList(likedUserListMap);
        List<User> mutualLiking = getUserList(mutualLikingListMap);
        List<List<User>> lists = new ArrayList<>();
        lists.add(userLiked);
        lists.add(likedUser);

        lists.add(mutualLiking);
        return lists;
    }

    private List<User> getUserList(List<Map<String, Object>> listMap) {
        List<User> listUsers = new ArrayList<>();
        for (Map<String, Object> map : listMap) {
            User user = new User();
            Long id = Long.parseLong(map.get("id").toString());
            user.setId(id);
            user.setSex((String) map.get("sex"));
            user.setName((String) map.get("name"));
            user.setStory((String) map.get("story"));
            user.setLookingFor((String) map.get("lookingFor"));
            listUsers.add(user);
        }
        return listUsers;
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
            List<List<User>> loversListList = search(id);
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

    private List[] getMatchesList(Long id) {
        String urlMatch = url + id + "/matches";
        return restTemplate.getForEntity(urlMatch, List[].class).getBody();
    }

    public void match(Match match) {
        String urlMatch = url + "matches";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Match> matchEntity = new HttpEntity<>(match, headers);
        restTemplate.postForObject(urlMatch, matchEntity, Match.class);
    }

    public void setBot(Bot bot) {
        this.bot = bot;
        userService.setBot(bot);
    }
}
