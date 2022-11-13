package ru.prerev.tinderclient.search;

import lombok.RequiredArgsConstructor;
import ru.prerev.tinderclient.domain.User;
import ru.prerev.tinderclient.rest.GetService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class MatchSearcher {
    private final GetService getService;

    public List<List<User>> search(Long id) {
        List[] userList = getService.getMatchesList(id);
        List<Map<String, Object>> userLikedListMap = (List<Map<String, Object>>) userList[0];
        List<Map<String, Object>> likedUserListMap = (List<Map<String, Object>>) userList[1];
        List<Map<String, Object>> mutualLikingListMap = (List<Map<String, Object>>) userList[2];
        List<User> userLiked = getUserList(userLikedListMap);
        List<User> likedUser = getUserList(likedUserListMap);
        List<User> mutualLiking = getUserList(mutualLikingListMap);
        List<List<User>> lists = new ArrayList<>();
        lists.add(userLiked);
        lists.add(likedUser);

        lists.add(mutualLiking);
        return lists;
    }

    private List<User> getUserList(List<Map<String, Object>> listMap) {
        List<User> listUsers = new ArrayList<>();
        for (Map<String, Object> map : listMap) {
            User user = new User();
            Long id = Long.parseLong(map.get("id").toString());
            user.setId(id);
            user.setSex((String) map.get("sex"));
            user.setName((String) map.get("name"));
            user.setStory((String) map.get("story"));
            user.setLookingFor((String) map.get("lookingFor"));
            listUsers.add(user);
        }
        return listUsers;
    }
}
