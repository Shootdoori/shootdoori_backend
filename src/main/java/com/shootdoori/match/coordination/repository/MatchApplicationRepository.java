package com.shootdoori.match.coordination.repository;

import com.shootdoori.match.coordination.domain.MatchApplication;
import com.shootdoori.match.coordination.domain.MatchApplicationStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchApplicationRepository extends JpaRepository<MatchApplication, Long> {

    boolean existsByMatchIdAndRequestTeamIdAndStatus(
        Long matchId,
        Long requestTeamId,
        MatchApplicationStatus status
    );

    List<MatchApplication> findAllByMatchIdAndStatus(Long matchId, MatchApplicationStatus status);
}
