package ru.prerev.tinderclient.search;

import lombok.RequiredArgsConstructor;
import ru.prerev.tinderclient.rest.GetService;
import ru.prerev.tinderclient.telegrambot.Bot;

@RequiredArgsConstructor
public class MatchSearcher {
    private Bot bot;

    private final GetService getService;

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public void search(Long id) {

    }
}

