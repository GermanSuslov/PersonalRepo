package ru.prerev.tinderclient.domain;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.rest.PostService;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.InlineKeyboardMaker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

@RequiredArgsConstructor
public class Authorizer {
    private Bot bot;

    private ArrayList<String> userDataList = new ArrayList<>();
    private final PostService postService;
    private final InlineKeyboardMaker inlineKeyboardMaker;
    private User user;

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public void authorize(Integer chatId, String message) throws IOException {
        message.trim();
        String fileName = "src/main/resources/" + chatId + "_user_data.txt";
        if (user == null) {
            user = new User();
        }
        if (!user.initiated()) {
            registration(chatId, message, fileName);
        } else {
            postService.post(user);
        }
        deleteUserData(message, fileName);
        showUserData(chatId, message);
    }

    private void registration(Integer chatId, String message, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            initiateUserData(chatId, message, writer);
        } catch (IOException e) {
            System.out.println("Файл не найден");
        } catch (TelegramApiException e) {
            System.out.println("Ошибка отправки сообщения при регистрации");
        }
    }


    private void initiateUserData(Integer chatId, String message, BufferedWriter writer)
            throws IOException, TelegramApiException {
        String ready = "Если вы хотите изменить анкету напишите: изменить анкету\n" +
                "Если вы хотите посмотреть анкету напишите: показать анкету";
        if (user.getId() == null) {
            SendMessage sexMessage = new SendMessage(chatId.toString(), "Вы сударь иль сударыня?");
            sexMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMessageSexButtons());
            bot.execute(sexMessage);
            user.setId(chatId);
        } else if (user.getSex() == null) {
            if (message.equalsIgnoreCase("сударъ") || message.equalsIgnoreCase("сударыня")) {
                user.setSex(message);
                SendMessage nameMessage = new SendMessage(chatId.toString(), "Как вас зовут?");
                bot.execute(nameMessage);
            } else {
                SendMessage sexMessageRepeat = new SendMessage(chatId.toString(), "Вы сударь иль сударыня?");
                sexMessageRepeat.setReplyMarkup(inlineKeyboardMaker.getInlineMessageSexButtons());
                bot.execute(sexMessageRepeat);
            }
        } else if (user.getName() == null) {
            SendMessage storyMessage = new SendMessage(chatId.toString(), "Опишите себя.");
            bot.execute(storyMessage);
            user.setName(message);
        } else if (user.getStory() == null) {
            SendMessage lookingForMessage = new SendMessage(chatId.toString(), "Кого вы ищите?");
            lookingForMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMessageLookingForButtons());
            bot.execute(lookingForMessage);
            user.setStory(message);
        } else if (user.getLooking_for() == null) {
            SendMessage welcomeMessage = new SendMessage(chatId.toString(),
                    "Вы успешно зарегистрированы. " + ready);
            bot.execute(welcomeMessage);
            user.setLooking_for(message);
        }
    }

    private void showUserData(Integer chatId, String message) {
        if (message.equalsIgnoreCase("Показать анкету")) {
            SendMessage data = new SendMessage(chatId.toString(), user.toString());
            try {
                bot.execute(data);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void deleteUserData(String message, String fileName) {
        if (message.equalsIgnoreCase("изменить анкету")) {
            try {
                Files.delete(Path.of(fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
