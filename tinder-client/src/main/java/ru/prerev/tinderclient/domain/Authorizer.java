package ru.prerev.tinderclient.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.enums.bot.ProfileButtonsEnum;
import ru.prerev.tinderclient.enums.resources.GenderEnum;
import ru.prerev.tinderclient.enums.resources.QuestionnaireEnum;
import ru.prerev.tinderclient.rest.DeleteService;
import ru.prerev.tinderclient.rest.GetService;
import ru.prerev.tinderclient.rest.PostService;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.InlineKeyboardMaker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class Authorizer {
    private Map<Long, User> userMap;
    private Map<Long, Boolean> authorizedMap;
    private final Profile profile;
    private final PostService postService;
    private final DeleteService deleteService;
    private final GetService getService;
    private final InlineKeyboardMaker inlineKeyboardMaker;

    private Bot bot;

    public void authorize(Long chatId, String message) {
        if (userMap == null) {
            userMap = new HashMap<>();
            authorizedMap = new HashMap<>();
        }
        message.trim();
        if (!userMap.containsKey(chatId)) {
            userMap.put(chatId, getService.get(chatId));
            authorizedMap.put(chatId, false);
        }
        if (userMap.get(chatId) != null) {
            if (userInitiated(userMap.get(chatId)) && !authorizedMap.get(chatId)) {
                profile.showProfile(chatId, userMap.get(chatId), inlineKeyboardMaker.getInlineMessageProfileButtons());
                authorizedMap.put(chatId, true);
            }
        } else {
            userMap.replace(chatId, new User());
        }
        if (userMap.get(chatId) == null || !userInitiated(userMap.get(chatId))) {
            registration(chatId, message);
        }
        if (message.equalsIgnoreCase(ProfileButtonsEnum.EDIT_PROFILE_BUTTON.getButtonName())) {
            deleteUserData(chatId);
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
        if (userInitiated(userMap.get(chatId))) {
            postService.post(userMap.get(chatId));
        }
    }

    private void initiateUserData(Long chatId, String message)
            throws IOException, TelegramApiException {
        if (userMap.get(chatId).getSex() == null) {
            if (message.equalsIgnoreCase(GenderEnum.MALE.getGender()) || message.equalsIgnoreCase(GenderEnum.FEMALE.getGender())) {
                userMap.get(chatId).setId(chatId);
                userMap.get(chatId).setSex(message);
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
            userMap.get(chatId).setLookingFor(message);
            SendMessage successMessage = new SendMessage(chatId.toString(),
                    "Вы успешно зарегистрированы.");
            bot.execute(successMessage);
            profile.showProfile(chatId, userMap.get(chatId), inlineKeyboardMaker.getInlineMessageProfileButtons());
        }
    }

    private void deleteUserData(Long chatId) {
        deleteService.delete(chatId);
        SendMessage deleteMessage = new SendMessage(chatId.toString(), "Анкета успешно удалена");
        try {
            bot.execute(deleteMessage);
            userMap.remove(chatId);
            authorize(chatId, "Рандом");
        } catch (TelegramApiException e) {
            log.error("Не удалось отправить сообщение: ");
        }
    }

    private boolean userInitiated(User user) {
        boolean initiated = true;
        if (user.getId() == null) {
            return false;
        }
        if (user.getSex() == null) {
            return false;
        }
        if (user.getName() == null) {
            return false;
        }
        if (user.getStory() == null) {
            return false;
        }
        if (user.getLookingFor() == null) {
            return false;
        }
        return initiated;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
        profile.setBot(bot);
    }
}
