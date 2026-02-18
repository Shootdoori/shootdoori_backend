package com.shootdoori.match.coordination.repository;

import com.shootdoori.match.coordination.domain.Match;
import com.shootdoori.match.coordination.domain.MatchStatus;
import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {

    Slice<Match> findAllByHomeTeamIdAndStatusAndExpiresAtAfter(Long homeTeamId, MatchStatus status,
        LocalDateTime now, Pageable pageable);
}
