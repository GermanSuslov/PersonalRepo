package ru.prerev.tinderclient.domain;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.rest.DeleteService;
import ru.prerev.tinderclient.rest.GetService;
import ru.prerev.tinderclient.rest.PostService;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.InlineKeyboardMaker;
import ru.prerev.tinderclient.telegrambot.keyboard.ReplyKeyboardMaker;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class Authorizer {

    private Bot bot;
    private Map<Long, User> userMap;
    private final PostService postService;
    private final FormPictureCreator pictureCreator;
    private final DeleteService deleteService;
    private final GetService getService;
    private final InlineKeyboardMaker inlineKeyboardMaker;

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public void authorize(Long chatId, String message) {
        String ready = "Если вы хотите изменить анкету напишите: изменить анкету\n" +
                "Если вы хотите посмотреть анкету напишите: показать анкету";
        if (userMap == null) {
            userMap = new HashMap<>();
        }
        message.trim();
        if (!userMap.containsKey(chatId)) {
            userMap.put(chatId, new User());
        }
        try {
            if(!userMap.get(chatId).initiated()) {
                userMap.replace(chatId, getService.get(chatId));
                /*SendMessage welcomeMessage = new SendMessage(chatId.toString(),
                        "Вы успешно авторизированы. " + ready);
                welcomeMessage.setReplyMarkup(inlineKeyboardMaker.getFormButton());
                bot.execute(welcomeMessage);*/
                pictureCreator.showUserData(chatId, userMap.get(chatId), inlineKeyboardMaker.getFormButton());
            }
        } catch (Exception ignored) {

        }
        if (userMap.get(chatId) == null || !userMap.get(chatId).initiated()) {
            registration(chatId, message);
        }
        if (message.equalsIgnoreCase("Изменить анкету")) {
            deleteUserData(chatId);
        }
        if (message.equalsIgnoreCase("Показать анкету")) {
            if (pictureCreator.getBot() == null) {
                pictureCreator.setBot(bot);
            }
            pictureCreator.showUserData(chatId, userMap.get(chatId), null);
            //showUserData(chatId);
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
            if (message.equalsIgnoreCase("сударъ") || message.equalsIgnoreCase("сударыня")) {
                userMap.get(chatId).setUser_id(chatId);
                userMap.get(chatId).setSex(message);
                SendMessage nameMessage = new SendMessage(chatId.toString(), "Как вас величать?");
                bot.execute(nameMessage);
            } else {
                SendMessage sexMessageRepeat = new SendMessage(chatId.toString(), "Вы сударь иль сударыня?");
                sexMessageRepeat.setReplyMarkup(inlineKeyboardMaker.getInlineMessageSexButtons());
                bot.execute(sexMessageRepeat);
            }
        } else if (userMap.get(chatId).getName() == null) {
            SendMessage storyMessage = new SendMessage(chatId.toString(), "Опишите себя.");
            bot.execute(storyMessage);
            userMap.get(chatId).setName(message);
        } else if (userMap.get(chatId).getStory() == null) {
            SendMessage lookingForMessage = new SendMessage(chatId.toString(), "Кого вы ищите?");
            lookingForMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMessageLookingForButtons());
            bot.execute(lookingForMessage);
            userMap.get(chatId).setStory(message);
        } else if (userMap.get(chatId).getLooking_for() == null) {
            userMap.get(chatId).setLooking_for(message);
            SendMessage successMessage = new SendMessage(chatId.toString(),
                    "Вы успешно зарегистрированы.");
            //successMessage.setReplyMarkup(inlineKeyboardMaker.getFormButton());
            bot.execute(successMessage);
            pictureCreator.setBot(bot);
            pictureCreator.showUserData(chatId, userMap.get(chatId), inlineKeyboardMaker.getFormButton());
        }
    }

/*    private void showUserData(Long chatId) {
        *//*String urlTranslate = "http://localhost:5006/translate?resource=" + userMap.get(chatId).getStory();
        String translate = this.restTemplate.getForObject(urlTranslate, String.class);
        String urlPng = "http://localhost:5005/internal/image/from/text/?description=" + translate;
        byte[] png = this.restTemplate.getForObject(urlPng, byte[].class);*//*
        File filePng = getService.getTranslatedPicture(userMap.get(chatId));
        InputFile pngFile = new InputFile(filePng,  userMap.get(chatId).getUser_id() + "_form.png");
        SendPhoto formPng = new SendPhoto(chatId.toString(), pngFile);
        SendMessage translatedMessage = new SendMessage(chatId.toString(), userMap.get(chatId).getSex() + ", " + userMap.get(chatId).getName());
        formPng.setReplyMarkup(replyKeyboardMaker.getMenuKeyboard());
        try {
            bot.execute(translatedMessage);
            bot.execute(formPng);
            SendMessage showMessage = new SendMessage(chatId.toString(), "Ваша анкета");
            showMessage.setReplyMarkup(inlineKeyboardMaker.getFormButton());
            bot.execute(showMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }*/

    private void deleteUserData(Long chatId) {
        deleteService.delete(chatId);
        SendMessage deleteMessage = new SendMessage(chatId.toString(), "Анкета успешно удалена");
        try {
            bot.execute(deleteMessage);
            userMap.remove(chatId);
            authorize(chatId, "Рандом");
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
