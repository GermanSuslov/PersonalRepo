package ru.prerev.tinderclient.search;

import lombok.RequiredArgsConstructor;
import ru.prerev.tinderclient.domain.User;
import ru.prerev.tinderclient.rest.GetService;
import ru.prerev.tinderclient.telegrambot.Bot;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class MatchSearcher {
    private Bot bot;

    private final GetService getService;

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public ArrayList<ArrayList<User>> search(Long id) {
        List userList = getService.getMatchesList(id);
        ArrayList<ArrayList<User>> userArrayLists = (ArrayList<ArrayList<User>>) userList;
        return userArrayLists;
    }
}
