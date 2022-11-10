package ru.prerev.tinderclient.search;

import lombok.RequiredArgsConstructor;
import ru.prerev.tinderclient.domain.User;
import ru.prerev.tinderclient.rest.GetService;
import ru.prerev.tinderclient.telegrambot.Bot;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
public class MatchSearcher {
    private Bot bot;

    private final GetService getService;

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public ArrayList<ArrayList<LinkedHashMap<String, String>>> search(Long id) {
        List userList = getService.getMatchesList(id);
        ArrayList<ArrayList<LinkedHashMap<String, String>>> userArrayLists = (ArrayList<ArrayList<LinkedHashMap<String, String>>>) userList;
        return userArrayLists;
    }
}
