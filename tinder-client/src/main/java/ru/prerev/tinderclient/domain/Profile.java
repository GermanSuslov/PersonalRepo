package ru.prerev.tinderclient.domain;

import lombok.RequiredArgsConstructor;
import ru.prerev.tinderclient.rest.GetService;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.InlineKeyboardMaker;

@RequiredArgsConstructor
public class Profile {
    private Bot bot;
    private final FormPictureCreator formPictureCreator;
    private final GetService getService;
    private final InlineKeyboardMaker inlineKeyboardMaker;

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public void showProfile(Long id) {
        formPictureCreator.setBot(bot);
        formPictureCreator.showUserData(getService.get(id), inlineKeyboardMaker.getFormButton());
    }
}
