package ru.prerev.tinderclient.search;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.domain.User;
import ru.prerev.tinderclient.rest.GetService;
import ru.prerev.tinderclient.telegrambot.Bot;

import java.io.File;
import java.util.List;

@RequiredArgsConstructor
public class GenderSearcher {
    private Bot bot;

    private final GetService getService;

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public void search(Long id) {
        List<User> userList = getService.getList(id);
        for (User user : userList) {
            File filePng = getService.getTranslatedPicture(user);
            InputFile pngFile = new InputFile(filePng, user.getUser_id() + "_form.png");
            SendPhoto formPng = new SendPhoto(id.toString(), pngFile);
            SendMessage translatedMessage = new SendMessage(id.toString(), user.getSex() + ", " + user.getName());
            try {
                bot.execute(translatedMessage);
                bot.execute(formPng);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
