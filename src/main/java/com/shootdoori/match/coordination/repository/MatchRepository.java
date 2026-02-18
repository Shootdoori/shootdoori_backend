package com.shootdoori.match.coordination.repository;

import com.shootdoori.match.coordination.domain.Match;
import com.shootdoori.match.coordination.domain.MatchStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchRepository extends JpaRepository<Match, Long> {

    Slice<Match> findAllByHomeTeamIdAndStatusAndExpiresAtAfter(Long homeTeamId, MatchStatus status,
        LocalDateTime now, Pageable pageable);

    @Query("""
        select m
        from Match m
        where (m.homeTeamId = :teamId or m.awayTeamId = :teamId)
          and m.status = :status
          and m.matchAt is not null
          and m.matchAt < :cursor
        order by m.matchAt desc, m.id desc
        """)
    List<Match> findRecentFinishedMatchesByTeamId(
        @Param("teamId") Long teamId,
        @Param("status") MatchStatus status,
        @Param("cursor") LocalDateTime cursor,
        Pageable pageable
    );
}
