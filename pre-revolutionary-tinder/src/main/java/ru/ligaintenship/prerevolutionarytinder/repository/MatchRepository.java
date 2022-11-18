package ru.ligaintenship.prerevolutionarytinder.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.ligaintenship.prerevolutionarytinder.domain.Match;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchRepository {
    private final JdbcTemplate jdbcTemplate;
    private final String deleteByMatch = "delete from tinder.user_matches where user_id = ";
    private final String postMatch = "insert into tinder.user_matches (user_id, liked_id) values (%d, %d)";

    public int postMatch(Match match) {
        String sql = postMatch
                .formatted(match.getId(), match.getLikedId());
        int resCode = jdbcTemplate.update(sql);
        log.debug("PostMatch method completed with code: " + resCode);
        return resCode;
    }


    public void deleteMatch(Long id) {
        String sql = deleteByMatch + id + " or liked_id = " + id;
        jdbcTemplate.update(sql);
    }
}
