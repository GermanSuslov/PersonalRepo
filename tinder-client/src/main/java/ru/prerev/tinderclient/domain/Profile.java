package ru.prerev.tinderclient.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.rest.GetService;
import ru.prerev.tinderclient.telegrambot.Bot;

import java.io.File;

@RequiredArgsConstructor
public class Profile {
    @Getter
    private Bot bot;
    private final GetService getService;

    public void showProfile(Long id, User user, ReplyKeyboard keyboard) {
        File filePng = getService.getTranslatedPicture(user);
        InputFile pngFile = new InputFile(filePng, user.getId() + "_form.png");
        SendPhoto formPng = new SendPhoto(id.toString(), pngFile);
        formPng.setReplyMarkup(keyboard);
        SendMessage translatedMessage = new SendMessage(id.toString(), user.getSex() + ", "
                + getService.getTranslate(user.getName()));

        try {
            bot.execute(translatedMessage);
            bot.execute(formPng);
        } catch (TelegramApiException e) {
            System.out.println("Не удалось отправить изображение :" + getClass());
        }
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }
}
