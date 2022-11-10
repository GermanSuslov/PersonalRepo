package ru.prerev.tinderclient.search;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.domain.FormPictureCreator;
import ru.prerev.tinderclient.domain.User;
import ru.prerev.tinderclient.rest.GetService;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.ReplyKeyboardMaker;

import java.io.File;
import java.util.List;

@RequiredArgsConstructor
public class GenderSearcher {
    private Bot bot;
    private final GetService getService;
    private final FormPictureCreator formPictureCreator;
    private final ReplyKeyboardMaker replyKeyboardMaker;

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public void search(Long id) {
        List<User> userList = getService.getList(id);
        /*if (userList.size() > 0) {
            formPictureCreator.setBot(bot);
            formPictureCreator.showUserData(userList.get(0), replyKeyboardMaker.getScrollKeyboard());
        } else {
            // no one in GenderSearch
        }*/

        for (User user : userList) {
            File filePng = getService.getTranslatedPicture(user);
            InputFile pngFile = new InputFile(filePng, user.getUser_id() + "_form.png");
            SendPhoto formPng = new SendPhoto(id.toString(), pngFile);
            formPng.setReplyMarkup(replyKeyboardMaker.getScrollKeyboard());
            SendMessage translatedMessage = new SendMessage(id.toString(), user.getSex()
                    + ", " + getService.getTranslate(user.getName()));
            try {
                bot.execute(translatedMessage);
                bot.execute(formPng);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
