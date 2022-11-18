package ru.prerev.tinderclient.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.domain.Match;
import ru.prerev.tinderclient.domain.User;
import ru.prerev.tinderclient.enums.bot.MenuButtonsEnum;
import ru.prerev.tinderclient.enums.bot.ProfileButtonsEnum;
import ru.prerev.tinderclient.enums.bot.ScrollButtonsEnum;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.InlineKeyboardMaker;
import ru.prerev.tinderclient.telegrambot.keyboard.ReplyKeyboardMaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class TelegramService {
    private final UserService userService;
    private final MatchService matchService;
    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final InlineKeyboardMaker inlineKeyboardMaker;

    private Bot bot;
    @Getter
    private Map<Long, List<User>> userListsMap;
    private Map<Long, Integer> indexMap;

    public void showMenu(Long id, String message) {
        if (message.equalsIgnoreCase(ProfileButtonsEnum.EDIT_PROFILE_BUTTON.getButtonName())) {
            userService.deleteUserData(id);
            matchService.deleteUserMatches(id);
        }
        if (message.equalsIgnoreCase(ProfileButtonsEnum.MENU_BUTTON.getButtonName())) {
            menuButtons(id);
        } else if (message.equalsIgnoreCase(MenuButtonsEnum.SEARCH_BUTTON.getButtonName())) {
            search(id);
            checkUserLists(id);
        } else if (message.equalsIgnoreCase(MenuButtonsEnum.PROFILE_BUTTON.getButtonName())) {
            userService.showProfile(id, userService.get(id), inlineKeyboardMaker.getInlineMessageProfileButtons());
        } else if (message.equalsIgnoreCase(MenuButtonsEnum.LOVERS_BUTTON.getButtonName())) {
            matchService.showLovers(id, ScrollButtonsEnum.RIGHT_BUTTON.getButtonName());
        } else if (message.equalsIgnoreCase(ScrollButtonsEnum.RIGHT_BUTTON.getButtonName())) {
            if (matchService.getLoversMap() == null || !matchService.getLoversMap().containsKey(id)) {
                like(id);
                search(id);
            } else {
                matchService.showLovers(id, message);
            }
        } else if (message.equalsIgnoreCase(ScrollButtonsEnum.LEFT_BUTTON.getButtonName())) {
            if (matchService.getLoversMap() == null || !matchService.getLoversMap().containsKey(id)) {
                search(id);
            } else {
                matchService.showLovers(id, message);
            }
        } else if (message.equalsIgnoreCase(ScrollButtonsEnum.MENU_BUTTON.getButtonName())) {
            if (getUserListsMap() != null && getUserListsMap().containsKey(id)) {
                resetSearchParameters(id);
            }
            if (matchService.getLoversMap() != null && matchService.getLoversMap().containsKey(id)) {
                matchService.resetMatchParameters(id);
            }
            menuButtons(id);
        }
    }

    private void checkUserLists(Long id) {
        if (getUserListsMap().get(id).isEmpty()) {
            SendMessage emptyListMessage = new SendMessage(id.toString(), "Нет подходящего кандидата");
            try {
                bot.execute(emptyListMessage);
            } catch (TelegramApiException e) {
                log.error("Не удалось отправить сообщение" + e);
            }
        }
    }

    private void menuButtons(Long id) {
        SendMessage menuMessage = new SendMessage(id.toString(), "Переход в меню");
        menuMessage.setReplyMarkup(replyKeyboardMaker.getMenuKeyboard());
        try {
            bot.execute(menuMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка при отображении кнопок: " + e);
        }
    }

    private void search(Long id) {
        if (userListsMap == null || !userListsMap.containsKey(id)) {
            setSearchParameters(id);
        }

        if (userListsMap.get(id).size() != 0) {
            User currentUser = userListsMap.get(id).get(indexMap.get(id));
            userService.showProfile(id, currentUser, replyKeyboardMaker.getScrollKeyboard());
        }
        if (indexMap.get(id) + 1 == userListsMap.get(id).size()) {
            indexMap.replace(id, 0);
        } else {
            indexMap.replace(id, indexMap.get(id) + 1);
        }
    }

    private void like(Long id) {
        List<User> userList = userListsMap.get(id);
        int index = indexMap.get(id) - 1;
        if (indexMap.get(id) == 0) {
            index = userList.size() - 1;
        }
        Long likedId = userList.get(index).getId();
        Match like = new Match();
        like.setId(id);
        like.setLikedId(likedId);
        like = matchService.match(like);
        log.debug("Лайк поставлен поставлен пользователем с id: " + like.getId() +
                " пользователю с id: " + like.getLikedId());
    }

    private void setSearchParameters(Long id) {
        if (userListsMap == null) {
            userListsMap = new HashMap<>();
            indexMap = new HashMap<>();
        }
        if (!userListsMap.containsKey(id)) {
            userListsMap.put(id, new ArrayList<>(userService.getList(id)));
            indexMap.put(id, 0);
        }
    }

    private void resetSearchParameters(Long id) {
        userListsMap.remove(id);
        indexMap.remove(id);
    }

    public void setBot(Bot bot) {
        this.bot = bot;
        userService.setBot(bot);
        matchService.setBot(bot);
    }
}
