package ru.prerev.tinderclient.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.domain.*;
import ru.prerev.tinderclient.enums.bot.MenuButtonsEnum;
import ru.prerev.tinderclient.enums.bot.ProfileButtonsEnum;
import ru.prerev.tinderclient.enums.bot.ScrollButtonsEnum;
import ru.prerev.tinderclient.enums.resources.GenderEnum;
import ru.prerev.tinderclient.enums.resources.QuestionnaireEnum;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.InlineKeyboardMaker;
import ru.prerev.tinderclient.telegrambot.keyboard.ReplyKeyboardMaker;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class TelegramService {
    private final UserService userService;
    private final MatchService matchService;
    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final InlineKeyboardMaker inlineKeyboardMaker;

    private Bot bot;
    private final Map<Long, SearchHelper> searchMap = new ConcurrentHashMap<>();
    private final Map<Long, MatchHelper> matchMap = new ConcurrentHashMap<>();
    private final Map<Long, User> userMap = new ConcurrentHashMap<>();
    private final Map<Long, Boolean> authorizedMap = new ConcurrentHashMap<>();

    public void showMenu(Long id, String message) {
        if (message.equalsIgnoreCase(ProfileButtonsEnum.EDIT_PROFILE_BUTTON.getButtonName())) {
            deleteUserData(id);
            matchService.deleteUserMatches(id);
        } else if (message.equalsIgnoreCase(ProfileButtonsEnum.MENU_BUTTON.getButtonName())) {
            menuButtons(id);
        } else if (message.equalsIgnoreCase(MenuButtonsEnum.SEARCH_BUTTON.getButtonName())) {
            search(id);
            checkUserLists(id);
        } else if (message.equalsIgnoreCase(MenuButtonsEnum.PROFILE_BUTTON.getButtonName())) {
            showProfile(id, userService.get(id), inlineKeyboardMaker.getInlineMessageProfileButtons());
        } else if (message.equalsIgnoreCase(MenuButtonsEnum.LOVERS_BUTTON.getButtonName())) {
            showLovers(id, "First");
        } else if (message.equalsIgnoreCase(ScrollButtonsEnum.RIGHT_BUTTON.getButtonName())) {
            if (!matchMap.containsKey(id)) {
                like(id);
                search(id);
            } else {
                showLovers(id, message);
            }
        } else if (message.equalsIgnoreCase(ScrollButtonsEnum.LEFT_BUTTON.getButtonName())) {
            if (!matchMap.containsKey(id)) {
                search(id);
            } else {
                showLovers(id, message);
            }
        } else if (message.equalsIgnoreCase(ScrollButtonsEnum.MENU_BUTTON.getButtonName())) {
            searchMap.remove(id);
            matchMap.remove(id);
            menuButtons(id);
        }
    }

    public void showProfile(Long id, User user, ReplyKeyboard keyboard) {
        File filePng = userService.getTranslatedPicture(user);
        InputFile pngFile = new InputFile(filePng, user.getId() + "_form.png");
        SendPhoto formPng = new SendPhoto(id.toString(), pngFile);
        formPng.setReplyMarkup(keyboard);
        SendMessage translatedMessage = new SendMessage(id.toString(), user.getSex().getGender() + ", "
                + userService.getTranslate(user.getName()));
        try {
            bot.execute(translatedMessage);
            bot.execute(formPng);
        } catch (TelegramApiException e) {
            log.error("Не удалось отправить изображение: ");
        }
    }

    public void authorize(Long chatId, String message) {
        if (!userMap.containsKey(chatId)) {
            userMap.put(chatId, userService.get(chatId));
            authorizedMap.put(chatId, false);
        }
        if (userMap.get(chatId) != null) {
            if (userService.userInitiated(userMap.get(chatId)) && !authorizedMap.get(chatId)) {
                showProfile(chatId, userMap.get(chatId), inlineKeyboardMaker.getInlineMessageProfileButtons());
                authorizedMap.put(chatId, true);
            }
        } else {
            userMap.replace(chatId, new User());
        }
        if (userMap.get(chatId) == null || !userService.userInitiated(userMap.get(chatId))) {
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
        if (userService.userInitiated(userMap.get(chatId))) {
            User postedUser = userService.post(userMap.get(chatId));
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
        userService.delete(chatId);
        SendMessage deleteMessage = new SendMessage(chatId.toString(), "Анкета успешно удалена");
        try {
            bot.execute(deleteMessage);
            userMap.remove(chatId);
            authorize(chatId, "Регистрация");
        } catch (TelegramApiException e) {
            log.error("Не удалось отправить сообщение: ");
        }
    }

    private void checkUserLists(Long id) {
        if (searchMap.get(id).getUsers().isEmpty()) {
            SendMessage emptyListMessage = new SendMessage(id.toString(),
                    "Нет подходящего кандидата, переход в меню");
            emptyListMessage.setReplyMarkup(replyKeyboardMaker.getMenuKeyboard());
            try {
                bot.execute(emptyListMessage);
            } catch (TelegramApiException e) {
                log.error("Не удалось отправить сообщение" + e);
            }
        }
    }

    private void search(Long id) {
        if (!searchMap.containsKey(id)) {
            searchMap.put(id, new SearchHelper(0, userService.getSearch(id)));
        }

        if (!searchMap.get(id).getUsers().isEmpty()) {
            User currentUser = searchMap.get(id).getUsers().get(searchMap.get(id).getIndex());
            showProfile(id, currentUser, replyKeyboardMaker.getScrollKeyboard());
        }
        if (searchMap.get(id).getIndex() + 1 == searchMap.get(id).getUsers().size()) {
            searchMap.get(id).setIndex(0);
        } else {
            searchMap.get(id).setIndex(searchMap.get(id).getIndex() + 1);
        }
    }

    private void like(Long id) {
        List<User> userList = searchMap.get(id).getUsers();
        int index = searchMap.get(id).getIndex() - 1;
        if (searchMap.get(id).getIndex() == 0) {
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

    public void showLovers(Long id, String message) {
        if (!matchMap.containsKey(id)) {
            matchMap.put(id, new MatchHelper(0, matchService.getMatchesList(id)));

            if (matchMap.get(id).getUsers().isEmpty()) {
                SendMessage emptyMatchesMessage = new SendMessage(id.toString(),
                        "Нет симпатий, переход в меню");
                emptyMatchesMessage.setReplyMarkup(replyKeyboardMaker.getMenuKeyboard());
                try {
                    bot.execute(emptyMatchesMessage);
                } catch (TelegramApiException e) {
                    log.error("Не удалось отправить сообщение" + e);
                }
                matchMap.remove(id);
            } else {
                showProfileWithMatch(id, matchMap.get(id).getUsers().get(matchMap.get(id).getIndex()));
            }
        }
        if (message.equalsIgnoreCase(ScrollButtonsEnum.RIGHT_BUTTON.getButtonName())) {
            if (matchMap.get(id).getIndex() == matchMap.get(id).getUsers().size() - 1) {
                matchMap.get(id).setIndex(0);
            } else {
                matchMap.get(id).setIndex(matchMap.get(id).getIndex() + 1);
            }
            showProfileWithMatch(id, matchMap.get(id).getUsers().get(matchMap.get(id).getIndex()));
        } else if (message.equalsIgnoreCase(ScrollButtonsEnum.LEFT_BUTTON.getButtonName())) {
            if (matchMap.get(id).getIndex() == 0) {
                matchMap.get(id).setIndex(matchMap.get(id).getUsers().size() - 1);
            } else {
                matchMap.get(id).setIndex(matchMap.get(id).getIndex() - 1);
            }
            showProfileWithMatch(id, matchMap.get(id).getUsers().get(matchMap.get(id).getIndex()));
        }
    }

    private void showProfileWithMatch(Long id, MatchUserDto matchUser) {
        showProfile(id, matchUser.getUser(), null);
        SendMessage matchMessage = new SendMessage(id.toString(), matchUser.getMatch().getMatch());
        matchMessage.setReplyMarkup(replyKeyboardMaker.getScrollKeyboard());
        try {
            bot.execute(matchMessage);
        } catch (TelegramApiException e) {
            log.error("Не удалось отправить изображение: ");
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

    public void setBot(Bot bot) {
        this.bot = bot;
    }
}
