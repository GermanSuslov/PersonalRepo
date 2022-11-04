package ru.prerev.tinderclient.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.prerev.tinderclient.telegrambot.Bot;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class Authorizer {
    private final Bot bot;
    private ArrayList<String> userDataList = new ArrayList<>();

    public void authorize(Long chatId, String message) throws IOException {
        message.trim();
        String fileName = "src/main/resources/" + chatId + "_user_data.txt";
        //BufferedReader testReader = null;
        try (BufferedReader authorizeReader = new BufferedReader(new FileReader(fileName))) {
            while (authorizeReader.ready()) {
                userDataList.add(authorizeReader.readLine());
            }
        } catch (FileNotFoundException e) {
            new File(fileName).createNewFile();
        }
        registration(chatId, message, fileName);
    }

    private void registration(Long chatId, String message, String fileName) {
        String ready = "Если вы хотите изменить анкету напишите: изменить анкету\n" +
                "Если вы хотите посмотреть анкету напишите: показать анкету";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            switch (userDataList.size()) {
                case 0:
                    writer.write(chatId.toString());//chatId
                    writer.newLine();
                    SendMessage sexMessage = new SendMessage(chatId.toString(), "Вы сударь иль сударыня?");
                    bot.execute(sexMessage);
                    break;
                case 1:
                    if (message.equalsIgnoreCase("сударъ") || message.equalsIgnoreCase("сударыня")) {
                        writer.write(message);//Пол
                        writer.newLine();
                    } else {
                        SendMessage sexMessageRepeat = new SendMessage(chatId.toString(), "Вы сударь иль сударыня?");
                        bot.execute(sexMessageRepeat);
                        bot.execute(sexMessageRepeat);
                        break;
                    }
                    SendMessage nameMessage = new SendMessage(chatId.toString(), "Как вас зовут?");
                    bot.execute(nameMessage);
                    break;
                case 2:
                    writer.write(message);//имя
                    writer.newLine();
                    SendMessage storyMessage = new SendMessage(chatId.toString(), "Опишите себя.");
                    bot.execute(storyMessage);
                    break;
                case 3:
                    writer.write(message);//история
                    writer.newLine();
                    SendMessage lookingForMessage = new SendMessage(chatId.toString(), "Кого вы ищите?");
                    bot.execute(lookingForMessage);
                    break;
                case 4:
                    writer.write(message);//кого ищет
                    SendMessage welcomeMessage = new SendMessage(chatId.toString(),
                            "Вы успешно зарегистрированы. " + ready);
                    bot.execute(welcomeMessage);
                    break;
                case 5:
                    if (!message.equalsIgnoreCase("изменить анкету") &&
                            !message.equalsIgnoreCase("Показать анкету")) {
                        SendMessage refactorMessage = new SendMessage(chatId.toString(), ready);
                        bot.execute(refactorMessage);
                    }
                    break;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        if (message.equalsIgnoreCase("изменить анкету")) {
            try {
                Files.delete(Path.of(fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (message.equalsIgnoreCase("Показать анкету")) {
            for (String str : userDataList) {
                SendMessage row = new SendMessage(chatId.toString(), str);
                try {
                    bot.execute(row);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
