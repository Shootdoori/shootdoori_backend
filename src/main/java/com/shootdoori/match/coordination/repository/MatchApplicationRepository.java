package com.shootdoori.match.coordination.repository;

import com.shootdoori.match.coordination.domain.MatchApplication;
import com.shootdoori.match.coordination.domain.MatchApplicationStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchApplicationRepository extends JpaRepository<MatchApplication, Long>,
    MatchApplicationRepositoryQuerydsl {

    boolean existsByMatchIdAndRequestTeamIdAndStatus(
        Long matchId,
        Long requestTeamId,
        MatchApplicationStatus status
    );

    List<MatchApplication> findAllByMatchIdAndStatus(Long matchId, MatchApplicationStatus status);
}
