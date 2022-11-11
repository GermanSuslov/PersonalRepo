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

    public ArrayList<ArrayList<User>> search(Long id) {
        List[] userList = getService.getMatchesList(id);
        ArrayList<LinkedHashMap<String, Object>> listHashUserLiked = (ArrayList<LinkedHashMap<String, Object>>) userList[0];
        ArrayList<LinkedHashMap<String, Object>> listHashLikedUser = (ArrayList<LinkedHashMap<String, Object>>) userList[1];
        ArrayList<LinkedHashMap<String, Object>> listHashMutualLiking = (ArrayList<LinkedHashMap<String, Object>>) userList[2];
        ArrayList<User> userLiked = getUserArrayList(listHashUserLiked);
        ArrayList<User> likedUser = getUserArrayList(listHashLikedUser);
        ArrayList<User> mutualLiking = getUserArrayList(listHashMutualLiking);
        ArrayList<ArrayList<User>> lists = new ArrayList<>();
        lists.add(userLiked);
        lists.add(likedUser);
        lists.add(mutualLiking);
        return lists;
    }

    private ArrayList<User> getUserArrayList(ArrayList<LinkedHashMap<String, Object>> listHash1) {
        ArrayList<User> listUsers = new ArrayList<>();
        for(LinkedHashMap<String, Object> hashMap : listHash1) {
            User user = new User();
            Long user_id = Long.parseLong(hashMap.get("user_id").toString());
            user.setUser_id(user_id);
            user.setSex((String) hashMap.get("sex"));
            user.setName((String) hashMap.get("name"));
            user.setStory((String) hashMap.get("story"));
            user.setLooking_for((String) hashMap.get("looking_for"));
            listUsers.add(user);
        }
        return listUsers;
    }
}
