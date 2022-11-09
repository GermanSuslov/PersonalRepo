package ru.prerev.tinderclient.domain;

import lombok.RequiredArgsConstructor;
import ru.prerev.tinderclient.telegrambot.Bot;

public class Menu {
    private Bot bot;
    public void setBot(Bot bot) {
        this.bot = bot;
    }
    public void showMenu(Long id, String message) {
        if (message.equalsIgnoreCase("Перейти в меню")) {

        }
    }
}
