package ru.prerev.tinderclient.domain;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.constants.bot.ProfileButtonsEnum;
import ru.prerev.tinderclient.constants.resources.GenderEnum;
import ru.prerev.tinderclient.constants.resources.QuestionnaireEnum;
import ru.prerev.tinderclient.rest.DeleteService;
import ru.prerev.tinderclient.rest.GetService;
import ru.prerev.tinderclient.rest.PostService;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.InlineKeyboardMaker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class Authorizer {
    private Bot bot;
    private Map<Long, User> userMap;
    private final Profile profile;
    private final PostService postService;
    private final DeleteService deleteService;
    private final GetService getService;
    private final InlineKeyboardMaker inlineKeyboardMaker;

    public void authorize(Long chatId, String message) {
        if (userMap == null) {
            userMap = new HashMap<>();
        }
        message.trim();
        if (!userMap.containsKey(chatId)) {
            userMap.put(chatId, new User());
        }
        try {
            if (!userMap.get(chatId).initiated()) {
                userMap.replace(chatId, getService.get(chatId));
                profile.setBot(bot);
                profile.showProfile(chatId, userMap.get(chatId), inlineKeyboardMaker.getInlineMessageProfileButtons());
            }
        } catch (Exception ignored) {

        }
        if (userMap.get(chatId) == null || !userMap.get(chatId).initiated()) {
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
            throw new RuntimeException(e);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        if (userMap.get(chatId).initiated()) {
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
            profile.setBot(bot);
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
            System.out.println("Не удалось отправить сообщение :" + getClass());
        }
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }
}
