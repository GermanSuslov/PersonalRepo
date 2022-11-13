package ru.prerev.tinderclient.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.rest.GetService;
import ru.prerev.tinderclient.telegrambot.Bot;

import java.io.File;

@Slf4j
@RequiredArgsConstructor
public class Profile {
    private final GetService getService;

    @Getter
    private Bot bot;

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
            log.error("Не удалось отправить изображение: ");
        }
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }
}
