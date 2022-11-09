package ru.prerev.tinderclient.domain;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.rest.GetService;
import ru.prerev.tinderclient.telegrambot.Bot;

import java.io.File;
@RequiredArgsConstructor
public class FormPictureCreator {
    private final GetService getService;

    private Bot bot;

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public void showUserData(User user) {
        File filePng = getService.getTranslatedPicture(user);
        InputFile pngFile = new InputFile(filePng,  user.getUser_id() + "_form.png");
        SendPhoto formPng = new SendPhoto(user.getUser_id().toString(), pngFile);
        SendMessage translatedMessage = new SendMessage(user.getUser_id().toString(), user.getSex() +
                ", " + user.getName());
        try {
            bot.execute(translatedMessage);
            bot.execute(formPng);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}