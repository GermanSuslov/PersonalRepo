package ru.prerev.tinderclient.search;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.domain.User;
import ru.prerev.tinderclient.rest.GetService;
import ru.prerev.tinderclient.rest.MatchService;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.ReplyKeyboardMaker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class GenderSearcher {
    private Bot bot;
    private Map<Long, ArrayList<User>> userListsMap;
    private Map<Long, Integer> countMap;
    private final GetService getService;
    private final MatchService matchService;
    private final ReplyKeyboardMaker replyKeyboardMaker;

    public void search(Long id) {
        if (userListsMap == null) {
            userListsMap = new HashMap<>();
        }
        if (countMap == null) {
            countMap = new HashMap<>();
        }
        if (!userListsMap.containsKey(id)) {
            userListsMap.put(id, new ArrayList<>(getService.getList(id)));
            countMap.put(id, 0);
        }
        User currentUser = userListsMap.get(id).get(countMap.get(id));
        File filePng = getService.getTranslatedPicture(currentUser);
        InputFile pngFile = new InputFile(filePng, currentUser.getUser_id() + "_form.png");
        SendPhoto formPng = new SendPhoto(id.toString(), pngFile);
        formPng.setReplyMarkup(replyKeyboardMaker.getScrollKeyboard());
        SendMessage translatedMessage = new SendMessage(id.toString(), currentUser.getSex()
                + ", " + getService.getTranslate(currentUser.getName()));
        try {
            bot.execute(translatedMessage);
            bot.execute(formPng);
            if (countMap.get(id) + 1 == userListsMap.get(id).size()) {
                countMap.replace(id, 0);
            } else {
                countMap.replace(id, countMap.get(id) + 1);
            }
        } catch (TelegramApiException e) {
            System.out.println("Не удалось отправить сообщение :" + getClass());
        }
    }
    public void match(Long user_id) {
        int matchCount = 1;
        ArrayList<User> userList = userListsMap.get(user_id);
        Integer count = countMap.get(user_id) - matchCount;
        if (countMap.get(user_id) == 0) {
            count = userList.size() - 1;
        }
        Long liked_id = userList.get(count).getUser_id();
        matchService.match(user_id, liked_id);
    }

    public void resetAddParameters() {
        userListsMap = null;
        countMap = null;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }
}
